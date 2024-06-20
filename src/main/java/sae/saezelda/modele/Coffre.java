package sae.saezelda.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Coffre {
    private static int nextId = 1;
    private final int id;
    private Item Contenu;
    private int x;
    private int y;
    private BooleanProperty ouvert;
    private int width;
    private int height;

    public Coffre(Item Contenue,int x ,  int y){
        this.id = nextId++;
        this.Contenu=Contenue;
        this.x=x;
        this.y=y;
        this.width=32;
        this.height=32;
        this.ouvert=new SimpleBooleanProperty(false);
    }
    public int getId() {
        return id;
    }
    public int getXValue() {
        return x;
    }
    public int getYValue() {
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

    public void setXValue(int x) {
        this.x = x;
    }
    public void setYValue(int y) {
        this.y = y;
    }
}

