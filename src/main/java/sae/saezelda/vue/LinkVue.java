package sae.saezelda.vue;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Link;

public class LinkVue {
    private Link link;
    private Pane panneauJeu;
    private TerrainVue terrainVue;
    private ImageView[] imageTab;
//    private Circle linkCircle;


    public LinkVue(Link link, Pane panneauJeu, TerrainVue terrainVue) {
        this.link = link;
        this.panneauJeu = panneauJeu;
        this.terrainVue = terrainVue;

        Image[] images = new Image[4];
        images[0] = new Image(String.valueOf(Main.class.getResource("/image/personnage/persoFace.png")));
        images[1] = new Image(String.valueOf(Main.class.getResource("/image/personnage/persoDos.png")));
        images[2] = new Image(String.valueOf(Main.class.getResource("/image/personnage/persoDroit.png")));
        images[3] = new Image(String.valueOf(Main.class.getResource("/image/personnage/deplacementGauche.gif")));

        imageTab = new ImageView[4];
        for (int i = 0; i < 4; i++) {
            imageTab[i] = new ImageView(images[i]);
            imageTab[i].setFitWidth(19);
            imageTab[i].setFitHeight(32);
            imageTab[i].translateXProperty().bind(link.getXProperties());
            imageTab[i].translateYProperty().bind(link.getYProperties());
        }


        creeLink();
    }

    public void creeLink(){
        panneauJeu.getChildren().add(imageTab[0]);
    }

    public Link getLink() {
        return link;
    }
}