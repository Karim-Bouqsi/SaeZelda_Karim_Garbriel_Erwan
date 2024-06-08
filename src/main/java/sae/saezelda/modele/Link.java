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

    public Link(Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, terrain, 100);
        this.terrain = terrain;
        this.item = null;
        this.arc = null;
        this.cooldown = 60;
        this.cooldownCompteur = 0;
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





    public void utiliser(Item item){
        if(item != null){
            System.out.println("Link a ramassé " + item.getNom());
            this.item = item;
        }
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
