package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.vue.ZombieVue;

public class MonObservableListeZombie implements ListChangeListener<Zombie> {

    private Pane panneauJeu;

    public MonObservableListeZombie(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(Change<? extends Zombie> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for(int i = 0; i < change.getAddedSubList().size(); i++) {
                    creerSprite(change.getAddedSubList().get(i));
                }
            }
            if (change.wasRemoved()) {
                for(int i = 0; i < change.getRemoved().size(); i++) {
                    System.out.println(change.getRemoved().get(i).getId());
                    panneauJeu.getChildren().remove(this.panneauJeu.lookup("#personnage" + change.getRemoved().get(i).getId()));
                }
            }
        }
    }

    private void creerSprite(Zombie zombie) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#personnage" + zombie.getId());
        if (imageView == null) {
            ZombieVue zombieVue = new ZombieVue(zombie, panneauJeu);
        }
    }

}
