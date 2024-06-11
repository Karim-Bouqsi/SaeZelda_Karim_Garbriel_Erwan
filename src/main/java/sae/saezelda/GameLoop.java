package sae.saezelda;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import sae.saezelda.modele.Bombe;
import sae.saezelda.modele.Link;
import sae.saezelda.modele.Zombie;
import sae.saezelda.vue.LinkVue;
import sae.saezelda.vue.TerrainVue;
import sae.saezelda.vue.ZombieVue;
import sae.saezelda.modele.Terrain;
import sae.saezelda.modele.Fleche;
import sae.saezelda.vue.FlecheVue;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameLoop {
    private Link link;
    private LinkVue linkVue;
    private final int FPS = 60;

    private Zombie zombie;
    private ZombieVue zombieVue;

    public GameLoop(Link link, LinkVue linkVue, Zombie zombie, ZombieVue zombieVue) {
        this.link = link;
        this.linkVue = linkVue;
        this.zombie = zombie;
        this.zombieVue = zombieVue;
    }

    public void startGameLoop(Terrain terrain, Pane paneJeu) {
        Duration duration = Duration.millis(1000.0 / FPS);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            updateGame(terrain, paneJeu);
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateGame(Terrain terrain, Pane paneJeu) {
        link.linkMove();
        int linkX = link.getXValue();
        int linkY = link.getYValue();
        zombie.deplacerVersLink(linkX, linkY);

//        updateFleches(terrain, paneJeu);
        terrain.faireAvancerLesFleches(paneJeu);
        link.decrementCooldown();
//        System.out.println(terrain.getFleches().size());
    }
}
