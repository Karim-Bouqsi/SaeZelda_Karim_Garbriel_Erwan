package sae.saezelda.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

public class Zombie extends Personnage {
    private boolean moveUp = true;
    private BooleanProperty attaqueLink = new SimpleBooleanProperty(false);
    private boolean enCooldown = false;


    public Zombie(Environnement environnement) {
        super("ZombieMan", 400, 110, 20, 32, 19, 4, environnement, 50);
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
        }
        else {
            direction = Direction.DOWN;
        }
        setDirectionValue(direction);
        int[] indiceTab = super.move();
        int newX = indiceTab[0];
        int newY = indiceTab[1];

        if (super.canMove(direction, newX, newY)) {
            setXValue(newX);
            setYValue(newY);
        }
        else {
            moveUp = !moveUp;
        }
    }



    public void deplacerVersLink(int linkX, int linkY) {
        if (!getMortValue()) {
            if (getYValue() == linkY) {
                if (getXValue() < linkX) {
                    setDirectionValue(Direction.RIGHT);
                    if (super.canMove(3, getXValue() + 2, getYValue())) {
                        setXValue(getXValue() + 2);
                    }
                }
                else if (getXValue() > linkX) {
                    setDirectionValue(Direction.LEFT);
                    if (super.canMove(2, getXValue() - 2, getYValue())) {
                        setXValue(getXValue() - 2);
                    }
                }
            }
            else if (getXValue() == linkX) {
                if (getYValue() < linkY) {
                    setDirectionValue(Direction.UP);
                    if (super.canMove(3, getXValue(), getYValue() + 2)) {
                        setYValue(getYValue() + 2);
                    }
                }
                else if (getYValue() > linkY) {
                    setDirectionValue(Direction.DOWN);
                    if (super.canMove(2, getXValue(), getYValue() - 2)) {
                        setYValue(getYValue() - 2);
                    }
                }
            }


            else {
                deplacer();
            }
            attaquerLink();
        }
        else if(getEnvironnement().getLink().getMortValue()) {
            deplacer();
        }
    }

    public void attaquerLink() {
        Environnement environnement = getEnvironnement();
        Link link = environnement.getLink();
        if (!super.getMortValue() && !getEnvironnement().getLink().getMortValue() && !enCooldown) {
            int distanceX = Math.abs(getXValue() - link.getXValue());
            int distanceY = Math.abs(getYValue() - link.getYValue());
            int proximite = 2;

            if (distanceX <= proximite && distanceY <= proximite) {
                attaqueLink.set(true);
                link.recevoirDegats(10);
                demarrerCooldown();
            }
            else {
                attaqueLink.set(false);
            }

        }
        else {
            deplacer();
            attaqueLink.set(false);
        }
    }
    public void demarrerCooldown() {
        enCooldown = true;
        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            enCooldown = false;
        }));
        cooldownTimeline.play();
    }
    public boolean estDansZoneBombe(int bombeX, int bombeY) {
        int zombieX = getXValue();
        int zombieY = getYValue();
        return zombieX - 19 < bombeX + 32 && zombieX + (19 * 2) > bombeX &&
                zombieY - 32 < bombeY + 32 && zombieY + (32 * 2) > bombeY;
    }
}
