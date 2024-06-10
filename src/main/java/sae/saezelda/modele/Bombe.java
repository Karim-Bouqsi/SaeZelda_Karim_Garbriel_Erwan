package sae.saezelda.modele;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;


public class Bombe extends Arme {
    private Terrain terrain;
    private IntegerProperty x;
    private IntegerProperty y;
    public Bombe(String nom, int ptAtt, int x, int y, Terrain terrain) {
        super(nom, ptAtt);
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.terrain = terrain;


    }
    public void bombeExplose() {
//        cooldown();
        System.out.println("bombe explose");

    }

//    public void obstacleProximité() {
//        ArrayList<Obstacle> obstacles = terrain.getObstacles();
//
//        for(int i = 0; i < obstacles.size(); i++) {
//            if(obstacles.get(i).getXValue())
//        }
//    }


    public int getXValue() {
        return x.get();
    }

    public IntegerProperty getXProperty() {
        return x;
    }

    public int getYValue() {
        return y.get();
    }

    public IntegerProperty getYProperty() {
        return y;
    }
}
