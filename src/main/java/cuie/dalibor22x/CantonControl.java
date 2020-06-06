package cuie.dalibor22x;

import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;

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

    private final BooleanProperty invalidCanton = new SimpleBooleanProperty() {
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

    private final BooleanProperty editableCanton = new SimpleBooleanProperty();
    private final StringProperty userFacingString = new SimpleStringProperty();
    private final ObjectProperty<Canton> actualCanton = new SimpleObjectProperty<>();
    private final StringProperty label = new SimpleStringProperty();


    private static final String CONVERTIBLE_REGEX = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
    private static final Pattern CONVERTIBLE_PATTERN = Pattern.compile(CONVERTIBLE_REGEX);
    private List<Canton> cantons;

    private static final int mapHeight = 605;
    private static final int mapWidth = 935;




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
                setInvalidCanton(false);
            } else {
                setInvalidCanton(true);
                setConvertible(false);
            }
        });
        actualCanton.addListener((observable, oldValue, newValue) -> {
            if (mandatory.get() && actualCanton.get() != null) {
                setInvalidCanton(true);
            } else {
                setInvalidCanton(false);
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
        boolean wasConverted = false;

        for (Canton canton : cantons) {
            if (canton.getShortName().toLowerCase().equals(getUserFacingString().toLowerCase())) {
                setActualCanton(canton);
                wasConverted = true;
                break;
            } else if (canton.getName().toLowerCase().startsWith(getUserFacingString().toLowerCase())) {
                setActualCanton(canton);
                wasConverted = true;
                break;
            }
        }

        if (!wasConverted) {
            setConvertible(false);
            setInvalidCanton(true);
        }
    }

    public void reset() {
        setUserFacingString("");
    }


    private void setupCantons() {
        cantons = new LinkedList<>();
        cantons.add(new Canton("BE", "Bern", "", "coats-of-arms/Wappen_Bern_matt.svg", "map/pngs/BE.png", 189.9, 152.23,
                304, 327));
        cantons.add(new Canton("ZH", "Zürich", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/ZH.png", 493.54, 46.68,
                160, 128));
        cantons.add(new Canton("LU", "Luzern", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/LU.png", 388.49, 168.89,
                155, 139));
        cantons.add(new Canton("UR", "Uri", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/UR.png", 504.04, 254.83,
                141, 115));
        cantons.add(new Canton("SZ", "Schwyz", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/SZ.png", 500.77, 186.01,
                102, 126));
        cantons.add(new Canton("OW", "Obwalden", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/OW.png", 430.74, 260.29,
                70, 97));
        cantons.add(new Canton("NW", "Nidwalden", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/NW.png", 466.24, 250.75,
                71, 75));
        cantons.add(new Canton("GL", "Glarus", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/GL.png", 600.05, 200.5,
                113, 78));
        cantons.add(new Canton("ZG", "Zug", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/ZG.png", 501.69, 180.12,
                50, 63));
        cantons.add(new Canton("FR", "Freiburg", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/FR.png", 166.13, 253.87,
                170, 131));
        cantons.add(new Canton("SO", "Solothurn", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/SO.png", 287.46, 105.6,
                129, 141));
        cantons.add(new Canton("BS", "Basel-Stadt", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/BS.png", 330.71, 56,
                43, 29));
        cantons.add(new Canton("BL", "Basel-Landschaft", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/BL.png", 284.68, 87.17,
                69, 130));
        cantons.add(new Canton("SH", "Schaffhausen", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/SH.png", 501.97, 0.05,
                87, 95));
        cantons.add(new Canton("AR", "Appenzell Ausserrhoden", "Appenzell A.Rh.", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/AR.png", 661.98, 109.77,
                69, 90));
        cantons.add(new Canton("AI", "Appenzell Innerrhoden", "Appenzell I.Rh.", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/AI.png", 686.25, 118.75,
                69, 63));
        cantons.add(new Canton("SG", "Sankt Gallen", "St. Gallen", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/SG.png", 582.6, 92.34,
                197, 178));
        cantons.add(new Canton("GR", "Graubünden", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/GR.png", 556.16, 230.12,
                269, 378));
        cantons.add(new Canton("AG", "Aargau", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/AG.png", 362.9, 69.74,
                145, 151));
        cantons.add(new Canton("TG", "Thurgau", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/TG.png", 554.69, 46.15,
                96, 165));
        cantons.add(new Canton("TI", "Tessin", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/TI.png", 503.44, 362.44,
                242, 161));
        cantons.add(new Canton("VD", "Waadt", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/VD.png", 24.25, 261.6,
                225, 245));
        cantons.add(new Canton("VS", "Wallis", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/VS.png", 169.81, 356.29,
                240, 352));
        cantons.add(new Canton("NE", "Neuenburg", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/NE.png", 102.14, 205.49,
                95, 134));
        cantons.add(new Canton("GE", "Genf", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/GE.png", 0.58, 442.35,
                72, 75));
        cantons.add(new Canton("JU", "Jura", "", "coats-of-arms/Wappen_Zurich_matt.svg", "map/pngs/JU.png", 185.67, 104.81,
                107, 147));
    }


    // Getter and Setter

    public boolean getEditableCanton() {
        return editableCanton.get();
    }

    public BooleanProperty editableCantonProperty() {
        return editableCanton;
    }

    public void setEditableCanton(boolean editableCanton) {
        this.editableCanton.set(editableCanton);
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

    public boolean isInvalidCanton() {
        return invalidCanton.get();
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


    public boolean getInvalidCanton() {
        return invalidCanton.get();
    }

    public void setInvalidCanton(boolean invalidCanton) {
        this.invalidCanton.set(invalidCanton);
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

    public static int getMapHeight() {
        return mapHeight;
    }

    public static int getMapWidth() {
        return mapWidth;
    }

    public List<Canton> getCantons() {
        return cantons;
    }
}
