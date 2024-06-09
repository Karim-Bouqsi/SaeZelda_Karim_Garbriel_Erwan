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

        Image[] imagesLink = new Image[9];
        imagesLink[Direction.DOWN] = new Image(String.valueOf(Main.class.getResource("/image/personnage/persoFace.png")));
        imagesLink[Direction.UP] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_haut.png")));
        imagesLink[Direction.RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_droite.png")));
        imagesLink[Direction.LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_gauche.png")));
        imagesLink[Direction.NEUTRE] = new Image(String.valueOf(Main.class.getResource("/image/personnage/persoFace.png")));
        imagesLink[Direction.DOWN_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_gauche.png")));
        imagesLink[Direction.DOWN_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_droite.png")));
        imagesLink[Direction.UP_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_gauche.png")));
        imagesLink[Direction.UP_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/perso_droite.png")));

        


        Image imageLinkMort = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_mort.png")));

        linkImageView = new ImageView();
        linkImageView.setFitWidth(19);
        linkImageView.setFitHeight(32);
        linkImageView.setImage(imagesLink[link.getDirectionValue()]);

        panneauJeu.getChildren().add(linkImageView);
        linkImageView.translateXProperty().bind(link.getXProperties());
        linkImageView.translateYProperty().bind(link.getYProperties());
//        creeLink();
        link.getDirectionProperty().addListener((observable, oldValue, newValue) -> {
            int direction = newValue.intValue();
            if (direction >= 0 && direction < imagesLink.length) {
                linkImageView.setImage(imagesLink[direction]);
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