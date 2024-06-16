package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Projectile;

public class ProjectileVue {
    private Projectile projectile;
    private Pane panneauJeu;
    private ImageView imageView;
    private Image normalImage;

    public ProjectileVue(Projectile projectile, Pane panneauJeu) {
        this.projectile = projectile;
        this.panneauJeu = panneauJeu;
        normalImage = new Image(String.valueOf(Main.class.getResource("/image/personnage/projectile.png")));
        Image mortImage = new Image(String.valueOf(Main.class.getResource("/image/personnage/mort_zombie.png")));

        imageView = new ImageView(normalImage);
        imageView.setFitWidth(19);
        imageView.setFitHeight(32);
        imageView.setTranslateX(projectile.getXValue());
        imageView.setTranslateY(projectile.getYValue());

        imageView.setId("projectile" + projectile.getId());

        imageView.translateXProperty().bind(projectile.getXProperties());
        imageView.translateYProperty().bind(projectile.getYProperties());

        projectile.getMortProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                imageView.setImage(mortImage);
            }
        });

        creerProjectile();
    }

    public void creerProjectile() {
        panneauJeu.getChildren().add(imageView);
    }
}
