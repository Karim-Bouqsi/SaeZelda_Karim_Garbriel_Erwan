package sae.saezelda.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public abstract class Obstacle {
    private static int nextId = 1;
    private final int id;
    private IntegerProperty x;
    private IntegerProperty y;
    private int hauteur;
    private int largeur;
    private Image image;
    private String cheminImage;

// mettre varialbe hauteur/largeur de l'obstacle

    public Obstacle(int x, int y, String cheminImage, int hauteur, int largeur) {
        this.id = nextId++;

        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.image = new Image(cheminImage);
        this.hauteur = hauteur;
        this.largeur = largeur;
    }

    public IntegerProperty getXProperties() {
        return this.x;
    }

    public int getXValue() {
        return this.x.getValue();
    }

    public void setXValue(int x) {
        this.x.setValue(x);
    }

    public IntegerProperty getYProperties() {
        return this.y;
    }

    public int getYValue() {
        return this.y.getValue();
    }

    public void setYValue(int y) {
        this.y.setValue(y);
    }

    public Image getImage() {
        return this.image;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public void move(int x, int y) {
        setXValue(getXValue() + x);
        setYValue(getYValue() + y);
    }

    public double getLargeur() {
        return this.image.getWidth();
    }


    public double getHauteur() {
        return this.image.getHeight();
    }
    public int getHauteurObstacle() {
        return hauteur;
    }
    public int getLargeurObstacle() {
        return largeur;
    }
    public int getId() {
        return id;
    }

}
