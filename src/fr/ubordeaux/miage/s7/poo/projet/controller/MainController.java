package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.view.*;
import fr.ubordeaux.miage.s7.poo.projet.model.*;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class MainController {
    private final Stage stage;
    private ObservableList<Locataire> locataires;
    private ObservableList<BienImmobilier> biens; 

    public MainController(Stage stage) {
        this.stage = stage;
        this.locataires = FXCollections.observableArrayList(); // Initialisation de la liste des locataires
        this.biens = FXCollections.observableArrayList(); // Initialisation de la liste des biens
        openHomeView(); // Affiche la vue d'accueil
    }

    // Retourne la liste des locataires
    public ObservableList<Locataire> getLocataires() {
        return locataires;
    }

    // Retourne la liste des biens 
    public ObservableList<BienImmobilier> getBiens() {
        return biens;
    }

    // Affiche la vue d'accueil
    public void openHomeView() {
        HomeView homeView = new HomeView(stage, this);
        homeView.show();
    }

    // Affiche la vue de gestion 
    public void openManagementView() {
        ManagementController managementController = new ManagementController(stage, this);
        managementController.showManagementView();
    }

    // Affiche la vue des locataires
    public void openLocataireView() {
        LocataireView locataireView = new LocataireView(stage, this, locataires);
        locataireView.show();
    }

    // Affiche la vue de gestion des biens, en incluant les locataires pour la gestion des locations
    public void openManagementViewWithBiens() {
        BienView managementView = new BienView(stage, this, biens, locataires);
        managementView.show();
    }

    public void addLocataire(Locataire locataire) {
        locataires.add(locataire);
    }

    // Ajoute un bien à la liste observable et ajoute un observateur pour gérer les contrats associés
    public void addBien(BienImmobilier bien) {
        biens.add(bien);
        ContratView contratView = new ContratView(stage, this, biens, locataires);
        bien.addObserver(contratView); // Lie le bien à la vue des contrats
    }

    public void removeLocataire(Locataire locataire) {
        locataires.remove(locataire);
    }

    public void removeBien(BienImmobilier bien) {
        biens.remove(bien);
    }

    public void openContratView() {
        ContratView contratView = new ContratView(stage, this, biens, locataires);
        contratView.show();
    }

    public void openTransactionView() {
        TransactionView transactionView = new TransactionView(stage, this, biens);
        transactionView.show();
    }

    public void openPatrimoineView() {
        PatrimoineController patrimoineController = new PatrimoineController(biens);
        PatrimoineView patrimoineView = new PatrimoineView(stage, patrimoineController, this);
        patrimoineView.show();
    }

    // Vérifie les contrats arrivant à échéance dans les 10 prochains jours
    public void checkContratsExpiration() {
        for (BienImmobilier bien : biens) {
            if (bien.getModel().getCurrentState().toBienImmobilierState() == BienImmobilierState.LOUER) {
                LocalDate today = LocalDate.now();
                LocalDate fin = bien.getFinLocation();

                if (fin != null && !today.isAfter(fin) && today.plusDays(10).isAfter(fin)) {
                    // Notification en console (à implémenter dans une vue)
                    System.out.println("Le contrat pour le bien " + bien.getAdresse() + " arrive à échéance le " + fin);
                }
            }
        }
    }

    // Démarre un vérificateur automatique des échéances des contrats (toutes les 24 heures)
    public void startContratsChecker() {
        Timer timer = new Timer(true); // Mode daemon : s'arrête avec l'application
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkContratsExpiration();
            }
        }, 0, 24 * 60 * 60 * 1000); // Intervalle : toutes les 24 heures
    }
}
