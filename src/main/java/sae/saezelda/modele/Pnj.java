package sae.saezelda.modele;

public class Pnj extends Personnage {
    private String[] dialogues;
    private int dialogueIndice;
    public Pnj(String nom, int positionX, int positionY, int capaciteMax, int hauteur, int largeur, int vitesse, Terrain terrain, Environnement environnement, int pv) {
        super("Sage", positionX, positionY, capaciteMax, hauteur, largeur, vitesse, terrain, environnement, pv);
    }




}
