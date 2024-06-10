package sae.saezelda.vue;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.modele.Obstacle;

public class ObstacleVue {
    private Obstacle obstacle;
    private Pane panneauJeu;
    private ImageView imageObstacle;

    public ObstacleVue(Pane panneauJeu, Obstacle obstacle) {
        this.obstacle = obstacle;
        this.panneauJeu = panneauJeu;
        this.imageObstacle = new ImageView(obstacle.getImage());


        imageObstacle.translateXProperty().bind(obstacle.getXProperties());
        imageObstacle.translateYProperty().bind(obstacle.getYProperties());

        panneauJeu.getChildren().add(imageObstacle);


    }

}