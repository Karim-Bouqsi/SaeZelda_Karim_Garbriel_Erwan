package sae.saezelda.modele;

public class Arc extends Arme {
    private int nbFleche;
    private int portee = 200; // en px TODO Ajouter une portée à l'arc si le temps est favorable

    public Arc(String nom, int ptAtt, int nbFleche) {
        super(nom, ptAtt);
        this.nbFleche = nbFleche;
    }

    public int getNombreDeFleches() {
        return nbFleche;
    }

    public int getPortee() {
        return portee;
    }

    public void setNombreDeFleches(int nombreDeFleches) {
        this.nbFleche = nombreDeFleches;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "nbFleche=" + nbFleche +
                '}';
    }
}
