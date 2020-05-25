package cuie.dalibor22x.demo;

import java.time.LocalTime;

import cuie.dalibor22x.BacklogEntry;
import cuie.dalibor22x.Canton;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PresentationModel {
    private final ObjectProperty<Canton> canton = new SimpleObjectProperty<>();
    private final StringProperty label = new SimpleStringProperty("Work Tracker");
    private final StringProperty caption = new SimpleStringProperty("Project xy");
    private final BooleanProperty mandatory = new SimpleBooleanProperty(true);
    private final BooleanProperty readOnlyTime = new SimpleBooleanProperty(false);
    private final BooleanProperty readOnlyCaption = new SimpleBooleanProperty(false);
    private final ObservableList<BacklogEntry> backlog = FXCollections.observableArrayList();

    public Canton getCanton() {
        return canton.get();
    }

    public ObjectProperty<Canton> cantonProperty() {
        return canton;
    }

    public void setCanton(Canton canton) {
        this.canton.set(canton);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public boolean isMandatory() {
        return mandatory.get();
    }

    public BooleanProperty mandatoryProperty() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory.set(mandatory);
    }

    public boolean getReadOnlyTime() {
        return readOnlyTime.get();
    }

    public BooleanProperty readOnlyTimeProperty() {
        return readOnlyTime;
    }

    public void setReadOnlyTime(boolean readOnlyTime) {
        this.readOnlyTime.set(readOnlyTime);
    }
    public boolean getReadOnlyCaption() {
        return readOnlyCaption.get();
    }

    public BooleanProperty readOnlyCaptionProperty() {
        return readOnlyCaption;
    }

    public void setReadOnlyCaption(boolean readOnlyCaption) {
        this.readOnlyCaption.set(readOnlyCaption);
    }

    public String getCaption() {
        return caption.get();
    }

    public StringProperty captionProperty() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }

    public ObservableList<BacklogEntry> getBacklog() {
        return backlog;
    }

}
