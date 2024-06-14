package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Obstacle;
import sae.saezelda.modele.Pnj;

public class PnjVue {
    private Pnj pnj;
    private Pane panneauJeu;
    private Image imagePnj;
    private ImageView imageViewPnj;



    public PnjVue(Pane panneauJeu, Pnj pnj) {
        this.pnj = pnj;
        this.panneauJeu = panneauJeu;
        imagePnj = new Image(String.valueOf(Main.class.getResource("/image/personnage/pnj.png")));
        imageViewPnj = new ImageView(imagePnj);
        imageViewPnj.setFitWidth(19);
        imageViewPnj.setFitHeight(32);
        imageViewPnj.setTranslateX(pnj.getXValue());
        imageViewPnj.setTranslateY(pnj.getYValue());
        imageViewPnj.setId("pnj" + pnj.getId());

        imageViewPnj.translateXProperty().bind(pnj.getXProperties());
        imageViewPnj.translateYProperty().bind(pnj.getYProperties());

        panneauJeu.getChildren().add(imageViewPnj);
    }
}
