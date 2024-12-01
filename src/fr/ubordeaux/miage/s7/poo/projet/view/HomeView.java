package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HomeView {
    private final Stage stage;
    private final MainController mainController;

    public HomeView(Stage stage, MainController mainController) {
        this.stage = stage;
        this.mainController = mainController;
    }

    public void show() {
        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);

        Button biensButton = new Button("Biens");
        biensButton.setOnAction(e -> mainController.openManagementView());

        Button tenantsButton = new Button("Locataire");
        tenantsButton.setOnAction(e -> mainController.openLocataireView());

        Button contratsButton = new Button("Contrat");
        contratsButton.setOnAction(e -> mainController.openContratView());

        Button patrimoineButton = new Button("Patrimoine");
        patrimoineButton.setOnAction(e -> mainController.openPatrimoineView());
        
        root.getChildren().addAll(tenantsButton, biensButton, contratsButton, patrimoineButton);

        Scene scene = new Scene(root, 600, 200); 
        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show();
    }
}
