package sae.saezelda.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

public class Link extends Personnage {
    private Terrain terrain;
    private Item arme;

    private ObservableList<Item> invetaire;

    public Link(Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, terrain, 100);
        this.terrain = terrain;
        this.arme = null;
        this.invetaire = FXCollections.observableArrayList();
    }


    public ObservableList getInventaire(){
        return invetaire;
    }
    public void utiliser(Item item){

            System.out.println("Link a ramassé "+item.getNom());
            invetaire.add(item);

    }



    public boolean estDansZone(Coffre coffre){
        return getXValue()-getHauteur() < coffre.getX()+coffre.getLargeur() && getXValue()+(getHauteur()*2)>coffre.getX() && getYValue()-getHauteur()<coffre.getY()+coffre.getHauteur() && getYValue()+(getHauteur()*2)>coffre.getY();
    }
    public Terrain getTerrain() {
        return terrain;
    }





}