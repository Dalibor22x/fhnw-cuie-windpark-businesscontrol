package cuie.dalibor22x.demo;

import cuie.dalibor22x.Canton;
import javafx.beans.property.*;


public class PresentationModel {
    private final ObjectProperty<Canton> canton = new SimpleObjectProperty<>();
    private final StringProperty label = new SimpleStringProperty("Canton");
    private final BooleanProperty mandatory = new SimpleBooleanProperty(true);
    private final BooleanProperty readOnlyCanton = new SimpleBooleanProperty(false);

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

    public boolean isReadOnlyCanton() {
        return readOnlyCanton.get();
    }

    public BooleanProperty readOnlyCantonProperty() {
        return readOnlyCanton;
    }

    public void setReadOnlyCanton(boolean readOnlyCanton) {
        this.readOnlyCanton.set(readOnlyCanton);
    }
}
