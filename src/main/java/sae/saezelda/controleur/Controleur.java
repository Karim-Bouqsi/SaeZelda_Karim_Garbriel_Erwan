package sae.saezelda.controleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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
    private Environnement environnement;
    private Terrain terrain;
    private TerrainVue terrainVue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        environnement = new Environnement();
        link = new Link(environnement, terrain);
        environnement.setLink(link);

        terrainVue = new TerrainVue(terrain, panneauDeJeu);
        linkVue = new LinkVue(link, paneJeu, terrainVue);

        pvLink.textProperty().bind(link.getPvProperties().asString());

        MonObservableListeObstacle observableListeObstacle = new MonObservableListeObstacle(paneJeu);
        environnement.getObstacles().addListener(observableListeObstacle);

        observableListeBombe = new MonObservableListeBombe(paneJeu);
        environnement.getBombes().addListener(observableListeBombe);

        MonObservableListeFleche observableListeFleche = new MonObservableListeFleche(paneJeu);
        environnement.getFleches().addListener(observableListeFleche);

        Pierre pierre1 = new Pierre(80, 50);
        environnement.ajouterObstacle(pierre1);

        arc = new Arc("Arc", 10, 2000);
        coffre1 = new Coffre(arc, 2 * 32, 0 * 32, terrain);
        coffreVue = new CoffreVue(coffre1, paneJeu, terrainVue);

        Zombie zombie = new Zombie(environnement, terrain);
        environnement.ajouterZombie(zombie);
        ZombieVue zombieVue = new ZombieVue(zombie, paneJeu, terrainVue);

        gameLoop = new GameLoop(link, linkVue, zombie, zombieVue);
        gameLoop.startGameLoop(environnement, paneJeu);
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

    private Coffre coffreDansZone() {
        if (link.estDansZone(coffre1)) {
            if (coffre1.estOuvert()) {
                System.out.println("Le coffre a déjà été ouvert");
                return null;
            }
            System.out.println("Coffre à proximité");
            return coffre1;
        }
        System.out.println("Pas de coffre à proximité");
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
