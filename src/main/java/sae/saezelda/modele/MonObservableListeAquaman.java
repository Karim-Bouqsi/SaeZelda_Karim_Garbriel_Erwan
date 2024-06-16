package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.vue.AquamanVue;

public class MonObservableListeAquaman implements ListChangeListener<Aquaman> {

    private Pane panneauJeu;

    public MonObservableListeAquaman(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(Change<? extends Aquaman> change) {
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

    private void creerSprite(Aquaman aquaman) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#personnage" + aquaman.getId());
        if (imageView == null) {
            AquamanVue aquamanVue = new AquamanVue(aquaman, panneauJeu);
        }
    }

}