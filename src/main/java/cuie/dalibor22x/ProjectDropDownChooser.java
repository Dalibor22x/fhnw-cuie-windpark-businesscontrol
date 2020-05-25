package cuie.dalibor22x;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ProjectDropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final CantonControl cantonControl;

    private TableView<BacklogEntry> listView;
    private TableColumn<BacklogEntry, String> projectColumn;


    public ProjectDropDownChooser(CantonControl cantonControl) {
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
        listView = new TableView();
        listView.getStyleClass().add("list-view");
        projectColumn = new TableColumn<>("Project");
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        projectColumn.setPrefWidth(100);
    }

    private void layoutParts() {
        listView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(listView, Priority.ALWAYS);

        listView.getColumns().addAll(projectColumn);
        getChildren().addAll(listView);
    }

    private void setupValueChangeListeners() {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cantonControl.setCaption(newValue.getProject());
        });
    }

    private void setupBindings() {
        listView.setItems(cantonControl.getBacklog());
    }
}
