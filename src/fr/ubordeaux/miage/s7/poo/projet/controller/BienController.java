package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilierState;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;

public class BienController {

    private Model model; // Référence au modèle principal pour gérer les états des biens immobiliers

    public BienController(Model model) {
        this.model = model;
    }

    //Change l'état d'un bien immobilier si le changement d'état est possible (disponible vers loué par exemple).
    public void changerEtatBien(BienImmobilierState event) {
        // Vérifie si l'état actuel permet la transition via l'événement donné
        if (model.getCurrentState().getAvailableEvents().contains(event)) {
            model.setNextEvent(event); // Applique le changement d'état
            System.out.println("L'état a changé. Nouvel état : " + model.getCurrentState().getName());
        } else {
            // Cas où la transition demandée n'est pas possible
            System.out.println("Le bien est déjà " + model.getCurrentState().getName() + ", aucune autre action possible.");
        }
    }
}
