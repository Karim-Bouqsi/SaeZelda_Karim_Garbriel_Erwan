package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Direction;
import sae.saezelda.modele.Fleche;
import sae.saezelda.modele.Link;

import java.util.ArrayList;

public class FlecheVue {
    private Fleche fleche;
    private Pane panneauJeu;
    private ImageView flecheImageView;
    private TerrainVue terrainVue;


    public FlecheVue(Fleche fleche, Pane panneauJeu) {
        this.fleche = fleche;
        this.panneauJeu = panneauJeu;
        this.terrainVue = terrainVue;

        Image[] imagesFleches = new Image[9];
        imagesFleches[Direction.RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/fleche11x3.png")));
        imagesFleches[Direction.LEFT] = new Image(String.valueOf(Main.class.getResource("/image/fleche_gauche.png")));
        imagesFleches[Direction.UP] = new Image(String.valueOf(Main.class.getResource("/image/fleche_haut.png")));
        imagesFleches[Direction.DOWN] = new Image(String.valueOf(Main.class.getResource("/image/fleche_bas.png")));
        imagesFleches[Direction.NEUTRE] = new Image(String.valueOf(Main.class.getResource("/image/fleche_bas.png")));
        imagesFleches[Direction.UP_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/fleche_gauche.png")));
        imagesFleches[Direction.UP_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/fleche_gauche.png")));
        imagesFleches[Direction.DOWN_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/fleche_gauche.png")));
        imagesFleches[Direction.DOWN_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/fleche_gauche.png")));


        flecheImageView = new ImageView();
        flecheImageView.setId("personnage" + fleche.getId());

        flecheImageView.setImage(imagesFleches[fleche.getDirectionProperties().get()]);
        panneauJeu.getChildren().add(flecheImageView);
        flecheImageView.translateXProperty().bind(fleche.getXProperty());
        flecheImageView.translateYProperty().bind(fleche.getYProperty());

    }

    public Fleche getFleche() {
        return fleche;
    }
}
