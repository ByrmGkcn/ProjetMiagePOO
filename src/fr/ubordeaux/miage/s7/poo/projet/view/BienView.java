package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilierState;
import fr.ubordeaux.miage.s7.poo.projet.model.Locataire;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;
import fr.ubordeaux.miage.s7.poo.projet.model.TypeBien;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BienView {
    private final Stage stage;
    private final MainController mainController;
    private final ObservableList<BienImmobilier> biens;
    private final ObservableList<Locataire> locataires;

    private TableView<BienImmobilier> table;

    public BienView(Stage stage, MainController mainController, ObservableList<BienImmobilier> biens, ObservableList<Locataire> locataires) {
        this.stage = stage;
        this.mainController = mainController;
        this.biens = biens;
        this.locataires = locataires;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        table = new TableView<>();
        table.setItems(biens);

        // Colonne ID
        TableColumn<BienImmobilier, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        // Colonne Adresse
        TableColumn<BienImmobilier, String> addressColumn = new TableColumn<>("Adresse");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));

        // Colonne Code Postal
        TableColumn<BienImmobilier, String> postalCodeColumn = new TableColumn<>("Code Postal");
        postalCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodePostal()));

        // Colonne Ville
        TableColumn<BienImmobilier, String> cityColumn = new TableColumn<>("Ville");
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVille()));

        // Colonne Valeur
        TableColumn<BienImmobilier, String> valueColumn = new TableColumn<>("Valeur (€)");
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValeur())));

        // Colonne État
        TableColumn<BienImmobilier, String> stateColumn = new TableColumn<>("État");
        stateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel().getCurrentState().getName()));

        // Colonne locataire
        TableColumn<BienImmobilier, String> locataireColumn = new TableColumn<>("Locataire");
        locataireColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocataireName()));

        TableColumn<BienImmobilier, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(col -> new TableCell<BienImmobilier, Void>() {
            private final Button modifyButton = new Button("Modifier");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    BienImmobilier bien = getTableView().getItems().get(getIndex());
                    modifyButton.setOnAction(e -> openModifyDialog(bien)); 
                    setGraphic(modifyButton);
                }
            }
        });
        
        TableColumn<BienImmobilier, Void> transactionColumn = new TableColumn<>("Transactions");
        transactionColumn.setCellFactory(col -> new TableCell<BienImmobilier, Void>() {
            private final Button transactionButton = new Button("Transactions");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    BienImmobilier bien = getTableView().getItems().get(getIndex());
                    transactionButton.setOnAction(e -> openTransactionView(bien)); // Ouvre la vue des transactions
                    setGraphic(transactionButton);
                }
            }
        });
        
        table.getColumns().addAll(idColumn, addressColumn, postalCodeColumn, cityColumn, valueColumn, locataireColumn, stateColumn, actionColumn,transactionColumn);




        // Boutons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        Button addButton = new Button("Ajouter un bien");
        addButton.setOnAction(e -> openAddBienDialog());

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> mainController.openHomeView());

        buttonBox.getChildren().addAll(addButton, backButton);

        root.setCenter(table);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Gestion des biens");
        stage.setScene(scene);
        stage.show();
    }

    
    private void openAddBienDialog() {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Ajouter un bien");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        TextField addressField = new TextField();
        addressField.setPromptText("Adresse du bien");

        TextField postalCodeField = new TextField();
        postalCodeField.setPromptText("Code Postal");

        TextField cityField = new TextField();
        cityField.setPromptText("Ville");

        TextField valueField = new TextField();
        valueField.setPromptText("Valeur du bien");

        ComboBox<TypeBien> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList(TypeBien.values()));
        typeComboBox.setPromptText("Type de bien");

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(e -> {
            try {
                String adresse = addressField.getText();
                String codePostal = postalCodeField.getText();
                String ville = cityField.getText();
                double valeur = Double.parseDouble(valueField.getText());
                TypeBien typeBien = typeComboBox.getValue();

                if (typeBien == null) {
                    throw new IllegalArgumentException("Veuillez sélectionner un type de bien.");
                }

                Model model = new Model();
                biens.add(new BienImmobilier(adresse, codePostal, ville, valeur, typeBien, model));
                dialog.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer une valeur valide.");
                alert.showAndWait();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            }
        });

        dialogVBox.getChildren().addAll(
            new Label("Adresse :"), addressField,
            new Label("Code Postal :"), postalCodeField,
            new Label("Ville :"), cityField,
            new Label("Valeur (€) :"), valueField,
            new Label("Type de bien :"), typeComboBox,
            saveButton
        );

        Scene dialogScene = new Scene(dialogVBox, 500, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    private void openLouerBienDialog(BienImmobilier bien) {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Louer le bien");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(15));

        // Liste des locataires à afficher dans une ComboBox
        ComboBox<Locataire> locataireComboBox = new ComboBox<>();
        locataireComboBox.setItems(locataires);
        locataireComboBox.setPromptText("Sélectionnez un locataire");

        Button louerButton = new Button("Louer");
        louerButton.setOnAction(e -> {
            Locataire locataire = locataireComboBox.getValue();
            if (locataire != null) {
                bien.louer(locataire); // Appelle la méthode pour louer le bien
                table.refresh(); // Rafraîchit manuellement le tableau après modification
                dialog.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner un locataire.");
                alert.showAndWait();
            }
        });

        dialogVBox.getChildren().addAll(
            new Label("Sélectionnez un locataire :"), locataireComboBox,
            louerButton
        );

        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    
    private void openTransactionView(BienImmobilier bien) {
        TransactionView transactionView = new TransactionView(stage, mainController, FXCollections.observableArrayList(bien));
        transactionView.show();
    }
    
    private void openModifyDialog(BienImmobilier bien) {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Modifier le bien");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(15));

        // Création des boutons pour chaque action
        Button louerButton = new Button("Louer");
        louerButton.setOnAction(e -> {
            openLouerBienDialog(bien); // ouvre une autre pop-up pour sélectionner un locataire
            dialog.close(); 
        });

        Button vendreButton = new Button("Vendre");
        vendreButton.setOnAction(e -> {
            bien.getModel().setNextEvent(BienImmobilierState.VENDRE); // Change l'état à "Vendu"
            bien.liberer();
            table.refresh(); // Rafraîchit la table
            dialog.close(); 
        });

        Button disponibleButton = new Button("Disponible");
        disponibleButton.setOnAction(e -> {
            bien.getModel().setNextEvent(BienImmobilierState.DISPONIBLE); // Change l'état à "Disponible"
            bien.liberer();
            table.refresh(); // Rafraîchit la table
            dialog.close();
        });

        // Ajout des boutons uniquement en fonction de l'état actuel
        String currentState = bien.getModel().getCurrentState().getName();
        dialogVBox.getChildren().add(new Label("Actions disponibles :"));

        switch (currentState) {
            case "DISPONIBLE":
                dialogVBox.getChildren().addAll(louerButton, vendreButton);
                break;
            case "LOUE":
                dialogVBox.getChildren().addAll(disponibleButton, vendreButton);
                break;
            case "VENDU":
                dialogVBox.getChildren().add(disponibleButton);
                break;
            default:
                dialogVBox.getChildren().add(new Label("Aucune action disponible."));
                break;
        }

        // Ajouter le contenu et afficher la fenêtre
        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

}
