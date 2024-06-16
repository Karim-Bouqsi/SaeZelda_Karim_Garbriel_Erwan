package sae.saezelda.modele;

public class PotionVie extends Potion {
    int pv;
    public PotionVie(){
        super("Potion de vie");
        this.pv=20;
    }
    public int getPv(){
        return this.pv;
    }

}
