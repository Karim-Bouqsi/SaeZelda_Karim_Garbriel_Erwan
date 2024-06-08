package sae.saezelda.vue;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Direction;
import sae.saezelda.modele.Link;

public class LinkVue {
    private Link link;
    private Pane panneauJeu;
    private TerrainVue terrainVue;
    private ImageView[] imageTab;
    private ImageView linkImageView;



    public LinkVue(Link link, Pane panneauJeu, TerrainVue terrainVue) {
        this.link = link;
        this.panneauJeu = panneauJeu;
        this.terrainVue = terrainVue;

        Image[] images = new Image[4];
        images[Direction.DOWN] = new Image(String.valueOf(Main.class.getResource("/image/personnage/persoFace.png")));
        images[Direction.UP] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_haut.png")));
        images[Direction.RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_droite.png")));
        images[Direction.LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_gauche.png")));

        Image imageLinkMort = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_mort.png")));

        linkImageView = new ImageView();
        linkImageView.setFitWidth(19);
        linkImageView.setFitHeight(32);
        linkImageView.setImage(images[link.getDirectionValue()]);

        panneauJeu.getChildren().add(linkImageView);
        linkImageView.translateXProperty().bind(link.getXProperties());
        linkImageView.translateYProperty().bind(link.getYProperties());
//        creeLink();
        link.getDirectionProperty().addListener((observable, oldValue, newValue) -> {
            int direction = newValue.intValue();
            if (direction >= 0 && direction < images.length) {
                linkImageView.setImage(images[direction]);
            }
        });
//        link.getMortProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                System.out.println("bool true");
//                linkImageView.setImage(imageLinkMort);
//            }
//        });
    }

    public void creeLink(){
        panneauJeu.getChildren().add(linkImageView);
    }


    public Link getLink() {
        return link;
    }
}