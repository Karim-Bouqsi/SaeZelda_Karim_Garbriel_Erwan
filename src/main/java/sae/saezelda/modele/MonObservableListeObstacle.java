package sae.saezelda.modele;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class MonObservableListeObstacle implements ListChangeListener<Obstacle> {
    private Pane panneauJeu;

    public MonObservableListeObstacle(Pane panneauJeu) {
        this.panneauJeu = panneauJeu;
    }

    @Override
    public void onChanged(Change<? extends Obstacle> change) {
        while (change.next()) {
            if (change.wasAdded()) {
                for(int i = 0; i < change.getAddedSubList().size(); i++) {
                    creerSprite(change.getAddedSubList().get(i));
                }
            }
            if (change.wasRemoved()) {
                    for(int i = 0; i < change.getRemoved().size(); i++) {
                    panneauJeu.getChildren().remove(this.panneauJeu.lookup("#obstacle" + change.getRemoved().get(i).getId()));
                }
            }
        }
    }

    private void creerSprite(Obstacle obstacle) {
        ImageView imageView = (ImageView) panneauJeu.lookup("#obstacle" + obstacle.getId());
        if (imageView == null) {
            imageView = new ImageView(obstacle.getImage());
            imageView.setId("obstacle" + obstacle.getId());

            imageView.translateXProperty().bind(obstacle.getXProperties());
            imageView.translateYProperty().bind(obstacle.getYProperties());

            panneauJeu.getChildren().add(imageView);
        }
    }




}
