package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.view.HomeView;
import fr.ubordeaux.miage.s7.poo.projet.view.LocataireView;
import fr.ubordeaux.miage.s7.poo.projet.view.ManagementView;
import fr.ubordeaux.miage.s7.poo.projet.model.Locataire;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class MainController {
    private final Stage stage;
    private ObservableList<Locataire> locataires;
    private ObservableList<BienImmobilier> biens; // Liste partagée pour les biens

    public MainController(Stage stage) {
        this.stage = stage;
        this.locataires = FXCollections.observableArrayList(); // Liste de locataires
        this.biens = FXCollections.observableArrayList(); // Liste de biens immobiliers
        openHomeView();
    }

    public ObservableList<Locataire> getLocataires() {
        return locataires;
    }

    public ObservableList<BienImmobilier> getBiens() {
        return biens; // Retourne la liste partagée des biens
    }

    public void openHomeView() {
        HomeView homeView = new HomeView(stage, this);
        homeView.show();
    }

    public void openManagementView() {
        ManagementController managementController = new ManagementController(stage, this);
        managementController.showManagementView();
    }

    public void openLocataireView() {
        LocataireView locataireView = new LocataireView(stage, this, locataires);
        locataireView.show();
    }

    public void openManagementViewWithBiens() {
        ManagementView managementView = new ManagementView(stage, this, biens);
        managementView.show();
    }
}
