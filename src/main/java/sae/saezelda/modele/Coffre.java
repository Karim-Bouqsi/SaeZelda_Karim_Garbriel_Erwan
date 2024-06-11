package sae.saezelda.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Coffre {
    private Item Contenu;
    private int x;
    private int y;
    private Terrain terrain;
    private BooleanProperty ouvert;
    private int width;
    private int height;



    // Constructeur pour le premier coffre
    public Coffre(Item Contenue,int x ,  int y ,Terrain terrain ){
        this.Contenu=Contenue;
        this.x=x;
        this.y=y;
        this.width=32;
        this.height=32;
        this.ouvert=new SimpleBooleanProperty(false);
        this.terrain=terrain;
    }



    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getLargeur(){
        return width;
    }
    public int getHauteur(){
        return height;
    }

    public boolean estOuvert(){
        return ouvert.getValue();
    }
    public BooleanProperty estOuvertProperty(){
        return ouvert;
    }
    public Item ouvrir(){
        ouvert.setValue(true);
        return Contenu;
    }




    // Constructeur pour avoir les items de maniere aleatoire
//    public Coffre(){
//        this.Contenu=
//    }
}

