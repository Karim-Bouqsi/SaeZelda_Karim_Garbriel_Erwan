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
    private Image[] imagesLinkArc;
    private Image[] imagesLinkCouteau;



    public LinkVue(Link link, Pane panneauJeu, TerrainVue terrainVue) {
        this.link = link;
        this.panneauJeu = panneauJeu;
        this.terrainVue = terrainVue;

        imagesLink = new Image[9];
        imagesLink[Direction.DOWN] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkbas.gif")));
        imagesLink[Direction.UP] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkhaut.gif")));
        imagesLink[Direction.RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkdroit.gif")));
        imagesLink[Direction.LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkgauche.gif")));
        imagesLink[Direction.NEUTRE] = new Image(String.valueOf(Main.class.getResource("/image/personnage/persoFace.png")));
        imagesLink[Direction.DOWN_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkgauche.gif")));
        imagesLink[Direction.DOWN_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkdroit.gif")));
        imagesLink[Direction.UP_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkgauche.gif")));
        imagesLink[Direction.UP_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/linkdroit.gif")));

        imagesLinkArc = new Image[9];
        imagesLinkArc[Direction.DOWN] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_bas.png")));
        imagesLinkArc[Direction.UP] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_haut.png")));
        imagesLinkArc[Direction.RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_droit.png")));
        imagesLinkArc[Direction.LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_gauche.png")));
        imagesLinkArc[Direction.NEUTRE] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_bas.png")));
        imagesLinkArc[Direction.DOWN_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_gauche.png")));
        imagesLinkArc[Direction.DOWN_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_droit.png")));
        imagesLinkArc[Direction.UP_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_gauche.png")));
        imagesLinkArc[Direction.UP_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_arc_droit.png")));

        imagesLinkCouteau = new Image[9];
        imagesLinkCouteau[Direction.DOWN] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaubas.gif")));
        imagesLinkCouteau[Direction.UP] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteauhaut.gif")));
        imagesLinkCouteau[Direction.RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaudroit.gif")));
        imagesLinkCouteau[Direction.LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaugauche.gif")));
        imagesLinkCouteau[Direction.NEUTRE] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaubas.gif")));
        imagesLinkCouteau[Direction.DOWN_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaugauche.gif")));
        imagesLinkCouteau[Direction.DOWN_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaudroit.gif")));
        imagesLinkCouteau[Direction.UP_LEFT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaugauche.gif")));
        imagesLinkCouteau[Direction.UP_RIGHT] = new Image(String.valueOf(Main.class.getResource("/image/personnage/couteaudroit.gif")));

        Image imageLinkMort = new Image(String.valueOf(Main.class.getResource("/image/personnage/link_mort.png")));

        linkImageView = new ImageView();
        linkImageView.setFitWidth(19);
        linkImageView.setFitHeight(32);
        updateLinkImageView();

        panneauJeu.getChildren().add(linkImageView);
        linkImageView.translateXProperty().bind(link.getXProperties());
        linkImageView.translateYProperty().bind(link.getYProperties());

        link.getDirectionProperty().addListener((observable, oldValue, newValue) -> updateLinkImageView());

         link.getMortProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue) {
                 linkImageView.setFitWidth(32);
                 linkImageView.setFitHeight(13);
                 linkImageView.setImage(imageLinkMort);
             }
         });
        link.getArcEquipeProperty().addListener((observable, oldValue, newValue) -> updateLinkImageView());
        link.getAttaqueCouteauProperty().addListener((observable, oldValue, newValue) -> {
            updateLinkImageView();
        });

    }

    private void updateLinkImageView() {
        int direction = link.getDirectionValue();
        if (direction >= 0 && direction < imagesLink.length && !link.getMortValue()) {
            if (link.getArcEquiperValue()) {
                    linkImageView.setImage(imagesLinkArc[direction]);
            }
            if (link.getCouteauAttaqueValue()) {
                linkImageView.setImage(imagesLinkCouteau[direction]);
            }
            else {
                linkImageView.setImage(imagesLink[direction]);
            }
        }
    }

    public Link getLink() {
        return link;
    }
}
