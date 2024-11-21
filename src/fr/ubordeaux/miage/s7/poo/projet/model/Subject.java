package fr.ubordeaux.miage.s7.poo.projet.model;

import fr.ubordeaux.miage.s7.poo.projet.view.Listener;

public interface Subject {
    void setListener(Listener listener);
    void notifyToListener();
}
