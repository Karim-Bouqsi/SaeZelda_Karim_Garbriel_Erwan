package main.test;

import org.junit.Before;
import org.junit.Test;
import sae.saezelda.modele.*;

import static org.junit.Assert.*;

public class ZombieTest {

    private Environnement environnement;
    private Zombie zombie;
    private Link link;
    private Terrain terrain;

    @Before
    public void setUp() {
        environnement = new Environnement();
        terrain = new Terrain();
        zombie = new Zombie(environnement, 50, 50);
        environnement.ajouterZombie(zombie);
        link = new Link(environnement, terrain);
        environnement.setLink(link);
    }

    /* Test déplacement */

    @Test
    public void testDeplacer() {
        int initialX = zombie.getXValue();
        int initialY = zombie.getYValue();
        zombie.deplacer();

        if (zombie.getDirectionValue() == Direction.UP) {
            assertEquals(initialY - 1, zombie.getYValue());
        } else if (zombie.getDirectionValue() == Direction.DOWN) {
            assertEquals(initialY + 1, zombie.getYValue());
        }
        assertEquals(initialX, zombie.getXValue());
    }

    @Test
    public void testDeplacerVersLink() {
        link.setXValue(55);
        link.setYValue(50);

        zombie.deplacerVersLink(link.getXValue(), link.getYValue());

        assertEquals(52, zombie.getXValue());
        assertEquals(50, zombie.getYValue());
    }

    /* Test zombie dans la zone d'explosion de la bombe */

    @Test
    public void testEstDansZoneBombe() {
        assertTrue(zombie.estDansZoneBombe(50, 50));
        assertFalse(zombie.estDansZoneBombe(100, 100));
    }
}