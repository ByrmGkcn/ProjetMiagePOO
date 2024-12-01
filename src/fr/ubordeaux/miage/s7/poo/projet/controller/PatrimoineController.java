package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilierState;

import java.util.List;
import java.util.stream.Collectors;

public class PatrimoineController {
    private final List<BienImmobilier> biens;

    public PatrimoineController(List<BienImmobilier> biens) {
        this.biens = biens;
    }

    public double calculerValeurTotale() {
        return biens.stream()
                .mapToDouble(BienImmobilier::getValeur)
                .sum();
    }

    public double calculerSoldeNetGeneres() {
        return biens.stream()
                .mapToDouble(bien -> bien.getRevenusGeneres() - bien.getDepenseGeneres())
                .sum();
    }

    public List<BienImmobilier> getLocationsEnCours() {
        return biens.stream()
                .filter(bien -> bien.getModel().getCurrentState().toBienImmobilierState() == BienImmobilierState.LOUER)
                .collect(Collectors.toList());
    }

    public List<BienImmobilier> getLocationsDisponibles() {
        return biens.stream()
                .filter(bien -> bien.getModel().getCurrentState().toBienImmobilierState() == BienImmobilierState.DISPONIBLE)
                .collect(Collectors.toList());
    }
    
    public List<BienImmobilier> getBiensVendus() {
        return biens.stream()
                .filter(bien -> bien.getModel().getCurrentState().toBienImmobilierState() == BienImmobilierState.VENDRE)
                .collect(Collectors.toList());
    }
}
