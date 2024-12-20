package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.view.BienView;
import javafx.stage.Stage;

public class ManagementController {
    private final Stage stage;
    private final MainController mainController;

    public ManagementController(Stage stage, MainController mainController) {
        this.stage = stage;
        this.mainController = mainController;
    }

    public void showManagementView() {
        var biens = mainController.getBiens();
        var locataires = mainController.getLocataires(); 

        BienView managementView = new BienView(stage, mainController, biens, locataires);
        managementView.show();
    }
}
