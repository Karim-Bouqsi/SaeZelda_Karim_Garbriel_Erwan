package sae.saezelda.modele;

public class Item {
    private static int nextId = 1;
    private final int id;
    private String nom;
    public Item(String nom){
        this.nom=nom;
        this.id = nextId++;
    }

    public String getNom(){
        return nom;
    }
    public int getId() {
        return id;
    }

}