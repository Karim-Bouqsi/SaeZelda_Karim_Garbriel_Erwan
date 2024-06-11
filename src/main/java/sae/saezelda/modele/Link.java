package sae.saezelda.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;

public class Link extends Personnage {
    private Terrain terrain;
    private Item item;
    private Arc arc;
    private int cooldown;
    private int cooldownCompteur;
    private BooleanProperty arcEquipe;
    public Link(Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, terrain, 100);
        this.terrain = terrain;
        this.item = null;
        this.arc = null;
        this.cooldown = 60;
        this.cooldownCompteur = 0;
        this.arcEquipe = new SimpleBooleanProperty(false);

    }

    public void tirerAvecArc() {
        if (arc != null && arc.getNombreDeFleches() > 0 && cooldownCompteur == 0 && !getMortValue()) {
            Fleche fleche = new Fleche(getXValue() + getLargeur(), getYValue() + getHauteur() / 2, getDirectionValue(), 5, terrain);
            terrain.ajouterFleche(fleche);

            arc.setNombreDeFleches(arc.getNombreDeFleches() - 1);
            cooldownCompteur = cooldown;
        } else if (arc == null) {
            System.out.println("Tu n'as pas d'arc");
        } else {
            System.out.println("Tu n'as pas de flèche");
        }
    }

    public void decrementCooldown() {
        if (cooldownCompteur > 0) {
            cooldownCompteur--;
        }
    }



    public void linkMove() {
        int[] tabindice = super.move();
        linkVerification(tabindice[0],tabindice[1]);
    }

    public void pousserPierre(int direction, Obstacle pierre) {
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
            case Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT:
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
                case Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT:
                    break;
            }
        }
    }
    public void linkVerification(int x, int y) {

        Obstacle obstacle = recupererObstacle(x, y);
        if (obstacle != null) {
            pousserPierre(getDirectionValue(), obstacle);
        }

        if (detecterPierre(getDirectionValue(), x, y)) {
            System.out.println("Obstacle detecter " + getDirectionValue());
        }

        if (canMove(getDirectionValue(), x, y)) {
            setXValue(x);
            setYValue(y);
        }
        else {
            System.out.println("Collision " + getDirectionValue());
        }

    }





    public void utiliser(Item item){
        if(item != null){
            System.out.println("Link a ramassé " + item.getNom());
            this.item = item;
        }
    }

    public BooleanProperty getArcEquipeProperty() {
        return arcEquipe;
    }

    public boolean arcEquipeValue() {
        return arcEquipe.get();
    }

    public void setArcEquipe(boolean arcEquipe) {
        this.arcEquipe.set(arcEquipe);
    }


    public void desequiperArc() {
        this.arc = null;
        setArcEquipe(false);
    }

    public void equiperArc(Arc arc) {
        this.arc = arc;
    }

    public boolean estDansZone(Coffre coffre){
        return getXValue() - getHauteur() < coffre.getX() + coffre.getLargeur() && getXValue() + (getHauteur() * 2) > coffre.getX() && getYValue() - getHauteur() < coffre.getY() + coffre.getHauteur() && getYValue() + (getHauteur() * 2) > coffre.getY();
    }

    public Terrain getTerrain() {
        return terrain;
    }


}
