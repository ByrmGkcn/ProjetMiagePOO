package fr.ubordeaux.miage.s7.poo.projet.model;

public enum BienImmobilierState {
    LOUER("LOUER"),
    VENDRE("VENDRE"),
    DISPONIBLE("DISPONIBLE");

    private String text;

    BienImmobilierState(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
