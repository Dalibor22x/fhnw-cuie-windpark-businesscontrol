package cuie.dalibor22x;

import java.io.File;

public class Canton {

    private final String shortName;
    private final String name;
    private final String nameAbbreviation;
    private final File coatOfArms;

    public Canton(String shortName, String name, String nameAbbreviation, File coatOfArms) {
        this.shortName = shortName;
        this.name = name;
        this.nameAbbreviation = nameAbbreviation;
        this.coatOfArms = coatOfArms;
    }

    public Canton(String shortName, String name, String nameAbbreviation, String coatOfArmsPath) {
        this.shortName = shortName;
        this.name = name;
        this.nameAbbreviation = nameAbbreviation;
        this.coatOfArms = new File(getClass().getClassLoader().getResource(coatOfArmsPath).getFile());
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
}
