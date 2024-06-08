package sae.saezelda.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Zombie extends Personnage {
    private boolean moveUp = true;
    private BooleanProperty attaqueLink = new SimpleBooleanProperty(false);

    public Zombie(Terrain terrain) {
        super("ZombieMan", 400, 110, 20, 32, 19, 4, terrain, 50);
    }

    public IntegerProperty getXProperty() {
        return super.getXProperties();
    }

    public IntegerProperty getYProperty() {
        return super.getYProperties();
    }

    public BooleanProperty attaqueLinkProperty() { return attaqueLink; }


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
        System.out.println(getPvValue());
    }

    public void deplacerVersLink(int linkX, int linkY) {
        if (!super.getMortValue()) {
            if (getYValue() == linkY) {
                if (getXValue() < linkX) {
                    setDirectionValue(Direction.RIGHT);
                    if(super.canMove(3, getXValue() + 2, getYValue())){
                        setXValue(getXValue() + 2);
                    }
                }
                else if (getXValue() > linkX) {
                    setDirectionValue(Direction.LEFT);
                    if(super.canMove(2, getXValue() - 2, getYValue())){
                        setXValue(getXValue() - 2);
                    }
                }
            }
            else {
                deplacer();
            }
            attaquerLink();
        }

    }

    public void attaquerLink() {
        Terrain terrain = getTerrain();
        Link link = terrain.getLink();

        if (!super.getMortValue()) {
            int distanceX = Math.abs(getXValue() - link.getXValue());
            int distanceY = Math.abs(getYValue() - link.getYValue());
            int proximite = 2;

            if (distanceX <= proximite && distanceY <= proximite) {
                attaqueLink.set(true);
                link.recevoirDegats(1);
            }
            else {
                attaqueLink.set(false);
            }
        }

    }



}

