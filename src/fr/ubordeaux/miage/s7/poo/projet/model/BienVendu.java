package fr.ubordeaux.miage.s7.poo.projet.model;

import java.util.List;

public class BienVendu implements State {

    private static final String NAME = "VENDU";

    @Override
    public void handle(Model model) {
        // Aucune transition possible à partir de cet état
        throw new IllegalStateException("Le bien est déjà vendu, aucune action possible.");
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<Event> getAvailableEvents() {
        return List.of(); // Aucune action possible après la vente
    }
}
