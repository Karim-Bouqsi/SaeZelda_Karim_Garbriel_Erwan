package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.vue.ProjectileVue;

public class MonObservableListeProjectile implements ListChangeListener<Projectile> {

    private Pane panneauJeu;

    public MonObservableListeProjectile(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(Change<? extends Projectile> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for(int i = 0; i < change.getAddedSubList().size(); i++) {
                    creerSprite(change.getAddedSubList().get(i));
                }
            }
            if (change.wasRemoved()) {
                for(int i = 0; i < change.getRemoved().size(); i++) {
                    panneauJeu.getChildren().remove(this.panneauJeu.lookup("#personnage" + change.getRemoved().get(i).getId()));
                }
            }
        }
    }

    private void creerSprite(Projectile projectile) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#personnage" + projectile.getId());
        if (imageView == null) {
            ProjectileVue projectileVue = new ProjectileVue(projectile, panneauJeu);
        }
    }

}