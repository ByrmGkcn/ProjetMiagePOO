package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.model.Event;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;

public class BienController {

    private Model model;

    public BienController(Model model) {
        this.model = model;
    }

    public void changerEtatBien(Event event) {
        // Vérifie si l'événement est valide pour l'état actuel
        if (model.getCurrentState().getAvailableEvents().contains(event)) {
            model.setNextEvent(event); // Modifie l'état si l'événement est valide
            System.out.println("L'état a changé. Nouvel état : " + model.getCurrentState().getName());
        } else {
            System.out.println("Le bien est déjà " + model.getCurrentState().getName() + ", aucune autre action possible.");
        }
    }
}
