package sae.saezelda;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import sae.saezelda.modele.*;
import sae.saezelda.vue.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameLoop {
    private Link link;
    private LinkVue linkVue;
    private final int FPS = 60;

    private Zombie zombie;
    private ZombieVue zombieVue;

    private Aquaman aquaman;
    private AquamanVue aquamanVue;

    private Projectile projectile;
    private ProjectileVue projectileVue;

    public GameLoop(Link link, LinkVue linkVue) {
        this.link = link;
        this.linkVue = linkVue;
        this.zombie = zombie;
//        this.zombieVue = zombieVue;
        this.aquaman = aquaman;
        this.projectile = projectile;
    }

    public void startGameLoop(Environnement environnement, Pane paneJeu) {
        Duration duration = Duration.millis(1000.0 / FPS);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            updateGame(environnement, paneJeu);
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateGame(Environnement environnement, Pane paneJeu) {
        link.linkMove();
        int linkX = link.getXValue();
        int linkY = link.getYValue();
        for (int i =0; i < environnement.getZombies().size(); i++) {
            environnement.getZombies().get(i).deplacerVersLink(linkX,linkY);
//            System.out.println(environnement.getZombies().get(i).getMortValue());
        }
        environnement.faireAvancerLesFleches();
        link.decrementCooldown();

        for (int i =0; i < environnement.getAquamen().size(); i++) {
            if(environnement.getAquamen().get(i).linkAPortee(link)){
                environnement.getProjectiles().get(i).tir(environnement.getAquamen().get(i).getXValue(), environnement.getAquamen().get(i).getYValue(), linkX, linkY);
            };
        }

        for (int i =0; i < environnement.getProjectiles().size(); i++){
            environnement.getProjectiles().get(i).bouge();
        }
    }
}