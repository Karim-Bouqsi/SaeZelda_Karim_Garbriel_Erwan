package sae.saezelda.modele;

public class Arme extends Item {
    private static int nextId = 1;
    private final int id;
    private int ptAtt;
    public Arme(String nom,int ptAtt){
        super(nom);
        this.ptAtt=ptAtt;
        this.id = nextId++;
    }
    public int getPtAtt(){
        return this.ptAtt;
    }
    public int getId() {
        return id;
    }
}