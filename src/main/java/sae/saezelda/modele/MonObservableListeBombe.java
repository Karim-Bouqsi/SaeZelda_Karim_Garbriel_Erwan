package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import sae.saezelda.Main;

public class MonObservableListeBombe implements ListChangeListener<Bombe> {
    private Pane panneauJeu;

    public MonObservableListeBombe(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(Change<? extends Bombe> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for(int i = 0; i < change.getAddedSubList().size(); i++) {
                    creerSprite(change.getAddedSubList().get(i));
                }
            }
            if (change.wasRemoved()) {
                for(int i = 0; i < change.getRemoved().size(); i++) {
                    panneauJeu.getChildren().remove(this.panneauJeu.lookup("#bombe" + change.getRemoved().get(i).getId()));
                }
            }
        }
    }

    private void creerSprite(Bombe bombe) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#bombe" + bombe.getId());
        if (imageView == null) {
            imageView = new ImageView(String.valueOf(Main.class.getResource("/image/bombe.gif")));
            imageView.setId("bombe" + bombe.getId());

            imageView.translateXProperty().bind(bombe.getXProperty());
            imageView.translateYProperty().bind(bombe.getYProperty());

            panneauJeu.getChildren().add(imageView);
        }
    }
}

