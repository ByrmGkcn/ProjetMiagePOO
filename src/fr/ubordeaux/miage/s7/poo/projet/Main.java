package fr.ubordeaux.miage.s7.poo.projet;

import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new MainController(primaryStage);
        Image logo = new Image(getClass().getResourceAsStream("/icons/logo.jpg"));
        primaryStage.getIcons().add(logo);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
