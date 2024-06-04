package sae.saezelda.modele;

public class Item {
    //TODO créer une sous classe equipement pour les armures que link pourra porter
    private String nom;
    public Item(String nom){
        this.nom=nom;
    }

    public String getNom(){
        return nom;
    }
}