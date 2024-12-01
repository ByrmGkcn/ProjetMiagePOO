package fr.ubordeaux.miage.s7.poo.projet.model;

import java.util.List;

public class BienLoue implements State {

    private static final String NAME = "LOUE";

    @Override
    public void handle(Model model) {
        switch (model.getNextEvent()) {
            case VENDRE:
                model.setCurrentState(new BienVendu());
                break;
            case DISPONIBLE:
            	model.setCurrentState(new BienDisponible());
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
        return List.of(BienImmobilierState.VENDRE);
    }
    
    @Override
    public BienImmobilierState toBienImmobilierState() {
        return BienImmobilierState.LOUER;
    }
}
