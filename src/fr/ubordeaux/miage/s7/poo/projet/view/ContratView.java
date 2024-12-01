package fr.ubordeaux.miage.s7.poo.projet.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import fr.ubordeaux.miage.s7.poo.projet.model.Locataire;
import fr.ubordeaux.miage.s7.poo.projet.model.Observer;

public class ContratView implements Observer {
    private final Stage stage;
    private final MainController mainController;
    private final ObservableList<BienImmobilier> biens;
    private final ObservableList<Locataire> locataires;

    public ContratView(Stage stage, MainController mainController, ObservableList<BienImmobilier> biens, ObservableList<Locataire> locataires) {
        this.stage = stage;
        this.mainController = mainController;
        this.biens = biens;
        this.locataires = locataires;
    }

    // Méthode pour afficher la vue des contrats
    public void show() {
        BorderPane root = new BorderPane();

        // Filtrer les biens pour n'afficher que ceux avec un locataire
        ObservableList<BienImmobilier> biensAvecLocataire = FXCollections.observableArrayList();
        for (BienImmobilier bien : biens) {
            if (bien.getLocataire() != null) {
                biensAvecLocataire.add(bien);
            }
        }

        // Table pour afficher les contrats
        TableView<BienImmobilier> table = new TableView<>();
        table.setItems(biensAvecLocataire);

        // Colonne des biens
        TableColumn<BienImmobilier, String> bienColumn = new TableColumn<>("Bien");
        bienColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));

        // Colonne des locataires
        TableColumn<BienImmobilier, String> locataireColumn = new TableColumn<>("Locataire");
        locataireColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocataire() != null ? cellData.getValue().getLocataire().getName() : "-"));

        // Colonne de la date de début
        TableColumn<BienImmobilier, String> debutColumn = new TableColumn<>("Début de location");
        debutColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDebutLocation() != null) {
                return new SimpleStringProperty(cellData.getValue().getDebutLocation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                return new SimpleStringProperty("-");
            }
        });

        // Colonne de la date de fin
        TableColumn<BienImmobilier, String> finColumn = new TableColumn<>("Fin de location");
        finColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFinLocation() != null) {
                return new SimpleStringProperty(cellData.getValue().getFinLocation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                return new SimpleStringProperty("-");
            }
        });

        // Colonne avec bouton pour télécharger le contrat
        TableColumn<BienImmobilier, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(col -> new TableCell<BienImmobilier, Void>() {
            private final Button btn = new Button("Télécharger");

            {
                btn.setOnAction(e -> {
                    BienImmobilier selectedBien = getTableRow().getItem();
                    if (selectedBien != null) {
                        downloadContract(selectedBien);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        table.getColumns().addAll(bienColumn, locataireColumn, debutColumn, finColumn, actionColumn);

        root.setCenter(table);

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> mainController.openHomeView());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(backButton);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Contrats de location");
        stage.setScene(scene);
        stage.show();
    }

    // Méthode pour télécharger le contrat sous forme de fichier texte (pdf plus compliqué)
    private void downloadContract(BienImmobilier selectedBien) {
        if (selectedBien == null || selectedBien.getLocataire() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ce bien n'a pas de locataire.");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                BienImmobilier bien = selectedBien;
                Locataire locataire = bien.getLocataire();

                // Écrire les informations dans le fichier texte
                writer.write("Contrat de Location\n");
                writer.write("Bien immobilier : " + bien.getAdresse() + "\n");
                writer.write("Locataire : " + locataire.getName() + "\n");
                writer.write("Début de location : " + (bien.getDebutLocation() != null ?
                        bien.getDebutLocation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "-") + "\n");
                writer.write("Fin de location : " + (bien.getFinLocation() != null ?
                        bien.getFinLocation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "-") + "\n");

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Le contrat a été téléchargé avec succès.");
                alert.showAndWait();

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la création du fichier texte.");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void update(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification de contrat");
        alert.setHeaderText("Fin de contrat proche !");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
