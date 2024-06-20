package main.test;

import org.junit.Before;
import org.junit.Test;
import sae.saezelda.modele.*;
import static org.junit.Assert.*;

public class LinkTest {
    private Environnement environnement;
    private Link link;
    private Terrain terrain;
    private Zombie zombie;
    private Coffre coffre;
    private Arc arc;


    @Before
    public void setUp() {
        environnement = new Environnement();
        terrain = new Terrain();
        link = new Link(environnement, terrain);
        zombie = new Zombie(environnement, 100, 100);
        environnement.ajouterZombie(zombie);
        Couteau couteau = new Couteau("couteau", 25, environnement);
        link.equiper(couteau);
        coffre = new Coffre(couteau, 100,100);
        arc = new Arc("Arc", 12, 12);
        link.utiliser(arc);
        coffre = new Coffre(arc, 100,100);

    }

    /* Test du coffre */

    @Test
    public void testEstDansZoneCoffreTrue() {
        link.setXValue(100);
        link.setYValue(100);

        coffre.setXValue(120);
        coffre.setYValue(120);

        assertTrue(link.estDansZoneCoffre(coffre));
    }
    @Test
    public void testEstDansZoneCoffreFalseCauseX() {
        link.setXValue(0);
        link.setYValue(100);

        coffre.setXValue(200);
        coffre.setYValue(100);

        assertFalse(link.estDansZoneCoffre(coffre));
    }

    @Test
    public void testEstDansZoneCoffreFalseCauseY() {
        link.setXValue(100);
        link.setYValue(0);

        coffre.setXValue(100);
        coffre.setYValue(200);

        assertFalse(link.estDansZoneCoffre(coffre));
    }

    @Test
    public void testEstDansZoneCoffreOver() {
        link.setXValue(150);
        link.setYValue(150);

        coffre.setXValue(100);
        coffre.setYValue(100);

        assertTrue(link.estDansZoneCoffre(coffre));
    }

    /* Test de l'équipement de l'arc */

    @Test
    public void testEquiperArc() {
        link.equiperArc(arc);

        assertTrue(link.getArcEquipeProperty().get());
        assertTrue(link.peutTirerFLeches.get());
        assertFalse(link.getInventaire().contains(arc));
    }

    /* Test des dégats */

    @Test
    public void testLinkSubitDegats() {
        int vieInitiale = link.getPvValue();
        int degats = 20;
        link.recevoirDegats(degats);
        assertEquals(vieInitiale - degats, link.getPvValue());
    }

    @Test
    public void testLinkInfligeDegatsZombie() {
        int vieInitialeZombie = zombie.getPvValue();
        int degats = 30;
        zombie.recevoirDegats(30);
        assertEquals(vieInitialeZombie - degats, zombie.getPvValue());
    }

    @Test
    public void testZombieInfligeDegatsLink() {
        int vieInitialeLink = link.getPvValue();
        int degats = 15;
        link.recevoirDegats(15);
        assertEquals(vieInitialeLink - degats, link.getPvValue());
    }

}
