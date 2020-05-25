package cuie.dalibor22x;

import javafx.scene.image.Image;
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
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("dropdown-chooser");
    }

    private void initializeParts() {
    }


    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void layoutParts() {
        for (Canton canton : cantonControl.getCantons()) {
            Pane cantonPane = new Pane();
//            BackgroundImage cantonImage = new BackgroundImage(new Image(canton.getMapElement().getAbsolutePath(),32,32,true,true),
//                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                    BackgroundSize.DEFAULT);
//            cantonPane.setBackground(new Background(cantonImage));

            drawingPane.getChildren().addAll(cantonPane);

            cantonPane.setLayoutX(canton.getxPosition());
            cantonPane.setLayoutY(canton.getyPosition());
        }
    }


    private void setupValueChangeListeners() {
    }


    private void setupBindings() {
    }
}
