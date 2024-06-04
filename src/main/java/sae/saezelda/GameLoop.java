package sae.saezelda;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import sae.saezelda.modele.Link;
import sae.saezelda.modele.Zombie;
import sae.saezelda.vue.LinkVue;
import sae.saezelda.vue.ZombieVue;

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

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        this.startGameLoop();
//    }

    public void startGameLoop() {
        Duration duration = Duration.millis(1000.0 / FPS);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            updateGame();
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateGame() {
        link.move();
        int linkX = link.getXValue();
        int linkY = link.getYValue();

        zombie.deplacerVersLink(linkX, linkY);
    }
}
