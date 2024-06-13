package sae.saezelda.vue;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Direction;
import sae.saezelda.modele.Link;

public class LinkVue {
    private Link link;
    private Pane panneauJeu;
    private TerrainVue terrainVue;
    private ImageView linkImageView;
    private Image[] imagesLink;

    public LinkVue(Link link, Pane panneauJeu, TerrainVue terrainVue) {
        this.link = link;
        this.panneauJeu = panneauJeu;
        this.terrainVue = terrainVue;

        imagesLink = new Image[9];
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
        updateLinkImageView();

        panneauJeu.getChildren().add(linkImageView);
        linkImageView.translateXProperty().bind(link.getXProperties());
        linkImageView.translateYProperty().bind(link.getYProperties());

        link.getDirectionProperty().addListener((observable, oldValue, newValue) -> updateLinkImageView());

        // link.getMortProperty().addListener((observable, oldValue, newValue) -> {
        //     if (newValue) {
        //         linkImageView.setImage(imageLinkMort);
        //     }
        // });
    }

    private void updateLinkImageView() {
        int direction = link.getDirectionValue();
        if (direction >= 0 && direction < imagesLink.length) {
            linkImageView.setImage(imagesLink[direction]);
        }
    }

    public Link getLink() {
        return link;
    }
}
