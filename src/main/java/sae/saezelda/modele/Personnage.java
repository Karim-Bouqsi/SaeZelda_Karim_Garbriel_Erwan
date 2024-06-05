package sae.saezelda.modele;

import javafx.beans.property.IntegerProperty;
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

    public Personnage(String nom, int positionX, int positionY, int capaciteMax, int hauteur, int largeur, int vitesse, Terrain terrain, int pv) {
        this.nom = nom;
        this.id = "P" + compteur;
        compteur++;
        this.x = new SimpleIntegerProperty(positionX);
        this.y = new SimpleIntegerProperty(positionY);
        this.direction = new SimpleIntegerProperty(Direction.UP);
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.vitesse = vitesse;
        this.terrain = terrain;
        this.pv = new SimpleIntegerProperty(pv);

        direction.addListener((obs, oldVal, newVal) -> {
            int newDirection = newVal.intValue();
//            System.out.println("changement d direction");
            setDirectionValue(newDirection);
        });
    }

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

    public void move() {
        int newX = getXValue();
        int newY = getYValue();

        switch (getDirectionValue()) {
            case Direction.UP:
                newY -= 1;
                break;
            case Direction.DOWN:
                newY += 1;
//                this.setPvValue(getPvValue() - 1);
                break;

            case Direction.LEFT:
                newX -= 1;
                break;
            case Direction.RIGHT:
                newX += 1;
                break;
        }

        Obstacle obstacle = recupererObstacle(newX, newY);
        if (obstacle != null) {
            pousserPierre(getDirectionValue(), obstacle);
        }

        if (detecterPierre(getDirectionValue(), newX, newY)) {
            System.out.println("Obstacle detecter " + getDirectionValue());
        }

        if (canMove(getDirectionValue(), newX, newY)) {
            setXValue(newX);
            setYValue(newY);
        }
        else {
            System.out.println("Collision " + getDirectionValue());
        }
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
            default:
                return false;
        }
    }


    public boolean detecterPierre(int direction, int x, int y) {
        for (Obstacle obstacle : terrain.getObstacles()) {
            switch (direction) {
                case Direction.UP:
//                    System.out.println("x : " + obstacle.getXValue());
//                    System.out.println("y : " + obstacle.getYValue());

//                    if (x + getLargeur() >= obstacle.getXValue() && x <= obstacle.getXValue() + 32 && (y >= obstacle.getYValue() + 32 && y <= obstacle.getYValue())) {
//                        return true;
//                    }
//                    System.out.println("x de link : " + x + " y de link : " + y);
//                    System.out.println("x de lobstacle : " + obstacle.getXValue() + "y de lobs : " + obstacle.getYValue());

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
                case Direction.LEFT:
                    if (x <= obstacle.getXValue() + obstacle.getLargeurObstacle() && x >= obstacle.getXValue() &&
                            y + getHauteur() >= obstacle.getYValue() && y <= obstacle.getYValue()) {
                        return true;
                    }
                    break;
                case Direction.RIGHT:
                    if (x + getLargeur() >= obstacle.getXValue() && x <= obstacle.getXValue() + obstacle.getLargeurObstacle() &&
                            y + getHauteur() >= obstacle.getYValue() && y <= obstacle.getYValue()) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }


    private void pousserPierre(int direction, Obstacle pierre) {
        int newX = pierre.getXValue();
        int newY = pierre.getYValue();

        switch (direction) {
            case Direction.UP:
                newY -= 1;
                break;
            case Direction.DOWN:
                newY += 1;
                break;
            case Direction.LEFT:
                newX -= 1;
                break;
            case Direction.RIGHT:
                newX += 1;
                break;
        }

        if (terrain.nouvellePositionValide(newX, newY)) {
            switch (direction) {
                case Direction.UP:
                    pierre.move(0, -3);
                    break;
                case Direction.DOWN:
                    pierre.move(0, 3);
                    break;
                case Direction.LEFT:
                    pierre.move(-3, 0);
                    break;
                case Direction.RIGHT:
                    pierre.move(3, 0);
                    break;
            }
        }
    }

    public Obstacle recupererObstacle(int x, int y) {
        for (Obstacle obstacle : terrain.getObstacles()) {
            if (detecterPierre(getDirectionValue(), x, y)) {
                return obstacle;
            }
        }
        return null;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Nom : " + this.nom + " id : " + id + " Capacité d'inventaire : " + capaciteMax + " Inventaire : " /*+ inventaire.toString()*/;
    }
}
