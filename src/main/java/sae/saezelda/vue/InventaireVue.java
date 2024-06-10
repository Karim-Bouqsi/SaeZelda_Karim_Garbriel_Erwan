package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sae.saezelda.Main;
import sae.saezelda.modele.Link;


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

        for(int i =0; i<link.getInventaire().size();i++){// l'inventaire et de 2 colonnes et 4 lignes

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(32);
            imageView.setFitHeight(32);
            gp.add(imageView,colonne,ligne);

            colonne++;
            if(i%2==0){
                ligne++;
                colonne=0;
            }
        }

    }
}
