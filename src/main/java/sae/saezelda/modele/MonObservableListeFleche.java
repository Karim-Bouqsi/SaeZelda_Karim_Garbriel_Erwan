package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import sae.saezelda.Main;
import sae.saezelda.vue.FlecheVue;

public class MonObservableListeFleche implements ListChangeListener<Fleche> {
    private Pane panneauJeu;

    public MonObservableListeFleche(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(Change<? extends Fleche> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for(int i = 0; i < change.getAddedSubList().size(); i++) {
                    creerSprite(change.getAddedSubList().get(i));
                }
            }
            if (change.wasRemoved()) {
                for(int i = 0; i < change.getRemoved().size(); i++) {
                    panneauJeu.getChildren().remove(this.panneauJeu.lookup("#fleche" + change.getRemoved().get(i).getId()));
                }
            }
        }
    }

    private void creerSprite(Fleche fleche) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#fleche" + fleche.getId());
        if (imageView == null) {
            FlecheVue flecheVue = new FlecheVue(fleche, panneauJeu);
        }
    }

    private String obtenirImageFlecheParDirection(int direction) {
        String imagePath = "";
        switch (direction) {
            case Direction.UP:
                imagePath = "/image/fleche_haut.png";
                break;
            case Direction.DOWN, Direction.NEUTRE:
                imagePath = "/image/fleche_bas.png";
                break;
            case Direction.LEFT:
                imagePath = "/image/fleche_gauche.png";
                break;
            case Direction.RIGHT:
                imagePath = "/image/fleche11x3.png";
                break;
            case Direction.UP_LEFT:
                imagePath = "/image/fleche_bas.png";
                break;
            case Direction.UP_RIGHT:
                imagePath = "/image/fleche_bas.png";
                break;
            case Direction.DOWN_LEFT:
                imagePath = "/image/fleche_bas.png";
                break;
            case Direction.DOWN_RIGHT:
                imagePath = "/image/fleche_bas.png";
                break;
        }
        return Main.class.getResource(imagePath).toExternalForm();
    }
}


