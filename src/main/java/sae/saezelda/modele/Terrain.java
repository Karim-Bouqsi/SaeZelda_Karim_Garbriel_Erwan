package sae.saezelda.modele;

import javafx.scene.layout.Pane;
import sae.saezelda.vue.FlecheVue;

import java.util.ArrayList;

public class Terrain {
    String nom;
    private int tailleTuile = 32;
    private int largeur = 640;
    private int hauteur = 320;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Fleche> fleches;
    private ArrayList<Zombie> zombies;
    private Link link;



    private int[] terrain = {
            0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,3,3,3,3,3,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,2,2,0,0,0,
            3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,3,3,3,3,3,
            0,0,0,0,0,0,3,3,3,3,3,3,3,0,0,3,0,0,0,0,
            1,1,1,1,1,0,0,0,0,0,0,0,3,3,3,3,0,2,0,0,
            0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,2,0,0,0,
            0,0,2,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,2,
            0,0,0,2,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,
            0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,2,0,1,1,
    };

    public Terrain() {
        obstacles = new ArrayList<>();
        link = new Link(this);
        this.nom = "Demo";
        this.fleches = new ArrayList<>();
        this.zombies = new ArrayList<>();
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public int getWidth() {
        return largeur;
    }

    public int getHeight() {
        return hauteur;
    }

    public Link getLink() {
        return link;
    }


    public void ajouterFleche(Fleche fleche) {
        fleches.add(fleche);
    }
    public ArrayList<Fleche> getFleches() {
        return fleches;
    }

    public int getIndiceTuile(int x, int y) {
        int colonne = x / tailleTuile;
        int ligne = y / tailleTuile;

        if (colonne < 0 || colonne >= largeur / tailleTuile || ligne < 0 || ligne >= hauteur / tailleTuile) {
            return -1;
        }
        return ligne * (largeur / tailleTuile) + colonne;
    }


    public void faireAvancerLesFleches(Pane panneauJeu) {
        ArrayList<Fleche> flechesASupprimer = new ArrayList<>();
        for (int i = 0; i < fleches.size(); i++) {
            Fleche fleche = fleches.get(i);
            fleche.deplacer();
            if (fleche.aDepasseLimites()) {
                flechesASupprimer.add(fleche);
            }
            else {
                FlecheVue flecheVue = new FlecheVue(fleche, panneauJeu, link);
            }
        }
        fleches.removeAll(flechesASupprimer);
    }


    public boolean estDansLesLimites(int x, int y) {
        if(x >= 0 && x <= largeur - link.getLargeur() && y >= -link.getHauteur() && y <= hauteur - link.getHauteur()) {
            return true;
        }
        return false;
    }

    public boolean estObstacle(int x, int y) {
        int indice = getIndiceTuile(x, y);
        if (indice < 0) {
            return false;
        }
        return terrain[indice] == 1 || terrain[indice] == 4;
    }

    public void supprimerFleches(ArrayList<Fleche> fleches) {
        for(int i = 0; i < fleches.size(); i++) {
            if(estDansLesLimites(fleches.get(i).getX(), fleches.get(i).getX())) {
                fleches.remove(i);
            }
        }
    }



    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void ajouterObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }



    public void ajouterZombie(Zombie zombie) {
        this.zombies.add(zombie);
    }



    public boolean nouvellePositionValide(int x, int y) {
        if (!estDansLesLimites(x, y)) {
            System.out.println("Nouvelle position invalide");
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getXValue() == x && obstacle.getYValue() == y) {
                System.out.println("Nouvelle position invalide");
                return false;
            }
        }
        System.out.println("Nouvelle position valide");
        return true;
    }






    public int[] getTerrain() {
        return this.terrain;
    }
    public String getNom(){
        return nom;
    }
}