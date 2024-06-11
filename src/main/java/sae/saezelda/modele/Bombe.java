package sae.saezelda.modele;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;



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
        ObservableList<Obstacle> obstaclesDansZone = estDansZone(terrain.getObstacles());
        System.out.println("bombe explose");
//        System.out.println(terrain.getLink().getXValue());
//        System.out.println(terrain.getLink().getYValue());
//        System.out.println(terrain.getLink().getXValue());
        if (terrain.getLink().estDansZoneBombe(getXValue(), getYValue())) {
            System.out.println("link toucher par la bombe");
            terrain.getLink().recevoirDegats(10);

        }
        terrain.retirerBombe(this);
    }

    public void cooldownBombeEtExplose() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> this.bombeExplose()));
        timeline.play();
    }

    public ObservableList<Obstacle> estDansZone(ObservableList<Obstacle> obstacles){
        ObservableList<Obstacle> obstaclesDansZone = FXCollections.observableArrayList();
        ObservableList<Obstacle> obstaclesASupprimer = FXCollections.observableArrayList();

        for(int i = 0; i < obstacles.size(); i++) {
            if(getXValue() - 32 < obstacles.get(i).getXValue() + obstacles.get(i).getLargeur() && getXValue() + (32 * 2) > obstacles.get(i).getXValue() &&
                    getYValue() - 32 < obstacles.get(i).getYValue() + obstacles.get(i).getHauteur() && getYValue() + (32 * 2) > obstacles.get(i).getYValue()) {

                obstaclesDansZone.add(obstacles.get(i));
                obstaclesASupprimer.add(obstacles.get(i));
                System.out.println("Bombe à proximité");
            }
            else
                System.out.println("trop loin de la pierre");
        }
        obstacles.removeAll(obstaclesASupprimer);

        return obstaclesDansZone;
    }




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
