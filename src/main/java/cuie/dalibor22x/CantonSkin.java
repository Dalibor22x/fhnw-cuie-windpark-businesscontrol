package cuie.dalibor22x;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

class CantonSkin extends SkinBase<CantonControl> {

    private TextField editableCanton;
    private Label readOnlyCanton;
    private Popup timePopup;
    private Region cantonDropDownMap;
    private Button cantonChooserButton;
    private Label label;

    CantonSkin(CantonControl control) {
        super(control);
        initializeSelf();
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        // getSkinnable().loadFonts("/fonts/Lato/Lato-Reg.ttf", "/fonts/Lato/Lato-Lig.ttf");
        getSkinnable().loadFonts("/fonts/Roboto-Light.ttf", "/fonts/Roboto-Bold.ttf");
        getSkinnable().addStylesheetFiles("style.css");
    }

    private void initializeParts() {
        editableCanton = new TextField();
        editableCanton.getStyleClass().add("editable-time");
        editableCanton.setVisible(getSkinnable().getEditableCanton());

        readOnlyCanton = new Label();
        readOnlyCanton.getStyleClass().add("read-only-time");
        readOnlyCanton.setVisible(!getSkinnable().getEditableCanton());

        label = new Label();
        label.getStyleClass().add("heading");

        cantonChooserButton = new Button("\u25BC");
        cantonChooserButton.getStyleClass().add("chooserButton");
        cantonDropDownMap = new CantonDropDownMap(getSkinnable(), CantonControl.getMapHeight(), CantonControl.getMapWidth());
        timePopup = new Popup();


        cantonChooserButton.setVisible(getSkinnable().getEditableCanton());
        cantonDropDownMap.setVisible(getSkinnable().getEditableCanton());
    }

    private void layoutParts() {
        StackPane timePane = new StackPane(editableCanton, readOnlyCanton, cantonChooserButton);
        StackPane.setAlignment(readOnlyCanton, Pos.CENTER_LEFT);
        StackPane.setAlignment(cantonChooserButton, Pos.CENTER_RIGHT);

        VBox content = new VBox(label, timePane);
        content.setSpacing(10);
        getChildren().addAll(content);

        timePopup.getContent().addAll(cantonDropDownMap);
    }

    private void setupEventHandlers() {
        editableCanton.setOnAction(event -> getSkinnable().convert());

        cantonChooserButton.setOnAction(event -> {
            if (timePopup.isShowing()) {
                timePopup.hide();
            } else {
                timePopup.show(editableCanton.getScene().getWindow());
            }
        });

        timePopup.setOnHidden(event -> cantonChooserButton.setText("\u25BC"));

        timePopup.setOnShown(event -> {
            cantonChooserButton.setText("\u25B2");
            Point2D location = editableCanton.localToScreen(editableCanton.getWidth() - cantonDropDownMap.getPrefWidth() - 3, editableCanton.getHeight() - 3);
            timePopup.setX(location.getX());
            timePopup.setY(location.getY());
        });

        editableCanton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            int caretPos = editableCanton.getCaretPosition();

            switch (event.getCode()){
                case ESCAPE:
                    getSkinnable().reset();
                    event.consume();
                    break;
                }
            });
    }

    private void setupValueChangeListeners() {
        getSkinnable().editableCantonProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                editableCanton.setVisible(true);
                readOnlyCanton.setVisible(false);
                cantonChooserButton.setVisible(true);
                cantonDropDownMap.setVisible(true);
            } else {
                editableCanton.setVisible(false);
                readOnlyCanton.setVisible(true);
                cantonChooserButton.setVisible(false);
                cantonDropDownMap.setVisible(false);
            }
        });

        getSkinnable().actualCantonProperty().addListener((observable, oldValue, newValue) -> readOnlyCanton.setText(getSkinnable().getActualCanton().getName()));
    }

    private void setupBindings() {
        editableCanton.textProperty().bindBidirectional(getSkinnable().userFacingStringProperty());

        label.textProperty().bindBidirectional(getSkinnable().labelProperty());
    }
}
