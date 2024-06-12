package sae.saezelda.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import sae.saezelda.GameLoop;
import sae.saezelda.modele.*;
import sae.saezelda.vue.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private GameLoop gameLoop;

    @FXML
    private Pane paneJeu;

    @FXML
    private TilePane panneauDeJeu;
    private boolean hPresser, bPresser, gPresser, dPresser;

    private Coffre coffre1;
    private CoffreVue coffreVue;
    private Epee epee;
    private Arc arc;

    private Link link;
    private LinkVue linkVue;
    @FXML
    private Label pvLink;
    private MonObservableListeBombe observableListeBombe;


    private Terrain terrain;
    private TerrainVue terrainVue;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, panneauDeJeu);
        link = terrain.getLink();
        linkVue = new LinkVue(link, paneJeu, terrainVue);

        pvLink.textProperty().bind(link.getPvProperties().asString());

        MonObservableListeObstacle observableListeObstacle = new MonObservableListeObstacle(paneJeu);
        terrain.getObstacles().addListener(observableListeObstacle);

        observableListeBombe = new MonObservableListeBombe(paneJeu);
        link.getTerrain().getBombes().addListener(observableListeBombe);

        MonObservableListeFleche observableListeFleche = new MonObservableListeFleche(paneJeu);
        terrain.getFleches().addListener(observableListeFleche);



        Pierre pierre1 = new Pierre(80, 50);
//        ObstacleVue pierreVue = new ObstacleVue(paneJeu, pierre1);
        terrain.ajouterObstacle(pierre1);
        epee = new Epee();
        arc = new Arc("Arc de Link", 10, 2000);
//        link.equiperArc(arc);

        coffre1 = new Coffre(arc,2*32,0*32,terrain);
        coffreVue = new CoffreVue(coffre1,paneJeu , terrainVue);

        Zombie zombie = new Zombie(terrain);
        terrain.ajouterZombie(zombie);
        ZombieVue zombieVue = new ZombieVue(zombie, paneJeu, terrainVue);


//        Bombe bombe2 = new Bombe("Bombe", 50, 100, 100, terrain);
//        bombe2.bombeExplose();

//        Bombe bombe = new Bombe("bombe", 15, 100,100,terrain);
//        BombeVue bombeVue = new BombeVue(bombe, paneJeu);

        gameLoop = new GameLoop(link, linkVue, zombie, zombieVue);
        gameLoop.startGameLoop(terrain, paneJeu);
    }

    @FXML
    public void touchePresser(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.G) {
            link.tirerAvecArc();
        }
        if (code == KeyCode.Z) {
            hPresser = true;
        }
        else if (code == KeyCode.S) {
            bPresser = true;
        }
        else if (code == KeyCode.Q) {
            gPresser = true;
        }
        else if (code == KeyCode.D) {
            dPresser = true;
        }
        else if (code == KeyCode.E) {
            if (coffreDansZone() != null) {
                link.utiliser(coffreDansZone().ouvrir());
                link.equiperArc(arc);
            }
        }
        else if (code == KeyCode.B) {
            link.placerBombe();
        }
        else if(code == KeyCode.F) {
            link.attaquerCouteau();
        }
        changerDirectionLink();


    }


    @FXML
    public void toucheRelacher(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.Z) {
            hPresser = false;
        } else if (code == KeyCode.S) {
            bPresser = false;
        } else if (code == KeyCode.Q) {
            gPresser = false;
        } else if (code == KeyCode.D) {
            dPresser = false;
        }
        changerDirectionLink();
    }

    public Coffre coffreDansZone(){
        if (link.estDansZone(coffre1)){
            if (coffre1.estOuvert()){
                System.out.println("Le coffre a deja été ouvert");
                return null;
            }
            System.out.println("Coffre a poximité");
            return coffre1;
        }
        System.out.println("Pas de coffre a proximité");
        return null;
    }

    private void changerDirectionLink() {
        if (hPresser && gPresser) {
            link.setDirectionValue(Direction.UP_LEFT);
        } else if (hPresser && dPresser) {
            link.setDirectionValue(Direction.UP_RIGHT);
        } else if (bPresser && gPresser) {
            link.setDirectionValue(Direction.DOWN_LEFT);
        } else if (bPresser && dPresser) {
            link.setDirectionValue(Direction.DOWN_RIGHT);
        } else if (hPresser) {
            link.setDirectionValue(Direction.UP);
        } else if (bPresser) {
            link.setDirectionValue(Direction.DOWN);
        } else if (gPresser) {
            link.setDirectionValue(Direction.LEFT);
        } else if (dPresser) {
            link.setDirectionValue(Direction.RIGHT);
        } else {
            link.setDirectionValue(Direction.NEUTRE);
        }
    }

}