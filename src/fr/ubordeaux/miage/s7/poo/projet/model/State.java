package fr.ubordeaux.miage.s7.poo.projet.model;

import java.util.List;

public interface State {
    void handle(Model model);
    String getName();
    List<Event> getAvailableEvents();
}
