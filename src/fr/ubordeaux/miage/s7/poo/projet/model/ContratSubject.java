package fr.ubordeaux.miage.s7.poo.projet.model;

public interface ContratSubject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}
