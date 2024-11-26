package fr.ubordeaux.miage.s7.poo.projet.model;

public class BienImmobilier {
    private static int idCounter = 0; // Compteur d'ID unique
    private final String id;
    private final String adresse;
    private final String codePostal;
    private final String ville;
    private final double valeur;
    private final Model model;

    public BienImmobilier(String adresse, String codePostal, String ville, double valeur, Model model) {
        this.id = "B" + (++idCounter); // Génération automatique d'un ID
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.valeur = valeur;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }

    public double getValeur() {
        return valeur;
    }

    public Model getModel() {
        return model;
    }

}
