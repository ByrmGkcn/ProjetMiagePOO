package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagementView {
    private final Stage stage;
    private final MainController mainController;
    private final ObservableList<BienImmobilier> biens;

    public ManagementView(Stage stage, MainController mainController, ObservableList<BienImmobilier> biens) {
        this.stage = stage;
        this.mainController = mainController;
        this.biens = biens; // Récupère la liste partagée de biens
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Tableau des biens
        TableView<BienImmobilier> table = new TableView<>();
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

        // Colonne Suppression
        TableColumn<BienImmobilier, Void> deleteColumn = new TableColumn<>("Action");
        deleteColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button();

            {
                ImageView trashIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/trash.png")));
                trashIcon.setFitWidth(16);
                trashIcon.setFitHeight(16);
                deleteButton.setGraphic(trashIcon);
                deleteButton.setOnAction(e -> {
                    BienImmobilier bien = getTableView().getItems().get(getIndex());
                    biens.remove(bien);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(idColumn, addressColumn, postalCodeColumn, cityColumn, valueColumn, stateColumn, deleteColumn);

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

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(e -> {
            try {
                String adresse = addressField.getText();
                String codePostal = postalCodeField.getText();
                String ville = cityField.getText();
                double valeur = Double.parseDouble(valueField.getText());
                Model model = new Model();
                biens.add(new BienImmobilier(adresse, codePostal, ville, valeur, model));
                dialog.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer une valeur valide.");
                alert.showAndWait();
            }
        });

        dialogVBox.getChildren().addAll(
            new Label("Adresse :"), addressField,
            new Label("Code Postal :"), postalCodeField,
            new Label("Ville :"), cityField,
            new Label("Valeur (€) :"), valueField,
            saveButton
        );

        Scene dialogScene = new Scene(dialogVBox, 300, 250);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
