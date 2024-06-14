package sae.saezelda.modele;

public class Pnj extends Personnage {
    private String[] dialogues;
    private int dialogueIndice;
    public Pnj(String nom, int positionX, int positionY, int capaciteMax, int hauteur, int largeur, int vitesse, Terrain terrain, Environnement environnement, int pv) {
        super("Sage", positionX, positionY, capaciteMax, hauteur, largeur, vitesse, terrain, environnement, pv);
        dialogues = new String[4];
        dialogues[0] = "Tu ne m'écoute pas ?";
        dialogues[1] = "Salut jeune aventurier, ne rentre surtout pas dans le portail situé en bas.";
        dialogues[2] = "Appui sur la touche 'P' pour entrer dans le portail";
        dialogues[3] = "Bon courage !";
    }


    public String parler() {
        dialogueIndice+=1;
        if (dialogueIndice < dialogues.length) {
            return dialogues[dialogueIndice];
        } else {
            dialogueIndice = 0;
            return dialogues[dialogueIndice];
        }
    }



}
