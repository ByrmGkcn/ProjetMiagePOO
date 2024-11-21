package fr.ubordeaux.miage.s7.poo.projet;

import fr.ubordeaux.miage.s7.poo.projet.controller.ButtonEventHandler;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;
import fr.ubordeaux.miage.s7.poo.projet.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Initialisation du modèle
        Model model = new Model();

        // Gestionnaire d'événements
        ButtonEventHandler eventHandler = new ButtonEventHandler(model);

        // Vue
        View view = new View(stage, eventHandler);
        model.setListener(view);

        // Lier la vue et le gestionnaire
        eventHandler.setView(view);
    }
}
