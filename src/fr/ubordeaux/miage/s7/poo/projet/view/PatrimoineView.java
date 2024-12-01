package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import fr.ubordeaux.miage.s7.poo.projet.controller.PatrimoineController;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class PatrimoineView {
    private final Stage stage;
    private final PatrimoineController patrimoineController;
    private final MainController mainController;

    public PatrimoineView(Stage stage, PatrimoineController patrimoineController, MainController mainController) {
        this.stage = stage;
        this.patrimoineController = patrimoineController;
        this.mainController = mainController;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Création du grid pour l'affichage des informations
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));

        // Ajout des labels pour chaque information
        addRow(grid, "Valeur totale du patrimoine :",
               String.format("%.2f €", patrimoineController.calculerValeurTotale()), 0);
        addRow(grid, "Revenus générés :",
               String.format("%.2f €", patrimoineController.calculerSoldeNetGeneres()), 1);
        addRow(grid, "Locations en cours :",
               patrimoineController.getLocationsEnCours()
                                   .stream()
                                   .map(BienImmobilier::toString)
                                   .collect(Collectors.joining(", ")), 2);
        addRow(grid, "Locations disponibles :",
               patrimoineController.getLocationsDisponibles()
                                   .stream()
                                   .map(BienImmobilier::toString)
                                   .collect(Collectors.joining(", ")), 3);
        addRow(grid, "Biens vendus :",
               patrimoineController.getBiensVendus()
                                   .stream()
                                   .map(BienImmobilier::toString)
                                   .collect(Collectors.joining(", ")), 4);

        // Encapsuler le grid dans un ScrollPane
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        // Bouton Retour
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> mainController.openHomeView());

        HBox buttonContainer = new HBox(backButton);
        buttonContainer.setPadding(new Insets(10));
        buttonContainer.setStyle("-fx-alignment: center;");
        root.setBottom(buttonContainer);

        // Création de la scène
        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.setTitle("État du patrimoine");
        stage.show();
    }

    private void addRow(GridPane grid, String label, String value, int rowIndex) {
        Label labelNode = new Label(label);
        Label valueNode = new Label(value);
        grid.add(labelNode, 0, rowIndex);
        grid.add(valueNode, 1, rowIndex);
    }
}
