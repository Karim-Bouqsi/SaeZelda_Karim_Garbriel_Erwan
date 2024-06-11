package sae.saezelda.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Personnage {

    private String nom;
    public static int compteur = 0;
    private String id;
    private int vitesse;
    private Terrain terrain;
    private int marge = 5;
    private int capaciteMax;
    private IntegerProperty pv;
    private IntegerProperty x;
    private IntegerProperty y;
    private IntegerProperty direction;
    private int largeur;
    private int hauteur;
    private BooleanProperty mort;


    public Personnage(String nom, int positionX, int positionY, int capaciteMax, int hauteur, int largeur, int vitesse, Terrain terrain, int pv) {
        this.nom = nom;
        this.id = "P" + compteur;
        compteur++;
        this.x = new SimpleIntegerProperty(positionX);
        this.y = new SimpleIntegerProperty(positionY);
        this.direction = new SimpleIntegerProperty(Direction.NEUTRE);
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.vitesse = vitesse;
        this.terrain = terrain;
        this.pv = new SimpleIntegerProperty(pv);
        this.mort = new SimpleBooleanProperty(false);

        direction.addListener((obs, oldVal, newVal) -> {
            int newDirection = newVal.intValue();
//            System.out.println("changement d direction");
            setDirectionValue(newDirection);
        });
    }
    public void setMortValue(boolean bool) {
        mort.set(bool);
    }



    public BooleanProperty getMortProperty() {
        return mort;
    }

    public boolean getMortValue() {
        return mort.get();
    }

    public void recevoirDegats(int degats) {
        setPvValue(getPvValue() - degats);
        if (getPvValue() <= 0) {
            mourir();
        }
    }
    public void tuer() {
        this.mort.set(true);
        setPvValue(0);
    }

    public void mourir() {
        tuer();
    }

    public int[] move() {

        int[] indicetab = new int[2];
        int newX = getXValue();
        int newY = getYValue();
        indicetab[0] = newX;
        indicetab[1] = newY;

        switch (getDirectionValue()) {
            case Direction.UP:
                newY -= 1;
                indicetab[1] = newY;
                break;
            case Direction.DOWN:
                newY += 1;
                indicetab[1] = newY;
                break;

            case Direction.LEFT:
                newX -= 1;
                indicetab[0] = newX;
                break;
            case Direction.RIGHT:
                newX += 1;
                indicetab[0] = newX;
                break;
            case Direction.UP_LEFT:
                newY -= 1;
                newX -= 1;
                indicetab[0] = newX;
                indicetab[1] = newY;
                break;
            case Direction.UP_RIGHT:
                newY -= 1;
                newX += 1;
                indicetab[0] = newX;
                indicetab[1] = newY;
                break;
            case Direction.DOWN_LEFT:
                newY += 1;
                newX -= 1;
                indicetab[0] = newX;
                indicetab[1] = newY;
                break;
            case Direction.DOWN_RIGHT:
                newY += 1;
                newX += 1;
                indicetab[0] = newX;
                indicetab[1] = newY;
                break;
            case Direction.NEUTRE:
                break;

        }
        return indicetab;
    }


    public boolean canMove(int direction, int x, int y) {
        switch (direction) {
            case Direction.UP:
                return terrain.estDansLesLimites(x, y) &&
                        !terrain.estObstacle(x, y + getHauteur() - marge) && // haut gauche
                        !terrain.estObstacle(x + getLargeur(), y + getHauteur() - marge) && // haut droite
                        !detecterPierre(direction, x, y);
            case Direction.DOWN:
                return terrain.estDansLesLimites(x, y) &&
                        !terrain.estObstacle(x, y + getHauteur()) && // bas gauche
                        !terrain.estObstacle(x + getLargeur(), y + getHauteur()) && // bas droite
                        !detecterPierre(direction, x, y);
            case Direction.LEFT:
                return terrain.estDansLesLimites(x, y) &&
                        !terrain.estObstacle(x, y + getHauteur() - 1) && // gauche haut
                        !detecterPierre(direction, x, y);
            case Direction.RIGHT:
                return terrain.estDansLesLimites(x, y) &&
                        !terrain.estObstacle(x + getLargeur(), y + getHauteur() - marge) && // droite bas
                        !detecterPierre(direction, x, y);
            case Direction.UP_LEFT:
                return terrain.estDansLesLimites(x - 1, y - 1) &&
                        !terrain.estObstacle(x - 1, y - 1 + getHauteur() - marge) &&
                        !terrain.estObstacle(x - 1 + getLargeur(), y - 1 + getHauteur() - marge) &&
                        !terrain.estObstacle(x - 1, y - 1) &&
                        !detecterPierre(direction, x - 1, y - 1);
            case Direction.UP_RIGHT:
                return terrain.estDansLesLimites(x + 1, y - 1) &&
                        !terrain.estObstacle(x + 1, y - 1 + getHauteur() - marge) &&
                        !terrain.estObstacle(x + 1 + getLargeur(), y - 1 + getHauteur() - marge) &&
                        !terrain.estObstacle(x + 1, y - 1) &&
                        !detecterPierre(direction, x + 1, y - 1);
            case Direction.DOWN_LEFT:
                return terrain.estDansLesLimites(x - 1, y + 1) &&
                        !terrain.estObstacle(x - 1, y + 1 + getHauteur()) &&
                        !terrain.estObstacle(x - 1 + getLargeur(), y + 1 + getHauteur()) &&
                        !terrain.estObstacle(x - 1, y + 1) &&
                        !detecterPierre(direction, x - 1, y + 1);
            case Direction.DOWN_RIGHT:
                return terrain.estDansLesLimites(x + 1, y + 1) &&
                        !terrain.estObstacle(x + 1, y + 1 + getHauteur()) &&
                        !terrain.estObstacle(x + 1 + getLargeur(), y + 1 + getHauteur()) &&
                        !terrain.estObstacle(x + 1, y + 1) &&
                        !detecterPierre(direction, x + 1, y + 1);
            case Direction.NEUTRE:
                return true;
            default:
                return false;
        }
    }


    public boolean detecterPierre(int direction, int x, int y) {
        for (Obstacle obstacle : terrain.getObstacles()) {
            switch (direction) {
                case Direction.UP:
                    if(x >= obstacle.getXValue() && x <= obstacle.getXValue() + obstacle.getLargeurObstacle() && y >= obstacle.getYValue() && y <= obstacle.getYValue()) {
                        return true;
                    }
                    break;
                case Direction.DOWN:
                    if (x + getLargeur() >= obstacle.getXValue() && x <= obstacle.getXValue() + obstacle.getLargeurObstacle() &&
                            y + getHauteur() >= obstacle.getYValue() && y + getHauteur() <= obstacle.getYValue() + obstacle.getHauteurObstacle()) {
                        return true;
                    }
                    break;
                case Direction.LEFT, Direction.DOWN_LEFT, Direction.UP_LEFT:
                    if (x <= obstacle.getXValue() + obstacle.getLargeurObstacle() && x >= obstacle.getXValue() &&
                            y + getHauteur() >= obstacle.getYValue() && y <= obstacle.getYValue()) {
                        return true;
                    }
                    break;
                case Direction.RIGHT, Direction.DOWN_RIGHT, Direction.UP_RIGHT:
                    if (x + getLargeur() >= obstacle.getXValue() && x <= obstacle.getXValue() + obstacle.getLargeurObstacle() &&
                            y + getHauteur() >= obstacle.getYValue() && y <= obstacle.getYValue()) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }




    public Obstacle recupererObstacle(int x, int y) {
        for (Obstacle obstacle : terrain.getObstacles()) {
            if (detecterPierre(getDirectionValue(), x, y)) {
                return obstacle;
            }
        }
        return null;
    }



                                    /* GETTEUR / SETTEUR */
    public String getNom() {return nom;}
    public IntegerProperty getPvProperties() {return pv;}
    public int getPvValue() {return pv.getValue();}
    public void setPvValue(int pv) {this.pv.setValue(pv);}

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

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public IntegerProperty getDirectionProperty() {
        return this.direction;
    }

    public int getDirectionValue() {
        return this.direction.getValue();
    }

    public void setDirectionValue(int direction) {
        this.direction.setValue(direction);
    }

    public Terrain getTerrain() {
        return terrain;
    }


    @Override
    public String toString() {
        return "Nom : " + this.nom + " id : " + id + " Capacité d'inventaire : " + capaciteMax + " Inventaire : " /*+ inventaire.toString()*/;
    }
}
