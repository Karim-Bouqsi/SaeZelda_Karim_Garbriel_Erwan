package sae.saezelda.modele;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;


import java.util.ArrayList;

public class Terrain {
    String nom;
    private int tailleTuile = 32;
    private int largeur = 640;
    private int hauteur = 320;
    private ArrayList<Obstacle> obstacles;
    private Link link;
    private ArrayList<Bombe> bombes;



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

    public int getWidth() {
        return largeur;
    }

    public int getHeight() {
        return hauteur;
    }

    public Link getLink() {
        return link;
    }

    public Terrain() {
        obstacles = new ArrayList<>();
        bombes = new ArrayList<>();
        link = new Link(this);
        this.nom = "Demo";
    }



    public ArrayList<Bombe> getBombes() {
        return bombes;
    }
    public void retirerObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }

    public int getIndiceTuile(int x, int y) {
        int colonne = x / tailleTuile;
        int ligne = y / tailleTuile;

        if (colonne < 0 || colonne >= largeur / tailleTuile || ligne < 0 || ligne >= hauteur / tailleTuile) {
            return -1;
        }
        return ligne * (largeur / tailleTuile) + colonne;
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

    public void ajouterBombe(Bombe bombe) {
        bombes.add(bombe);
    }

    public void retirerBombe(Bombe bombe) {
        bombes.remove(bombe);
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void ajouterObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
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