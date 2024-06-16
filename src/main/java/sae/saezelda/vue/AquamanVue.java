package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Aquaman;

public class AquamanVue {
    private Aquaman aquaman;
    private Pane panneauJeu;
    private ImageView imageView;
    private Image normalImage;

    public AquamanVue(Aquaman aquaman, Pane panneauJeu) {
        this.aquaman = aquaman;
        this.panneauJeu = panneauJeu;
        normalImage = new Image(String.valueOf(Main.class.getResource("/image/personnage/aquaman.jpg")));
        Image mortImage = new Image(String.valueOf(Main.class.getResource("/image/personnage/mort_zombie.png")));

        imageView = new ImageView(normalImage);
        imageView.setFitWidth(19);
        imageView.setFitHeight(32);
        imageView.setTranslateX(aquaman.getXValue());
        imageView.setTranslateY(aquaman.getYValue());

        imageView.setId("personnage" + aquaman.getId());

        imageView.translateXProperty().bind(aquaman.getXProperties());
        imageView.translateYProperty().bind(aquaman.getYProperties());

        aquaman.getMortProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                imageView.setImage(mortImage);
            }
        });

        creerAquaman();
    }

    public void creerAquaman() {
        panneauJeu.getChildren().add(imageView);
    }
}
