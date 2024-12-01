package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import fr.ubordeaux.miage.s7.poo.projet.model.Locataire;
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
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocataireView {
    private final Stage stage;
    private final MainController mainController;
    private final ObservableList<Locataire> locataires;

    public LocataireView(Stage stage, MainController mainController, ObservableList<Locataire> locataires) {
        this.stage = stage;
        this.mainController = mainController;
        this.locataires = locataires; // Utilise bien la liste de locataires partagée
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Tableau des locataires
        TableView<Locataire> table = new TableView<>();
        table.setItems(locataires);

        TableColumn<Locataire, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Locataire, String> surnameColumn = new TableColumn<>("Prénom");
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));

        TableColumn<Locataire, String> genderColumn = new TableColumn<>("Genre");
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));

        TableColumn<Locataire, String> birthDateColumn = new TableColumn<>("Date de naissance");
        birthDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate().toString()));

        TableColumn<Locataire, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        table.getColumns().addAll(nameColumn, surnameColumn, genderColumn, birthDateColumn, emailColumn);

        // Boutons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        Button addButton = new Button("Ajouter un locataire");
        addButton.setOnAction(e -> openAddLocataireDialog());

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> mainController.openHomeView());

        buttonBox.getChildren().addAll(addButton, backButton);

        root.setCenter(table);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Gestion des locataires");
        stage.setScene(scene);
        stage.show();
    }

    private void openAddLocataireDialog() {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Ajouter un locataire");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(15));

        TextField nameField = new TextField();
        nameField.setPromptText("Nom");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Prénom");

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.setItems(FXCollections.observableArrayList("Homme", "Femme", "Non spécifié"));
        genderBox.setPromptText("Genre");

        DatePicker birthDatePicker = new DatePicker();
        birthDatePicker.setPromptText("Date de naissance");
        birthDatePicker.setConverter(new StringConverter<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                return date != null ? date.format(formatter) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(string, formatter);
            }
        });

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String surname = surnameField.getText().trim();
                String gender = genderBox.getValue();
                LocalDate birthDate = birthDatePicker.getValue();
                String email = emailField.getText().trim();

                // Validation des champs obligatoires
                if (name.isEmpty() || surname.isEmpty() || gender == null || birthDate == null || email.isEmpty()) {
                    throw new IllegalArgumentException("Tous les champs doivent être remplis.");
                }

                // Validation de l'email
                if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    throw new IllegalArgumentException("Adresse email invalide.");
                }

                // Ajout du locataire
                locataires.add(new Locataire(name, surname, gender, birthDate, email));
                dialog.close();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Une erreur inattendue est survenue.");
                alert.showAndWait();
            }
        });

        dialogVBox.getChildren().addAll(
            new Label("Nom :"), nameField,
            new Label("Prénom :"), surnameField,
            new Label("Genre :"), genderBox,
            new Label("Date de naissance :"), birthDatePicker,
            new Label("Email :"), emailField,
            saveButton
        );

        Scene dialogScene = new Scene(dialogVBox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
