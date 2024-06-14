package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.vue.BombeVue;
import sae.saezelda.vue.PnjVue;

public class MonObservableListePnj implements ListChangeListener<Pnj> {
    private Pane panneauJeu;

    public MonObservableListePnj(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(Change<? extends Pnj> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for(int i = 0; i < change.getAddedSubList().size(); i++) {
                    creerSprite(change.getAddedSubList().get(i));
                }
            }
            if (change.wasRemoved()) {
                for(int i = 0; i < change.getRemoved().size(); i++) {
                    panneauJeu.getChildren().remove(this.panneauJeu.lookup("#pnj" + change.getRemoved().get(i).getId()));
                }
            }
        }
    }

    private void creerSprite(Pnj pnj) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#pnj" + pnj.getId());
        if (imageView == null) {
            PnjVue pnjVue = new PnjVue(panneauJeu, pnj);
        }
    }
}
