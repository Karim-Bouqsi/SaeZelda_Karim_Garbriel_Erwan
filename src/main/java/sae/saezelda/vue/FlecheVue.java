package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Fleche;
import sae.saezelda.modele.Link;

import java.util.ArrayList;

public class FlecheVue {
    private int largeur = 11;
    private int hauteur = 3;
    private Fleche fleche;
    private Pane panneauJeu;
    private ImageView flecheImageView;
    private Image flecheImage;
    private TerrainVue terrainVue;
    private Link link;


    public FlecheVue(Fleche fleche, Pane panneauJeu, Link link) {
        this.fleche = fleche;
        this.panneauJeu = panneauJeu;
        this.terrainVue = terrainVue;
        this.link = link;

        Image[] imagesFleches = new Image[4];
        imagesFleches[3] = new Image(String.valueOf(Main.class.getResource("/image/fleche11x3.png")));
        imagesFleches[2] = new Image(String.valueOf(Main.class.getResource("/image/fleche_gauche.png")));
        imagesFleches[0] = new Image(String.valueOf(Main.class.getResource("/image/fleche_haut.png")));
        imagesFleches[1] = new Image(String.valueOf(Main.class.getResource("/image/fleche_bas.png")));

        flecheImageView = new ImageView();

        flecheImageView.setImage(imagesFleches[link.getDirectionValue()]);
        panneauJeu.getChildren().add(flecheImageView);
        flecheImageView.translateXProperty().bind(fleche.xProperty());
        flecheImageView.translateYProperty().bind(fleche.yProperty());

        link.getDirectionProperty().addListener((observable, oldValue, newValue) -> {
            int direction = newValue.intValue();
            System.out.println(newValue.intValue());
            if (direction >= 0 && direction < imagesFleches.length) {
                flecheImageView.setImage(imagesFleches[direction]);
            }
        });

    }

    public void detruireFleches() {
        panneauJeu.getChildren().remove(flecheImageView);
        System.out.println("fleche détruite");
    }

    public Fleche getFleche() {
        return fleche;
    }
}
