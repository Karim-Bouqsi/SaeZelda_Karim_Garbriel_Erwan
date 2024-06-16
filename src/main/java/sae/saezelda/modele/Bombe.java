package sae.saezelda.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class Bombe extends Arme {
    private Environnement environnement;
    private IntegerProperty x;
    private IntegerProperty y;

    public Bombe(String nom, int ptAtt, int x, int y, Environnement environnement) {
        super(nom, ptAtt);
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.environnement = environnement;
    }

    public void bombeExplose() {
        ObservableList<Obstacle> obstaclesDansZone = estDansZone(environnement.getObstacles());
        System.out.println("bombe explose");
        if (environnement.getLink().estDansZoneBombe(getXValue(), getYValue())) {
            System.out.println("link toucher par la bombe");
            environnement.getLink().recevoirDegats(10);
        }
        for (Zombie zombie : environnement.getZombies()) {
            if (zombie.estDansZoneBombe(getXValue(), getYValue())) {
                System.out.println("zombie toucher par la bombe");
                zombie.recevoirDegats(100);
            }
        }
        environnement.retirerBombe(this);
    }

    public void cooldownBombeEtExplose() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> this.bombeExplose()));
        timeline.play();
    }

    public ObservableList<Obstacle> estDansZone(ObservableList<Obstacle> obstacles) {
        ObservableList<Obstacle> obstaclesDansZone = FXCollections.observableArrayList();
        ObservableList<Obstacle> obstaclesASupprimer = FXCollections.observableArrayList();

        for (Obstacle obstacle : obstacles) {
            if (getXValue() - 32 < obstacle.getXValue() + obstacle.getLargeur() && getXValue() + (32 * 2) > obstacle.getXValue() &&
                    getYValue() - 32 < obstacle.getYValue() + obstacle.getHauteur() && getYValue() + (32 * 2) > obstacle.getYValue()) {
                obstaclesDansZone.add(obstacle);
                obstaclesASupprimer.add(obstacle);
                System.out.println("Bombe à proximité");
            } else {
                System.out.println("trop loin de la pierre");
            }
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