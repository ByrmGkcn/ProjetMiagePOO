package fr.ubordeaux.miage.s7.poo.projet.model;

import java.time.LocalDate;

public class BienImmobilier {
	private static int idCounter = 1;
    private String id;
    private String adresse;
    private String codePostal;
    private String ville;
    private double valeur;
    private TypeBien typeBien;
    private Model model;
    private Locataire locataire;
    private LocalDate dateDebutLocation;
    private LocalDate dateFinLocation;

    public BienImmobilier(String adresse, String codePostal, String ville, double valeur, TypeBien typeBien, Model model) {
        this.id = "B" + idCounter;
        idCounter++;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.valeur = valeur;
        this.typeBien = typeBien;
        this.model = model;
        this.locataire = null;
        this.dateDebutLocation = null;
        this.dateFinLocation = null;
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

    public TypeBien getTypeBien() {
        return typeBien;
    }

    public Model getModel() {
        return model;
    }

    public LocalDate getDebutLocation() {
        return dateDebutLocation;
    }

    public LocalDate getFinLocation() {
        return dateFinLocation;
    }
    public Locataire getLocataire() {
        return locataire;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }

    // Getter et setter pour la date de début et de fin
    public LocalDate getDateDebutLocation() {
        return dateDebutLocation;
    }

    public void setDateDebutLocation(LocalDate dateDebutLocation) {
        this.dateDebutLocation = dateDebutLocation;
    }

    public LocalDate getDateFinLocation() {
        return dateFinLocation;
    }

    public void setDateFinLocation(LocalDate dateFinLocation) {
        this.dateFinLocation = dateFinLocation;
    }

    public void louer(Locataire locataire) {
        if (model.getCurrentState() instanceof BienDisponible) {
            setLocataire(locataire);
            setDateDebutLocation(LocalDate.now());
            setDateFinLocation(LocalDate.now().plusYears(1));
            model.setCurrentState(new BienLoue()); // Change l'état en "Loué"
        }
    }

    public void liberer() {
        this.locataire = null;
    }

    public String getLocataireName() {
        return (locataire != null) ? locataire.getName() : "-";
    }
}
