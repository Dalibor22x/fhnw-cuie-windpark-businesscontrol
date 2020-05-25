package cuie.dalibor22x;

import java.io.File;

public class Canton {

    private final String shortName;
    private final String name;
    private final String nameAbbreviation;
    private final File coatOfArms;
    private final File mapElement;
    private final double xPosition;
    private final double yPosition;

    public Canton(String shortName, String name, String nameAbbreviation, File coatOfArms, String mapElementPath, double xPosition, double yPosition) {
        this.shortName = shortName;
        this.name = name;
        this.nameAbbreviation = nameAbbreviation;
        this.coatOfArms = coatOfArms;
        this.mapElement = new File(getClass().getClassLoader().getResource(mapElementPath).getFile());
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public Canton(String shortName, String name, String nameAbbreviation, String coatOfArmsPath, String mapElementPath, double xPosition, double yPosition) {
        this.shortName = shortName;
        this.name = name;
        this.nameAbbreviation = nameAbbreviation;
        this.coatOfArms = new File(getClass().getClassLoader().getResource(coatOfArmsPath).getFile());
        this.mapElement = new File(getClass().getClassLoader().getResource(mapElementPath).getFile());
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }

    public File getCoatOfArms() {
        return coatOfArms;
    }

    public String getNameAbbreviation() {
        return nameAbbreviation;
    }

    public File getMapElement() {
        return mapElement;
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }
}
