package sae.saezelda.vue;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sae.saezelda.Main;
import sae.saezelda.modele.*;


public class InventaireVue {
    GridPane gi;
    GridPane ge;
    VBox vbox;
    Link link;
    Image image;
    int ligne;
    int colonne;


    final int NB_MAX=8;


    public InventaireVue(VBox vbox,GridPane gi, GridPane ge,Link link){
        this.gi = gi;
        this.ge= ge;
        this.vbox=vbox;
        this.link=link;
        this.image = new Image(String.valueOf(Main.class.getResource("/image/orcish_dagger.png")));
        ligne = 0;
        colonne = 0;
        dessinePane();
    }
    public void dessinePane(){
        synchronized (this) {
            gi.getChildren().clear();
            ge.getChildren().clear();
            if (link.getArme() == null) {
                ImageView imageView = new ImageView(new Image(String.valueOf(Main.class.getResource("/image/case.png"))));
                ge.add(imageView, 0, 0);
            } else {
                ImageView imageView = new ImageView(choixImage(link.getArme()));
                imageView.setOnMouseClicked(event -> {
                    System.out.println("Arme cliquée");
                    link.desquiperArme();
                });
                ge.add(imageView, 0, 0);
            }
            if (link.getArmure() == null) {
                ImageView imageView = new ImageView(new Image(String.valueOf(Main.class.getResource("/image/case.png"))));
                ge.add(imageView, 0, 1);
            } else {
                ImageView imageView = new ImageView(new Image(String.valueOf(Main.class.getResource("/image/epee.png"))));
                imageView.setOnMouseClicked(event -> {
                    System.out.println("armure cliquée");
                    link.desequiperArmure();
                });
                ge.add(imageView, 0, 1);
            }

            for (int i = 0; i < NB_MAX; i++) {// l'inventaire et de 2 colonnes et 4 lignes
                ImageView imageView;
                if (i < link.getInventaire().size()) {
                    image = choixImage((Item) link.getInventaire().get(i));
                    imageView = new ImageView(image);
                    imageView.setFitWidth(32);
                    imageView.setFitHeight(32);
                    int finalI = i;
                    imageView.setOnMouseClicked(event -> {
                        System.out.println("Inventaire cliqué");
                        if (link.getInventaire().get(finalI) instanceof Arme) {
                            link.equiper((Item) link.getInventaire().get(finalI));

                        }
                        else if(link.getInventaire().get(finalI) instanceof PotionVie){
                            link.equiper((Item) link.getInventaire().get(finalI));
                        }
                        //TODO le faire pour l'armure
                    });
                } else {
                    imageView = new ImageView(new Image(String.valueOf(Main.class.getResource("/image/case.png"))));
                }
                gi.add(imageView, colonne, ligne);

                colonne++;
                if (colonne == 2) {
                    ligne++;
                    colonne = 0;
                }
            }
        }
    }
    public Image choixImage(Item item){
        Image image;
        if (item instanceof Couteau) {
            image = new Image(String.valueOf(Main.class.getResource("/image/epee.png")));
        } else if (item instanceof Potion) {
            image = new Image(String.valueOf(Main.class.getResource("/image/potion.png")));
        } else if (item instanceof Bombe) {
            image = new Image(String.valueOf(Main.class.getResource("/image/case_bombe.png")));
        } else if (item instanceof Arc) {
            image = new Image(String.valueOf(Main.class.getResource("/image/case_arc.png")));
        } else {
            image = new Image(String.valueOf(Main.class.getResource("/image/sol.png")));
        }

        return image;
    }
}
