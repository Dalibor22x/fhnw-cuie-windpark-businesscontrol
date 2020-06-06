package cuie.dalibor22x;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Canton {

    private final String shortName;
    private final String name;
    private final String nameAbbreviation;
    private final File coatOfArms;
    private final File mapElement;
    private final double xPosition;
    private final double yPosition;
    private final double height;
    private final double width;

    private final ImageView imageView;

    public Canton(String shortName, String name, String nameAbbreviation, File coatOfArms, String mapElementPath, double xPosition, double yPosition, double height, double width) {
        this.shortName = shortName;
        this.name = name;
        this.nameAbbreviation = nameAbbreviation;
        this.coatOfArms = coatOfArms;
        this.mapElement = new File(getClass().getClassLoader().getResource(mapElementPath).getFile());
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
        imageView = new ImageView();
        setupImageView();
    }

    public Canton(String shortName, String name, String nameAbbreviation, String coatOfArmsPath, String mapElementPath, double xPosition, double yPosition, double height, double width) {
        this.shortName = shortName;
        this.name = name;
        this.nameAbbreviation = nameAbbreviation;
        this.coatOfArms = new File(getClass().getClassLoader().getResource(coatOfArmsPath).getFile());
        this.mapElement = new File(getClass().getClassLoader().getResource(mapElementPath).getFile());
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
        imageView = new ImageView();
        setupImageView();
    }

    private void setupImageView() {
        try {
            URL url = getMapElement().toURI().toURL();
            imageView.setImage(new Image(url.toString(), getWidth(), getHeight(), true, true));
            imageView.setLayoutX(getxPosition());
            imageView.setLayoutY(getyPosition());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public ImageView getImageView() {
        return imageView;
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

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
