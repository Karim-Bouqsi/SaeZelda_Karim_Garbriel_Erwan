package sae.saezelda.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Fleche {
    private static int nextId = 1;
    private final int id;
    private IntegerProperty x;
    private IntegerProperty y;
    private IntegerProperty direction;
    private int vitesse;
    private boolean toucher;
    private Environnement environnement;

    public Fleche(int x, int y, int direction, int vitesse, Environnement environnement) {
        this.environnement = environnement;
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.direction = new SimpleIntegerProperty(direction);
        this.vitesse = vitesse;
        this.toucher = false;
        this.id = nextId++;
    }

    public void deplacer() {
        switch (direction.getValue()) {
            case Direction.UP:
                setY(getY() - vitesse);
                break;
            case Direction.DOWN, Direction.NEUTRE:
                setY(getY() + vitesse);
                break;
            case Direction.LEFT:
                setX(getX() - vitesse);
                break;
            case Direction.RIGHT:
                setX(getX() + vitesse);
                break;
            case Direction.UP_LEFT:
                setX(getX() - vitesse);
                setY(getY() - vitesse);
                break;
            case Direction.UP_RIGHT:
                setX(getX() + vitesse);
                setY(getY() - vitesse);
                break;
            case Direction.DOWN_LEFT:
                setX(getX() - vitesse);
                setY(getY() + vitesse);
                break;
            case Direction.DOWN_RIGHT:
                setX(getX() + vitesse);
                setY(getY() + vitesse);
                break;
        }
        toucheCible();
    }

    public boolean toucheCible() {
        if (toucher) {
            return false;
        }
        for (Zombie zombie : environnement.getZombies()) {
            boolean toucher = false;
            switch (direction.get()) {
                case Direction.RIGHT, Direction.DOWN_RIGHT, Direction.UP_RIGHT:
                    if (getX() + getLargeur() >= zombie.getXValue() && getX() + getLargeur() <= zombie.getXValue() + 19 &&
                            getY() >= zombie.getYValue() && getY() <= zombie.getYValue() + 32) {
                        zombie.setXValue(zombie.getXValue() + 30);
                        toucher = true;
                    }
                    break;
                case Direction.LEFT, Direction.DOWN_LEFT, Direction.UP_LEFT:
                    if (getX() >= zombie.getXValue() && getX() <= zombie.getXValue() + 19 &&
                            getY() >= zombie.getYValue() && getY() <= zombie.getYValue() + 32) {
                        zombie.setXValue(zombie.getXValue() - 30);
                        toucher = true;
                    }
                    break;
                case Direction.UP:
                    if (getX() >= zombie.getXValue() && getX() <= zombie.getXValue() + 19 &&
                            getY() >= zombie.getYValue() && getY() <= zombie.getYValue() + 12) {
                        zombie.setYValue(zombie.getYValue() - 30);
                        toucher = true;
                    }
                    break;
                case Direction.DOWN, Direction.NEUTRE:
                    if (getX() >= zombie.getXValue() && getX() <= zombie.getXValue() + 19 &&
                            getY() + 12 >= zombie.getYValue() && getY() + 12 <= zombie.getYValue() + 19) {
                        zombie.setYValue(zombie.getYValue() + 30);
                        toucher = true;
                    }
                    break;
            }
            if (toucher) {
                this.toucher = true;
                zombie.recevoirDegats(25);
                return true;
            }
        }
        return false;
    }
    public boolean aDepasseLimites() {
        return x.get() + 11 < 0 || x.get() > 650 || y.get() < 0 || y.get() > 330;
    }

    public int getLargeur() {
        return 11;
    }
    public int getId() {
        return id;
    }
    public IntegerProperty getDirectionProperties() {
        return direction;
    }
    public int getX() {
        return x.get();
    }
    public void setX(int x) {
        this.x.set(x);
    }
    public IntegerProperty getXProperty() {
        return x;
    }
    public int getY() {
        return y.get();
    }
    public void setY(int y) {
        this.y.set(y);
    }
    public IntegerProperty getYProperty() {
        return y;
    }
}
