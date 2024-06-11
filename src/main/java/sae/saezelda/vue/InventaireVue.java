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
    GridPane gp;
    Link link;
    Image image;
    int ligne;
    int colonne;

    public InventaireVue(GridPane gp,Link link){
        this.gp = gp;
        this.link=link;
        this.image = new Image(String.valueOf(Main.class.getResource("/image/orcish_dagger.png")));
        ligne = 0;
        colonne = 0;
        dessinePane();
    }
    //TODO Faire en sorte que l'on voit la bonne image pour le bonne item

    public void dessinePane(){
        //DESSSINE LINVENTAIRE IMAGE PAR IMAGE SUR LE GRID PANE SANS PRENDRE EN COMPTE LITEM
        gp.getChildren().clear();
        for(int i =0; i<link.getInventaire().size();i++){// l'inventaire et de 2 colonnes et 4 lignes


            if (link.getInventaire().get(i) instanceof Epee) {
                image = new Image( String.valueOf(Main.class.getResource("/image/orcish_dagger.png")));
            } else if (link.getInventaire().get(i) instanceof Potion) {
                image = new Image( String.valueOf(Main.class.getResource("/image/ruby.png")));
            } else {
                image = new Image( String.valueOf(Main.class.getResource("/image/sol.png")));
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(32);
            imageView.setFitHeight(32);
            int finalI = i;
            imageView.setOnMouseClicked(event -> {
                System.out.println("Main Gauche cliqué");
                if (link.getInventaire().get(finalI) instanceof Epee) {
                    link.equiper((Item) link.getInventaire().get(finalI));
                }
            });
            gp.add(imageView,colonne,ligne);

            colonne++;
            if(i%2==0){
                ligne++;
                colonne=0;
            }
        }

    }
}
