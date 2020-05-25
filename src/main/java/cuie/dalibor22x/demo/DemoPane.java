package cuie.dalibor22x.demo;

import java.time.LocalTime;

import cuie.dalibor22x.BacklogEntry;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import cuie.dalibor22x.SkinType;
import cuie.dalibor22x.CantonControl;


public class DemoPane extends BorderPane {
    private final PresentationModel pm;

    private CantonControl businessControl;

    private Label timeLabel;

    private CheckBox readOnlyTimeBox;
    private CheckBox readOnlyCaptionBox;
    private CheckBox mandatoryBox;
    private TextField labelField;
    private TextField captionField;

    private TableView<BacklogEntry> listView;
    private TableColumn<BacklogEntry, String> projectColumn;
    private TableColumn<BacklogEntry, LocalTime> timeColumn;

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

        timeLabel    = new Label();
        readOnlyTimeBox = new CheckBox();
        readOnlyCaptionBox = new CheckBox();
        mandatoryBox = new CheckBox();
        labelField   = new TextField();
        captionField = new TextField();
        listView = new TableView();
        projectColumn = new TableColumn<>("Project");
        timeColumn = new TableColumn<>("Time");
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        projectColumn.setPrefWidth(100);
        timeColumn.setPrefWidth(100);
    }

    private void layoutControls() {
        listView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        listView.getColumns().addAll(projectColumn, timeColumn);
        VBox box = new VBox(10, new Label("Time Control Properties"),
                            timeLabel,
                            new Label("Time ReadOnly"), readOnlyTimeBox,
                            new Label("Caption ReadOnly"), readOnlyCaptionBox,
                            new Label("Mandatory"), mandatoryBox,
                            new Label("Label"), labelField,
                            new Label("Caption"), captionField,
                            new Label("Backlog"), listView);
        box.setPadding(new Insets(10));
        box.setSpacing(10);

        setCenter(businessControl);
        setRight(box);
    }

    private void setupValueChangeListeners() {

        // "Binding" of businessControl backlog to pm backlog
        businessControl.getBacklog().addListener((ListChangeListener<BacklogEntry>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    pm.getBacklog().addAll(c.getAddedSubList());
                }
                if (c.wasRemoved()) {
                    pm.getBacklog().removeAll(c.getRemoved());
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pm.setCaption(newValue.getProject());
        });

        pm.cantonProperty().addListener((observable, oldValue, newValue) -> timeLabel.setText(pm.getCanton().getName()));
    }

    private void setupBindings() {
        readOnlyTimeBox.selectedProperty().bindBidirectional(pm.readOnlyTimeProperty());
        readOnlyCaptionBox.selectedProperty().bindBidirectional(pm.readOnlyCaptionProperty());
        mandatoryBox.selectedProperty().bindBidirectional(pm.mandatoryProperty());
        labelField.textProperty().bindBidirectional(pm.labelProperty());
        captionField.textProperty().bindBidirectional(pm.captionProperty());

        listView.setItems(pm.getBacklog());

        //todo setup bindings to businesscontrol
        businessControl.actualCantonProperty().bindBidirectional(pm.cantonProperty());
        businessControl.captionProperty().bindBidirectional(pm.captionProperty());
        businessControl.labelProperty().bindBidirectional(pm.labelProperty());
        businessControl.mandatoryProperty().bind(pm.mandatoryProperty());
        businessControl.editableTimeProperty().bind(pm.readOnlyTimeProperty().not());
        businessControl.editableCaptionProperty().bind(pm.readOnlyCaptionProperty().not());
    }

}
