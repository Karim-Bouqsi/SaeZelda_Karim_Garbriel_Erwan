package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import sae.saezelda.vue.CoffreVue;

public class MonObservableListeCoffre implements ListChangeListener<Coffre> {
    private Pane panneauJeu;

    public MonObservableListeCoffre(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(ListChangeListener.Change<? extends Coffre> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for(int i = 0; i < change.getAddedSubList().size(); i++) {
                    creerSprite(change.getAddedSubList().get(i));
                }
            }
            if (change.wasRemoved()) {
                for(int i = 0; i < change.getRemoved().size(); i++) {
                    panneauJeu.getChildren().remove(this.panneauJeu.lookup("#coffre" + change.getRemoved().get(i).getId()));
                }
            }
        }
    }

    private void creerSprite(Coffre coffre) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#coffre" + coffre.getId());
        if (imageView == null) {
            CoffreVue coffreVue = new CoffreVue(coffre, panneauJeu);
        }
    }
}
