package cuie.dalibor22x;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.time.LocalTime;

public class TimeDropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final CantonControl cantonControl;


    public TimeDropDownChooser(CantonControl cantonControl) {
        this.cantonControl = cantonControl;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("dropdown-chooser");
    }

    private void initializeParts() {
    }

    private void layoutParts() {
    }


    private void setupValueChangeListeners() {
    }


    private void setupBindings() {
    }
}
