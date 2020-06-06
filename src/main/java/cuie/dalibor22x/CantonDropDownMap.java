package cuie.dalibor22x;

import javafx.scene.layout.*;


public class CantonDropDownMap extends Region {
    private static final String STYLE_CSS = "dropDownMap.css";

    private final CantonControl cantonControl;

    private final double ARTBOARD_WIDTH;
    private final double ARTBOARD_HEIGHT;

    private final double ASPECT_RATIO;

    private final double MINIMUM_WIDTH;
    private final double MINIMUM_HEIGHT;

    private static final double MAXIMUM_WIDTH = 1800;


    // needed for resizing
    private Pane drawingPane;

    public CantonDropDownMap(CantonControl cantonControl, int mapHeight, int mapWidth) {
        this.cantonControl = cantonControl;
        this.ARTBOARD_HEIGHT = mapHeight;
        this.ARTBOARD_WIDTH = mapWidth;
        MINIMUM_WIDTH = mapWidth;
        ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;
        MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupValueChangeListeners();
        setupEventHandlers();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("dropdown-chooser");
    }

    private void initializeParts() {}


    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void layoutParts() {
        for (Canton canton : cantonControl.getCantons()) {
            drawingPane.getChildren().add(canton.getImageView());
        }

        getChildren().add(drawingPane);
    }


    private void setupValueChangeListeners() {}


    private void setupEventHandlers() {
        for (Canton canton : cantonControl.getCantons()) {
            canton.getImageView().setOnMousePressed((e) -> cantonControl.setActualCanton(canton));
        }
    }

    private void setupBindings() {}
}
