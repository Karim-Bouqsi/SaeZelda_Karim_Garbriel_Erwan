package sae.saezelda.modele;

public class Couteau extends Arme {
    private Environnement environnement;

    public Couteau(String nom, int ptAtt, Environnement environnement) {
        super(nom, ptAtt);
        this.environnement = environnement;
    }
}
