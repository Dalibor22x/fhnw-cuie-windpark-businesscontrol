package cuie.dalibor22x;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

class CantonSkin extends SkinBase<CantonControl> {

    private TextField editableCanton;
    private Label readOnlyTime;
    private Popup timePopup;
    private Pane timeDropDownChooser;
    private Button timeChooserButton;
    private Button addButton;
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
        editableCanton.setVisible(getSkinnable().getEditableTime());

        readOnlyTime = new Label();
        readOnlyTime.getStyleClass().add("read-only-time");
        readOnlyTime.setVisible(!getSkinnable().getEditableTime());

        label = new Label();
        label.getStyleClass().add("heading");

        addButton = new Button("Add");
        addButton.getStyleClass().add("add-button");

        timeChooserButton = new Button("\u25BC");
        timeChooserButton.getStyleClass().add("chooserButton");
        timeDropDownChooser = new TimeDropDownChooser(getSkinnable());
        timePopup = new Popup();


        timeChooserButton.setVisible(getSkinnable().getEditableTime());
        timeDropDownChooser.setVisible(getSkinnable().getEditableTime());
    }

    private void layoutParts() {
        StackPane timePane = new StackPane(editableCanton, readOnlyTime, timeChooserButton);
        StackPane.setAlignment(readOnlyTime, Pos.CENTER_LEFT);
        StackPane.setAlignment(timeChooserButton, Pos.CENTER_RIGHT);

        VBox content = new VBox(label, timePane, addButton);
        content.setSpacing(10);
        getChildren().addAll(content);

        timePopup.getContent().addAll(timeDropDownChooser);
    }

    private void setupEventHandlers() {
        editableCanton.setOnAction(event -> getSkinnable().convert());

        timeChooserButton.setOnAction(event -> {
            if (timePopup.isShowing()) {
                timePopup.hide();
            } else {
                timePopup.show(editableCanton.getScene().getWindow());
            }
        });

        timePopup.setOnHidden(event -> timeChooserButton.setText("\u25BC"));

        timePopup.setOnShown(event -> {
            timeChooserButton.setText("\u25B2");
            Point2D location = editableCanton.localToScreen(
                    editableCanton.getWidth() - timeDropDownChooser.getPrefWidth() - 3, editableCanton.getHeight() - 3);
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
        getSkinnable().editableTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                editableCanton.setVisible(true);
                readOnlyTime.setVisible(false);
                timeChooserButton.setVisible(true);
                timeDropDownChooser.setVisible(true);

            } else {
                editableCanton.setVisible(false);
                readOnlyTime.setVisible(true);
                timeChooserButton.setVisible(false);
                timeDropDownChooser.setVisible(false);
            }
        });
    }

    private void setupBindings() {
        editableCanton.textProperty().bindBidirectional(getSkinnable().userFacingStringProperty());

        readOnlyTime.textProperty().bind(getSkinnable().actualCantonProperty().asString());

        label.textProperty().bindBidirectional(getSkinnable().labelProperty());
    }
}
