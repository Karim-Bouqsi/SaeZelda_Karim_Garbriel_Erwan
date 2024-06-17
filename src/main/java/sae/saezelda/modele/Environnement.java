package sae.saezelda.modele;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Environnement {
    private int tailleTuile = 32;
    private int largeur = 640;
    private int hauteur = 320;
    private ObservableList<Obstacle> obstacles;
    private ObservableList<Fleche> fleches;
    private ObservableList<Zombie> zombies;
    private ObservableList<Bombe> bombes;
    private ObservableList<Coffre> coffres;
    private ObservableList<Pnj> pnjs;
    private Terrain terrain;
    private ObservableList<Aquaman> aquamen;
    private ObservableList<Projectile> projectiles;
    private Link link;

    public Environnement() {
        obstacles = FXCollections.observableArrayList();
        bombes = FXCollections.observableArrayList();
        fleches = FXCollections.observableArrayList();
        zombies = FXCollections.observableArrayList();
        coffres = FXCollections.observableArrayList();
        pnjs = FXCollections.observableArrayList();
        aquamen = FXCollections.observableArrayList();
        projectiles = FXCollections.observableArrayList();
        this.terrain = new Terrain();
        this.link = new Link(this, terrain);
    }
    public int getIndiceTuile(int x, int y) {
        int colonne = x / tailleTuile;
        int ligne = y / tailleTuile;

        if (colonne < 0 || colonne >= largeur / tailleTuile || ligne < 0 || ligne >= hauteur / tailleTuile) {
            return -1;
        }
        return ligne * (largeur / tailleTuile) + colonne;
    }
    public void faireAvancerLesFleches() {
        ObservableList<Fleche> flechesASupprimer = FXCollections.observableArrayList();
        for (int i = 0; i < fleches.size(); i++) {
            Fleche fleche = fleches.get(i);
            fleche.deplacer();
        }
        verifierEtSupprimerFleches();
    }
    public void verifierEtSupprimerFleches() {
        ObservableList<Fleche> flechesASupprimer = FXCollections.observableArrayList();
        for (Fleche fleche : fleches) {
            if (fleche.aDepasseLimites() || fleche.toucheCible()) {
                flechesASupprimer.add(fleche);
            }
        }
        fleches.removeAll(flechesASupprimer);
    }
    public boolean estDansLesLimites(int x, int y) {
        if (link != null && x >= 0 && x <= largeur - link.getLargeur() && y >= -link.getHauteur() && y <= hauteur - link.getHauteur()) {
            return true;
        }
        return false;
    }
    public boolean estObstacle(int x, int y) {
        int indice = getIndiceTuile(x, y);
        if (indice < 0) {
            return false;
        }
        return terrain.getTerrain()[indice] == 1 || terrain.getTerrain()[indice] == 4;
    }
    public boolean nouvellePositionValide(int x, int y) {

        if (!estDansLesLimites(x, y)) {
            return false;
        }

        for (Obstacle obstacle : obstacles) {
            if (obstacle.getXValue() == x && obstacle.getYValue() == y) {
                return false;
            }
        }
        return true;
    }
    public void reinitialiserElementsTerrain() {
        obstacles.clear();
        bombes.clear();
        fleches.clear();
        zombies.clear();
        coffres.clear();
        pnjs.clear();
        link.setArcJeterValue(false);
    }

    public void changerTerrain(Terrain terrain) {
        this.terrain = terrain;
        reinitialiserElementsTerrain();
    }
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
    public Terrain getTerrainActuel() {
        return terrain;
    }
    public Terrain getTerrain() {
        return terrain;
    }
    public void setLink(Link link) {
        this.link = link;
    }
    public void ajouterAquaman(Aquaman aquaman) { this.aquamen.add(aquaman); }
    public void retirerAquaman(Aquaman aquaman) {aquamen.remove(aquaman);}
    public ObservableList<Aquaman> getAquamen() { return aquamen; }
    public void ajouterProjectile(Projectile projectile) {this.projectiles.add(projectile);}
    public void retirerProjectile(Projectile projectile){projectiles.remove(projectile); }
    public ObservableList<Projectile> getProjectiles() { return projectiles; }
    public void ajouterPnj(Pnj pnj) {
        pnjs.add(pnj);
    }
    public ObservableList<Pnj> getPnjs() {
        return pnjs;
    }
    public int getLargeur() {
        return largeur;
    }
    public Link getLink() {
        return link;
    }
    public void retirerZombie(Zombie zombie) {
        zombies.remove(zombie);
    }
    public int getHauteur() {
        return hauteur;
    }
    public void ajouterFleche(Fleche fleche) {
        fleches.add(fleche);
    }
    public ObservableList<Fleche> getFleches() {
        return fleches;
    }
    public ObservableList<Bombe> getBombes() {
        return bombes;
    }
    public void retirerObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }
    public void ajouterBombe(Bombe bombe) {
        bombes.add(bombe);
    }
    public void ajouterCoffre(Coffre coffre) {
        coffres.add(coffre);
    }
    public void retirerBombe(Bombe bombe) {
        bombes.remove(bombe);
    }
    public void ajouterObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
    public ObservableList<Obstacle> getObstacles() {
        return obstacles;
    }
    public void ajouterZombie(Zombie zombie) {
        this.zombies.add(zombie);
    }
    public ObservableList<Zombie> getZombies() {
        return zombies;
    }
    public ObservableList<Coffre> getCoffres() {
        return coffres;
    }

}