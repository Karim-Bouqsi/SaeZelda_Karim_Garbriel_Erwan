package sae.saezelda.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Zombie extends Personnage {
    private boolean moveUp = true;
    private BooleanProperty attaqueLink = new SimpleBooleanProperty(false);

    public Zombie(Environnement environnement, Terrain terrain) {
        super("ZombieMan", 400, 110, 20, 32, 19, 4, terrain, environnement, 50);
    }

    public IntegerProperty getXProperty() {
        return super.getXProperties();
    }

    public IntegerProperty getYProperty() {
        return super.getYProperties();
    }

    public BooleanProperty attaqueLinkProperty() {
        return attaqueLink;
    }

    public void deplacer() {
        int direction;
        if (moveUp) {
            direction = Direction.UP;
        } else {
            direction = Direction.DOWN;
        }
        setDirectionValue(direction);
        int[] indiceTab = super.move();
        int newX = indiceTab[0];
        int newY = indiceTab[1];

        if (super.canMove(direction, newX, newY)) {
            setXValue(newX);
            setYValue(newY);
        } else {
            moveUp = !moveUp;
        }
    }

    public void deplacerVersLink(int linkX, int linkY) {
        System.out.println(getMortValue());
        if (!getMortValue()) {
            if (getYValue() == linkY) {
                if (getXValue() < linkX) {
                    setDirectionValue(Direction.RIGHT);
                    if (super.canMove(3, getXValue() + 2, getYValue())) {
                        setXValue(getXValue() + 2);
                    }
                } else if (getXValue() > linkX) {
                    setDirectionValue(Direction.LEFT);
                    if (super.canMove(2, getXValue() - 2, getYValue())) {
                        setXValue(getXValue() - 2);
                    }
                }
            } else {
                deplacer();
            }
            attaquerLink();
        }
//        else if(getMortValue() == true) {
//            getEnvironnement().getZombies().remove(this);
//        }
    }

    public void attaquerLink() {
        Environnement environnement = getEnvironnement();
        Link link = environnement.getLink();

        if (!super.getMortValue()) {
            int distanceX = Math.abs(getXValue() - link.getXValue());
            int distanceY = Math.abs(getYValue() - link.getYValue());
            int proximite = 2;

            if (distanceX <= proximite && distanceY <= proximite) {
                attaqueLink.set(true);
                link.recevoirDegats(1);
            } else {
                attaqueLink.set(false);
            }
        }
    }

    public boolean estDansZoneBombe(int bombeX, int bombeY) {
        int zombieX = getXValue();
        int zombieY = getYValue();
        return zombieX - 19 < bombeX + 32 && zombieX + (19 * 2) > bombeX &&
                zombieY - 32 < bombeY + 32 && zombieY + (32 * 2) > bombeY;
    }
}
