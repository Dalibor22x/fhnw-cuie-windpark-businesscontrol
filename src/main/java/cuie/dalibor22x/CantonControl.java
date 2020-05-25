package cuie.dalibor22x;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class CantonControl extends Control {

    private final SkinType skinType;

    private static final PseudoClass MANDATORY_CLASS = PseudoClass.getPseudoClass("mandatory");
    private static final PseudoClass INVALID_CLASS = PseudoClass.getPseudoClass("invalid");
    private static final PseudoClass CONVERTIBLE_CLASS = PseudoClass.getPseudoClass("convertible");

    private final BooleanProperty mandatory = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(MANDATORY_CLASS, get());
        }
    };

    private final BooleanProperty invalidCaption = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(INVALID_CLASS, get());
        }
    };

    private final BooleanProperty invalidTime = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(INVALID_CLASS, get());
        }
    };

    private final BooleanProperty convertible = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            super.invalidated();
            pseudoClassStateChanged(CONVERTIBLE_CLASS, get());
        }
    };

    private final BooleanProperty editableTime = new SimpleBooleanProperty();
    private final BooleanProperty editableCaption = new SimpleBooleanProperty();
    private final StringProperty userFacingString = new SimpleStringProperty();
    private final ObjectProperty<Canton> actualCanton = new SimpleObjectProperty<>();
    private final StringProperty caption = new SimpleStringProperty();
    private final StringProperty label = new SimpleStringProperty();
    private final ObservableList<BacklogEntry> backlog = FXCollections.observableArrayList();


    private static final String CONVERTIBLE_REGEX = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
    private static final Pattern CONVERTIBLE_PATTERN = Pattern.compile(CONVERTIBLE_REGEX);
    private List<Canton> cantons;




    public CantonControl(SkinType skinType) {
        this.skinType = skinType;
        initializeSelf();
        setupValueChangeListener();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return skinType.getFactory().apply(this);
    }

    private void initializeSelf() {
        getStyleClass().add("time-control");

        setupCantons();
    }

    private void setupValueChangeListener() {

        userFacingString.addListener((observable, oldValue, newValue) -> {
            if (CONVERTIBLE_PATTERN.matcher(newValue).matches()) {
                setConvertible(true);
                setInvalidTime(false);
            } else {
                setInvalidTime(true);
                setConvertible(false);
            }
        });
        caption.addListener((observable, oldValue, newValue) -> {
            if (mandatory.get() && "".equals(caption.get())) {
                setInvalidCaption(true);
            } else {
                setInvalidCaption(false);
            }
        });

        actualCanton.addListener((observable, oldValue, newValue) -> {
            setUserFacingString(actualCanton.get().getName());
        });
    }

    public void loadFonts(String... font) {
        for (String f : font) {
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    public void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }


    public void convert() {
        if (!isConvertible()) {
            return;
        }

        for (Canton canton : cantons) {
            if (canton.getShortName().equals(getUserFacingString())) {
                setActualCanton(canton);
                break;
            } else if (canton.getName().startsWith(getUserFacingString())) {
                setActualCanton(canton);
                break;
            }
        }
    }

    public void addToBacklog(String caption, LocalTime time) {
        if (mandatory.get() && "".equals(caption)) return;

        BacklogEntry newEntry = new BacklogEntry(caption, time);
        boolean isUnique = true;

        for (BacklogEntry e : backlog) {
            if (e.equals(newEntry)) {
                isUnique = false;
                break;
            }
        }

        if (isUnique) backlog.add(newEntry);
    }

    public void reset() {
    }


    private void setupCantons() {
        cantons = new LinkedList<>();
        cantons.add(new Canton("BE", "Bern", "", "coats-of-arms/Wappen_Bern_matt.svg"));
        cantons.add(new Canton("ZU", "Zürich", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("LU", "Luzern", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("UR", "Uri", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("SZ", "Schwyz", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("OW", "Obwalden", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("NW", "Nidwalden", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("GL", "Glarus", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("ZG", "Zug", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("FR", "Freiburg", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("SO", "Solothurn", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("BS", "Basel-Stadt", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("BL", "Basel-Landschaft", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("SH", "Schaffhausen", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("AR", "Appenzell Ausserrhoden", "Appenzell A.Rh.", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("AI", "Appenzell Innerrhoden", "Appenzell I.Rh.", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("SG", "Sankt Gallen", "St. Gallen", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("GR", "Graubünden", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("AG", "Aargau", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("TU", "Thurgau", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("TI", "Tessin", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("VD", "Waadt", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("VS", "Wallis", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("NE", "Neuenburg", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("GE", "Genf", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
        cantons.add(new Canton("JU", "Jura", "", "coats-of-arms/Wappen_Zurich_matt.svg"));
    }


    // Getter and Setter

    public boolean getEditableTime() {
        return editableTime.get();
    }

    public BooleanProperty editableTimeProperty() {
        return editableTime;
    }

    public void setEditableTime(boolean editableTime) {
        this.editableTime.set(editableTime);
    }

    public boolean getEditableCaption() {
        return editableCaption.get();
    }

    public BooleanProperty editableCaptionProperty() {
        return editableCaption;
    }

    public void setEditableCaption(boolean editableCaption) {
        this.editableCaption.set(editableCaption);
    }

    public String getUserFacingString() {
        return userFacingString.get();
    }

    public StringProperty userFacingStringProperty() {
        return userFacingString;
    }

    public void setUserFacingString(String userFacingString) {
        this.userFacingString.set(userFacingString);
    }

    public Canton getActualCanton() {
        return actualCanton.get();
    }

    public ObjectProperty<Canton> actualCantonProperty() {
        return actualCanton;
    }

    public void setActualCanton(Canton actualCanton) {
        this.actualCanton.set(actualCanton);
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

    public static PseudoClass getMandatoryClass() {
        return MANDATORY_CLASS;
    }

    public static PseudoClass getInvalidClass() {
        return INVALID_CLASS;
    }

    public static PseudoClass getConvertibleClass() {
        return CONVERTIBLE_CLASS;
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

    public boolean getInvalidTime() {
        return invalidTime.get();
    }

    public BooleanProperty invalidTimeProperty() {
        return invalidTime;
    }

    public void setInvalidTime(boolean invalidTime) {
        this.invalidTime.set(invalidTime);
    }

    public boolean getInvalidCaption() {
        return invalidCaption.get();
    }

    public BooleanProperty invalidCaptionProperty() {
        return invalidTime;
    }

    public void setInvalidCaption(boolean invalidCaption) {
        this.invalidCaption.set(invalidCaption);
    }

    public boolean isConvertible() {
        return convertible.get();
    }

    public BooleanProperty convertibleProperty() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible.set(convertible);
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

    public ObservableList<BacklogEntry> getBacklog() {
        return backlog;
    }


}
