package fr.ubordeaux.miage.s7.poo.projet.model;

public class ParkingDecorator implements BienImmobilierDecorator {
    private BienImmobilierDecorator bienImmobilierDecorator;

    public ParkingDecorator(BienImmobilierDecorator bienImmobilierDecorator) {
        this.bienImmobilierDecorator = bienImmobilierDecorator;
    }

    @Override
    public double getValeur() {
        return bienImmobilierDecorator.getValeur() + 10000; // valeur à ajouter pour l'option
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
