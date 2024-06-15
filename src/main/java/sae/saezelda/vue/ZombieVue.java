package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Zombie;

public class ZombieVue {
    private Zombie zombie;
    private Pane panneauJeu;
    private ImageView imageView;
    private Image normalImage;
    private Image attackImage;

    public ZombieVue(Zombie zombie, Pane panneauJeu) {
        this.zombie = zombie;
        this.panneauJeu = panneauJeu;
        normalImage = new Image(String.valueOf(Main.class.getResource("/image/personnage/zombie19x32.png")));
        attackImage = new Image(String.valueOf(Main.class.getResource("/image/personnage/attaquezombie.gif")));
        Image mortImage = new Image(String.valueOf(Main.class.getResource("/image/personnage/mort_zombie.png")));

        imageView = new ImageView(normalImage);
        imageView.setFitWidth(19);
        imageView.setFitHeight(32);
        imageView.setTranslateX(zombie.getXValue());
        imageView.setTranslateY(zombie.getYValue());

        imageView.setId("personnage" + zombie.getId());

        imageView.translateXProperty().bind(zombie.getXProperties());
        imageView.translateYProperty().bind(zombie.getYProperties());

        zombie.attaqueLinkProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                imageView.setImage(attackImage);
            }
            else {
                imageView.setImage(normalImage);
            }
        });

        zombie.getMortProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                imageView.setImage(mortImage);
            }
        });

        creerZombie();
    }

    public void creerZombie() {
        panneauJeu.getChildren().add(imageView);
    }
}
