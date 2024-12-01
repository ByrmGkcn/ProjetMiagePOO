package fr.ubordeaux.miage.s7.poo.projet.model;

import java.time.LocalDate;

public class Transaction {
	
    public enum TransactionType {
        REVENUE("Revenu"),
        EXPENSE("Dépense"),
    	LOYER("Loyer");

        private final String label;

        TransactionType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private final LocalDate date;
    private final double montant;
    private final TransactionType type; 
    private final BienImmobilier bien;
    private final String titre;

    public Transaction(LocalDate date, double montant, TransactionType type, BienImmobilier bien, String titre) {
        this.date = date;
        this.montant = montant;
        this.type = type;
        this.bien = bien;
        this.titre=titre;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getMontant() {
        return montant;
    }

    public TransactionType getType() {
        return type;
    }
    
    public String getTitre() {
    	return titre;
    }

    public BienImmobilier getBien() {
        return bien;
    }

    @Override
    public String toString() {
        return "Type: " + type.getLabel() + ", Montant: " + montant + ", Date: " + date;
    }
}
