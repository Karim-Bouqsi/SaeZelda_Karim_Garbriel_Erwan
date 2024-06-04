package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Zombie;

public class ZombieVue {
    private Zombie zombie;
    private Pane panneauJeu;
    private TerrainVue terrainVue;
    private ImageView image;


    public ZombieVue(Zombie zombie, Pane panneauJeu, TerrainVue terrainVue) {
        this.zombie = zombie;
        this.panneauJeu = panneauJeu;
        this.terrainVue = terrainVue;
        Image imageZombie = new Image(String.valueOf(Main.class.getResource("/image/personnage/zombie19x32.png")));
        this.image = new ImageView(imageZombie);

        this.image.setFitWidth(19);
        this.image.setFitHeight(32);

        this.image.translateXProperty().bind(zombie.getXProperties());
        this.image.translateYProperty().bind(zombie.getYProperties());



        creerZombie();
    }

    public void creerZombie(){
        panneauJeu.getChildren().add(this.image);
    }
}

