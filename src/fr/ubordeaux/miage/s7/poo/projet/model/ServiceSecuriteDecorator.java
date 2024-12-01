package fr.ubordeaux.miage.s7.poo.projet.model;

public class ServiceSecuriteDecorator implements BienImmobilierDecorator {
    private BienImmobilierDecorator bienImmobilierDecorator;

    public ServiceSecuriteDecorator(BienImmobilierDecorator bienImmobilierDecorator) {
        this.bienImmobilierDecorator = bienImmobilierDecorator;
    }

    @Override
    public double getValeur() {
        return bienImmobilierDecorator.getValeur() + 15000; // Ajouter 15 000 pour le service de sécurité
    }

    @Override
    public void addObserver(Observer observer) {
        bienImmobilierDecorator.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        bienImmobilierDecorator.removeObserver(observer);
    }

    @Override
    public void notifyObservers(String message) {
        bienImmobilierDecorator.notifyObservers(message);
    }
}