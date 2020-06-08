package cuie.dalibor22x;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

class CantonSkin extends SkinBase<CantonControl> {

    private TextField editableCanton;
    private Label readOnlyCanton;
    private Popup mapPopup;
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
        editableCanton.getStyleClass().add("editable-canton");
        editableCanton.setVisible(getSkinnable().getEditableCanton());

        readOnlyCanton = new Label();
        readOnlyCanton.getStyleClass().add("read-only-canton");
        readOnlyCanton.setVisible(!getSkinnable().getEditableCanton());

        label = new Label();
        label.getStyleClass().add("heading");

        cantonChooserButton = new Button("\u25BC");
        cantonChooserButton.getStyleClass().add("chooserButton");
        cantonDropDownMap = new CantonDropDownMap(getSkinnable(), CantonControl.getMapHeight(), CantonControl.getMapWidth());
        mapPopup = new Popup();

        cantonChooserButton.setVisible(getSkinnable().getEditableCanton());
        cantonDropDownMap.setVisible(getSkinnable().getEditableCanton());
    }

    private void layoutParts() {
        StackPane controlPane = new StackPane(editableCanton, readOnlyCanton, cantonChooserButton);
        StackPane.setAlignment(readOnlyCanton, Pos.TOP_LEFT);
        StackPane.setAlignment(editableCanton, Pos.TOP_LEFT);
        StackPane.setAlignment(cantonChooserButton, Pos.TOP_RIGHT);

        HBox content = new HBox(label, controlPane);
        content.setSpacing(10);
        getChildren().addAll(content);

        mapPopup.getContent().add(cantonDropDownMap);
    }

    private void setupEventHandlers() {
        editableCanton.setOnAction(event -> getSkinnable().convert());

        cantonChooserButton.setOnAction(event -> {
            if (mapPopup.isShowing()) {
                mapPopup.hide();
            } else {
                mapPopup.show(editableCanton.getScene().getWindow());
            }
        });

        mapPopup.setOnHidden(event -> cantonChooserButton.setText("\u25BC"));

        mapPopup.setOnShown(event -> {
            cantonChooserButton.setText("\u25B2");
            Point2D location = editableCanton.localToScreen(editableCanton.getWidth() - cantonDropDownMap.getPrefWidth() - 3, editableCanton.getHeight() - 3);
            mapPopup.setX(location.getX());
            mapPopup.setY(location.getY());
        });

        editableCanton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
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

        getSkinnable().actualCantonProperty().addListener((observable, oldValue, newValue) -> {
            readOnlyCanton.setText(getSkinnable().getActualCanton().getName());
            cantonChooserButton.setGraphic(new ImageView(new Image(getSkinnable().getActualCanton().getCoatOfArms().toURI().toString(), 20, 20, true ,true)));
        });
    }

    private void setupBindings() {
        editableCanton.textProperty().bindBidirectional(getSkinnable().userFacingStringProperty());

        label.textProperty().bindBidirectional(getSkinnable().labelProperty());
    }
}
