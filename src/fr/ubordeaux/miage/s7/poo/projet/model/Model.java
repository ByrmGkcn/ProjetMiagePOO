package fr.ubordeaux.miage.s7.poo.projet.model;

import fr.ubordeaux.miage.s7.poo.projet.view.Listener;

public class Model implements Subject {

    private State currentState;
    private Event nextEvent;
    private Listener listener;

    public Model() {
        currentState = new BienDisponible(); // Il s'agit de l'état inital d'un bien !
    }

    public Event getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(Event nextEvent) {
        this.nextEvent = nextEvent;
        currentState.handle(this); // Appelle le gestionnaire de l'état actuel
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        notifyToListener();
    }

    public State getCurrentState() {
        return currentState;
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void notifyToListener() {
        if (listener != null) {
            listener.update(this);
        }
    }
}
