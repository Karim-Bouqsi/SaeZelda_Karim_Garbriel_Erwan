package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sae.saezelda.Main;
import sae.saezelda.modele.Epee;
import sae.saezelda.modele.Item;
import sae.saezelda.modele.Link;
import sae.saezelda.modele.Potion;


public class InventaireVue {
    GridPane gi;
    GridPane ge;
    Link link;
    Image image;
    int ligne;
    int colonne;

    final int NB_MAX=8;


    public InventaireVue(GridPane gi, GridPane ge,Link link){
        this.gi = gi;
        this.ge= ge;
        this.link=link;
        this.image = new Image(String.valueOf(Main.class.getResource("/image/orcish_dagger.png")));
        ligne = 0;
        colonne = 0;
        dessinePane();
    }
    //TODO Faire en sorte que l'on voit la bonne image pour le bonne item

    public void dessinePane(){
        //DESSSINE LINVENTAIRE IMAGE PAR IMAGE SUR LE GRID PANE SANS PRENDRE EN COMPTE LITEM
        gi.getChildren().clear();
        ge.getChildren().clear();

        for(int i =0; i<NB_MAX;i++){// l'inventaire et de 2 colonnes et 4 lignes
            ImageView imageView;
            if (i<link.getInventaire().size()) {
                if (link.getInventaire().get(i) instanceof Epee) {
                    image = new Image(String.valueOf(Main.class.getResource("/image/epee.png")));
                } else if (link.getInventaire().get(i) instanceof Potion) {
                    image = new Image(String.valueOf(Main.class.getResource("/image/potion.png")));
                } else {
                    image = new Image(String.valueOf(Main.class.getResource("/image/sol.png")));
                }
                imageView = new ImageView(image);
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
                int finalI = i;
                imageView.setOnMouseClicked(event -> {
                    System.out.println("Inventaire cliqué");
                    if (link.getInventaire().get(finalI) instanceof Epee) {
                        link.equiper((Item) link.getInventaire().get(finalI));
                    }
                    //TODO le faire pour l'armure
                });
            }
            else {
                imageView = new ImageView(new Image(String.valueOf(Main.class.getResource("/image/case.png"))));
            }
            gi.add(imageView,colonne,ligne);

            colonne++;
            if(i%2==0){
                ligne++;
                colonne=0;
            }
        }
    }
}
