package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import fr.ubordeaux.miage.s7.poo.projet.model.Transaction;

import java.time.LocalDate;

public class TransactionController {

    public void enregistrerLoyer(double montant, BienImmobilier bien, String titre) {
        Transaction transaction = new Transaction(LocalDate.now(), montant, Transaction.TransactionType.LOYER, bien, titre);
        bien.addTransaction(transaction);
    }

    public void enregistrerDepense(double montant, BienImmobilier bien, String titre) {
        Transaction transaction = new Transaction(LocalDate.now(), montant, Transaction.TransactionType.EXPENSE, bien, titre);
        bien.addTransaction(transaction);
    }
    
    public void enregistrerRevenu(double montant, BienImmobilier bien, String titre) {
        Transaction transaction = new Transaction(LocalDate.now(), montant, Transaction.TransactionType.REVENUE, bien, titre);
        bien.addTransaction(transaction);
    }
    
}
