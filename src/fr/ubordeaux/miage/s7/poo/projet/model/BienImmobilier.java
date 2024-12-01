package fr.ubordeaux.miage.s7.poo.projet.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BienImmobilier implements BienImmobilierDecorator {
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
    private List<Transaction> transactions;
    private List<Observer> observers = new ArrayList<>();

    public BienImmobilier(String adresse, String codePostal, String ville, double valeur, TypeBien typeBien, Model model) {
        this.id = "B" + idCounter; // B1, puis B2 etc..
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
        this.transactions=new ArrayList<>();
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


    public void setDateDebutLocation(LocalDate dateDebutLocation) {
        this.dateDebutLocation = dateDebutLocation;
    }

    public double getRevenusGeneres() {
        return transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.REVENUE ||
                             t.getType() == Transaction.TransactionType.LOYER)
                .mapToDouble(Transaction::getMontant)
                .sum();
    }
    
    public double getDepenseGeneres() {
        return transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.EXPENSE)
                .mapToDouble(Transaction::getMontant)
                .sum();
    }
    
    public void setDateFinLocation(LocalDate dateFinLocation) {
        this.dateFinLocation = dateFinLocation;
    }

    public void louer(Locataire locataire) {
        if (model.getCurrentState() instanceof BienDisponible) {
            setLocataire(locataire);
            setDateDebutLocation(LocalDate.now());
            setDateFinLocation(LocalDate.now().plusYears(1));
            model.setCurrentState(new BienLoue());
        }
    }

    public void liberer() {
        this.locataire = null;
    }

    public String getLocataireName() {
        return (locataire != null) ? locataire.getName() : "-";
    }
    
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    @Override
    public String toString() {
        return String.format("%s, %s - %s", adresse, ville, codePostal);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


	@Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
	
	//Arbitrairement un bien arrive à échéance à partir de 10 jours avant la fin du contrat
	public void checkExpiration() {
        LocalDate today = LocalDate.now();
        if (dateFinLocation != null && !today.isAfter(dateFinLocation) && today.plusDays(10).isAfter(dateFinLocation)) {
            notifyObservers("Le contrat pour le bien " + adresse + " arrive à échéance le " + dateFinLocation);
        }
    }


}
