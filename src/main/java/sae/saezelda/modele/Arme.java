package sae.saezelda.modele;

public class Arme extends Item {
    private int ptAtt;
    public Arme(String nom,int ptAtt){
        super(nom);
        this.ptAtt=ptAtt;
    }
    public int getPtAtt(){
        return this.ptAtt;
    }
}