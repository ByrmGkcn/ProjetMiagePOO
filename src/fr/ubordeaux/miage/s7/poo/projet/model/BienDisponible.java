package fr.ubordeaux.miage.s7.poo.projet.model;

import java.util.List;

public class BienDisponible implements State {

    private static final String NAME = "DISPONIBLE";

    @Override
    public void handle(Model model) {
        switch (model.getNextEvent()) {
            case LOUER:
                model.setCurrentState(new BienLoue());
                break;
            case VENDRE:
                model.setCurrentState(new BienVendu());
                break;
            default:
                throw new IllegalStateException("Événement non pris en charge dans cet état.");
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<BienImmobilierState> getAvailableEvents() {
        return List.of(BienImmobilierState.LOUER, BienImmobilierState.VENDRE);
    }
    
    @Override
    public BienImmobilierState toBienImmobilierState() {
        return BienImmobilierState.DISPONIBLE;
    }
}
