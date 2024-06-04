package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import sae.saezelda.Main;
import sae.saezelda.modele.Obstacle;
import sae.saezelda.modele.Terrain;

public class TerrainVue {
    private Terrain terrain;
    private TilePane panneauJeu;

    public TerrainVue(Terrain terrain, TilePane panneauJeu){
        this.terrain = terrain;
        this.panneauJeu = panneauJeu;
        afficherTerrain();
    }
    public void afficherTerrain(){
        panneauJeu.setPrefColumns(20);
        panneauJeu.setMaxWidth(20*32);
        panneauJeu.setPrefRows(10);
        panneauJeu.setMaxHeight(10*32);

        Image eau = new Image(String.valueOf(Main.class.getResource("/image/eau.png")));
        Image sol = new Image(String.valueOf(Main.class.getResource("/image/herbe.png")));
        Image solPierre = new Image(String.valueOf(Main.class.getResource("/image/solPierre.png")));
        Image fleur = new Image(String.valueOf(Main.class.getResource("/image/fleur.png")));
        Image obstaclePierre = new Image(String.valueOf(Main.class.getResource("/image/pierre.png")));

        panneauJeu.getChildren().clear();


        for (int i = 0; i < terrain.getTerrain().length; i++){
            switch (terrain.getTerrain()[i]) {
                case 0 :
                    ImageView ivSol = new ImageView(sol);
                    panneauJeu.getChildren().add(ivSol);
                    break;
                case 1 :
                    ImageView ivEau = new ImageView(eau);
                    panneauJeu.getChildren().add(ivEau);
                    break;

                case 2 :
                    ImageView ivfleur = new ImageView(fleur);
                    panneauJeu.getChildren().add(ivfleur);
                    break;
                case 3 :
                    ImageView ivSolPierre = new ImageView(solPierre);
                    panneauJeu.getChildren().add(ivSolPierre);
                    break;
                case 4 :
                    ImageView ivObstaclePierre = new ImageView(obstaclePierre);
                    panneauJeu.getChildren().add(ivObstaclePierre);
            }
        }

        for (Obstacle obstacle : terrain.getObstacles()) {
            ImageView obstacleView = new ImageView(obstacle.getImage());
            obstacleView.layoutXProperty().bind(obstacle.getXProperties().multiply(32));
            obstacleView.layoutYProperty().bind(obstacle.getYProperties().multiply(32));
            panneauJeu.getChildren().add(obstacleView);
        }
    }
    public void ajouterObstacle(Obstacle obstacle) {
        ImageView obstacleImageView = new ImageView(obstacle.getImage());

        obstacleImageView.translateXProperty().bind(obstacle.getXProperties());
        obstacleImageView.translateYProperty().bind(obstacle.getYProperties());
        panneauJeu.getChildren().add(obstacleImageView);
    }





    public ImageView getImageViewObstacle(Obstacle obstacle) {
        return new ImageView(obstacle.getImage());
    }

    public TilePane getPanneauJeu() {
        return panneauJeu;
    }

}
