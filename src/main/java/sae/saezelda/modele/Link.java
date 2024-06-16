package sae.saezelda.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Link extends Personnage {
    private Terrain terrain;
    private Item arme;
    private Item armure;
    public static final int PV_MAX = 80;

    private ObservableList<Item> invetaire;

    public Link(Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, terrain, 87);
        this.terrain = terrain;
        this.arme = null;
        this.armure=null;
        this.invetaire = FXCollections.observableArrayList();
    }


    public ObservableList getInventaire(){
        return invetaire;
    }

    public void equiper(Item item){
        if(item instanceof Arme){
            if(this.arme==null) {
                this.arme=item;
                getInventaire().remove(item);
            }
            else {
                getInventaire().add(this.arme);
                this.arme=item;
            }
        }
        else if (item instanceof PotionVie){
            boire((PotionVie) item);
        }
    }
    public void desquiperArme(){
        Item pivot = this.arme;
        this.arme=null;
        this.invetaire.add(pivot);

    }
    public void desequiperArmure(){
        Item pivot = this.armure;
        this.armure=null;
        this.invetaire.add(pivot);
    }
    public Item getArme(){
        return this.arme;
    }
    public Item getArmure(){
        return this.armure;
    }
    public void boire(PotionVie potion){
        if (getPvValue()==PV_MAX) System.out.println("Tu ne peux pas boire pv max");
        else if (getPvValue()>PV_MAX-20){
            System.out.println("Pv regenerer 100pv");
            this.setPvValue(100);

        }
        else{
            System.out.println("regene pv");
            this.setPvValue(getPvValue()+ potion.getPv());
        }
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