package fr.ubordeaux.miage.s7.poo.projet.controller;

import fr.ubordeaux.miage.s7.poo.projet.model.Event;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;
import fr.ubordeaux.miage.s7.poo.projet.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

import java.util.HashMap;
import java.util.Map;

public class ButtonEventHandler implements EventHandler<ActionEvent> {

    private Model model;
    private View view;
    private Map<String, Event> nameToEvent;

    public ButtonEventHandler(Model model) {
        this.model = model;
        this.nameToEvent = new HashMap<>();
        initEventMapping();
    }

    private void initEventMapping() {
        for (Event event : Event.values()) {
            nameToEvent.put(event.name(), event);
        }
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton button = (RadioButton) actionEvent.getSource();
        Event selectedEvent = nameToEvent.get(button.getId());

        // Définir le prochain événement pour modifier l'état
        model.setNextEvent(selectedEvent);
    }

    public void setView(View view) {
        this.view = view;
    }
}
