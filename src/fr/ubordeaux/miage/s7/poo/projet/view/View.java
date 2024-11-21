package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.model.Event;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View implements Listener {

    private Label label;
    private Map<String, RadioButton> radioButtons = new HashMap<>();

    public View(Stage stage, EventHandler<ActionEvent> eventHandler) {
        stage.setTitle("Gestion des biens immobiliers");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BASELINE_LEFT);
        vBox.setSpacing(10);

        // Label d'état
        label = new Label("État initial : DISPONIBLE");
        label.setFont(new Font("Arial", 20));
        VBox.setMargin(label, new Insets(10, 10, 20, 10));
        vBox.getChildren().add(label);

        // Groupe de boutons pour les événements
        ToggleGroup group = new ToggleGroup();

        for (Event event : Event.values()) {
            RadioButton radioButton = new RadioButton(event.getText());
            radioButton.setToggleGroup(group);
            radioButton.setId(event.name()); // ID correspondant à l'event
            radioButtons.put(event.name(), radioButton);
            radioButton.setOnAction(eventHandler);
            vBox.getChildren().add(radioButton);
        }

        Scene scene = new Scene(vBox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void disableRadioButtons(List<Event> availableEvents) {
        // Activer/désactiver les boutons selon les événements disponibles
        for (RadioButton button : radioButtons.values()) {
            button.setDisable(true);
        }
        for (Event event : availableEvents) {
            radioButtons.get(event.name()).setDisable(false);
        }
    }

    @Override
    public void update(Model model) {
        // Mettre à jour le label avec l'état actuel
        setLabel("État actuel : " + model.getCurrentState().getName());

        // Désactiver/activer les boutons selon les événements disponibles
        disableRadioButtons(model.getCurrentState().getAvailableEvents());
    }
}
