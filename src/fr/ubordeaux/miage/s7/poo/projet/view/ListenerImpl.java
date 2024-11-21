package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.model.Model;

public class ListenerImpl implements Listener {
    @Override
    public void update(Model model) {
        System.out.println("L'état a changé. Nouvel état : " + model.getCurrentState().getName());
    }
}
