package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Bombe;

public class BombeVue {
    private Bombe bombe;
    private Pane panneauJeu;
    private ImageView bombeImageView;

    public BombeVue(Bombe bombe, Pane panneauJeu) {
        this.bombe = bombe;
        this.panneauJeu = panneauJeu;

        Image bombeImage = new Image(String.valueOf(Main.class.getResource("/image/bombe.gif")));
        bombeImageView = new ImageView(bombeImage);

        bombeImageView.translateXProperty().bind(bombe.getXProperty());
        bombeImageView.translateYProperty().bind(bombe.getYProperty());
        panneauJeu.getChildren().add(bombeImageView);
    }

    public void detruireBombe() {
        panneauJeu.getChildren().remove(bombeImageView);
    }

    public Bombe getBombe() {
        return bombe;
    }
}
