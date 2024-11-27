package fr.ubordeaux.miage.s7.poo.projet.model;

public enum TypeBien {
    APPARTEMENT,
    BUREAU,
    COMMERCE,
    TERRAIN;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
