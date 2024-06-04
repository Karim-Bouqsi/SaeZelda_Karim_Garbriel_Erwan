package sae.saezelda.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sae.saezelda.Main;
import sae.saezelda.modele.Coffre;

public class CoffreVue {
    Coffre coffre;
    private Pane panneauJeu;
    private TerrainVue terrainVue;

    private ImageView[] imageTab;
    public CoffreVue(Coffre coffre, Pane panneauJeu , TerrainVue terrainVue){
        this.coffre=coffre;
        this.panneauJeu=panneauJeu;
        this.terrainVue=terrainVue;
        Image[] images = new Image[2];
        images[0] =  new Image(String.valueOf(Main.class.getResource("/image/coffre_ferme.png")));
        images[1] = new Image(String.valueOf(Main.class.getResource("/image/coffre_ouvert.png")));
        imageTab = new ImageView[2];
        for (int i  =0 ; i<2;i++) {
            imageTab[i] = new ImageView(images[i]);
            imageTab[i].setFitWidth(32);
            imageTab[i].setFitHeight(32);
            imageTab[i].setTranslateX((coffre.getX()));
            imageTab[i].setTranslateY(coffre.getY());
        }
        panneauJeu.getChildren().add(imageTab[0]);
        coffre.estOuvertProperty().addListener((obj,old,nouv)->{
            if(nouv){
                System.out.println("listener");
                panneauJeu.getChildren().set(2,imageTab[1]);
            }
        });
    }


}
