package sae.saezelda.modele;

import javafx.beans.property.IntegerProperty;

public class Zombie extends Personnage {
    private Terrain terrain;  // TODO attention : déjà dans la super classe
    private boolean moveUp = true;

    public Zombie(Terrain terrain) {
        super("ZombieMan", 400, 110, 20, 32, 19, 4, terrain, 50);
        this.terrain = terrain;
    }

    public IntegerProperty getXProperty() {
        return super.getXProperties();
    }

    public IntegerProperty getYProperty() {
        return super.getYProperties();
    }


    public void deplacer() {
        int direction;
        if (moveUp) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }

        setDirectionValue(direction);
        super.move();

        if ((moveUp && !super.canMove(direction, getXValue(), getYValue() - 1)) ||
                (!moveUp && !super.canMove(direction, getXValue(), getYValue() + 1))) {
            moveUp = !moveUp;
        }
    }

    public void deplacerVersLink(int linkX, int linkY) {
//        System.out.println(terrain.getLink().getXValue());
//        System.out.println(terrain.getLink().getYValue());

        if (getXValue() == linkX) {
            if (getYValue() < linkY) {
                setDirectionValue(Direction.DOWN);
                setYValue(getYValue() + 2);
            }
            else if (getYValue() > linkY) {
                setDirectionValue(Direction.UP);
                setYValue(getYValue() - 2);
            }
        }
        else if (getYValue() == linkY) {
            if (getXValue() < linkX) {
                setDirectionValue(Direction.RIGHT);
                setXValue(getXValue() + 2);
            }
            else if (getXValue() > linkX) {
                setDirectionValue(Direction.LEFT);
                setXValue(getXValue() - 2);
            }
        }
        else {
            deplacer();
        }
    }

    private boolean detecterLink(int x, int y) {
        return getYValue() == y && getXValue() == x;
    }
}
