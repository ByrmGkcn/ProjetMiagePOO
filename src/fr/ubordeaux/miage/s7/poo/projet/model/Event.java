package fr.ubordeaux.miage.s7.poo.projet.model;

public enum Event {
    LOUER("LOUER"),
    VENDRE("VENDRE"),
    DISPONIBLE("DISPONIBLE");

    private String text;

    Event(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
