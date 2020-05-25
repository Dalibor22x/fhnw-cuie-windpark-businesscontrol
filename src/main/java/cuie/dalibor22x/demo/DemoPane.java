package cuie.dalibor22x.demo;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.dalibor22x.SkinType;
import cuie.dalibor22x.CantonControl;


public class DemoPane extends BorderPane {
    private final PresentationModel pm;

    private CantonControl businessControl;

    private Label cantonLabel;

    private CheckBox readOnlyCantonBox;
    private CheckBox mandatoryBox;
    private TextField labelField;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        businessControl = new CantonControl(SkinType.DEFAULT_TYPE);

        cantonLabel = new Label();
        readOnlyCantonBox = new CheckBox();
        mandatoryBox = new CheckBox();
        labelField = new TextField();
    }

    private void layoutControls() {
        VBox box = new VBox(10, new Label("Canton Control Properties"), cantonLabel,
                            new Label("Canton ReadOnly"), readOnlyCantonBox,
                            new Label("Mandatory"), mandatoryBox,
                            new Label("Label"), labelField);
        box.setPadding(new Insets(10));
        box.setSpacing(10);

        setCenter(businessControl);
        setRight(box);
    }

    private void setupValueChangeListeners() {
        pm.cantonProperty().addListener((observable, oldValue, newValue) -> cantonLabel.setText(pm.getCanton().getName()));
    }

    private void setupBindings() {
        readOnlyCantonBox.selectedProperty().bindBidirectional(pm.readOnlyCantonProperty());
        mandatoryBox.selectedProperty().bindBidirectional(pm.mandatoryProperty());
        labelField.textProperty().bindBidirectional(pm.labelProperty());

        //todo setup bindings to businesscontrol
        businessControl.actualCantonProperty().bindBidirectional(pm.cantonProperty());
        businessControl.labelProperty().bindBidirectional(pm.labelProperty());
        businessControl.mandatoryProperty().bind(pm.mandatoryProperty());
        businessControl.editableCantonProperty().bind(pm.readOnlyCantonProperty().not());
    }

}
