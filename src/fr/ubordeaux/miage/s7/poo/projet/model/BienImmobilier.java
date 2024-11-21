package fr.ubordeaux.miage.s7.poo.projet.model;

public class BienImmobilier {
    private String adresse;
    private double valeur;
    private Model model;

    public BienImmobilier(String adresse, double valeur, Model model) {
        this.adresse = adresse;
        this.valeur = valeur;
        this.model = model;
    }

    public void changerEtat(State nouvelEtat) {
        model.setCurrentState(nouvelEtat);
    }

    public String getAdresse() {
        return adresse;
    }

    public double getValeur() {
        return valeur;
    }
}
