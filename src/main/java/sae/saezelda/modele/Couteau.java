package sae.saezelda.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Couteau extends Arme {

    private Terrain terrain;
    public Couteau(String nom, int ptAtt, Terrain terrain) {
        super(nom, ptAtt);
        this.terrain = terrain;

    }




}
