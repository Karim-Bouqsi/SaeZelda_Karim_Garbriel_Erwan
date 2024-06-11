package sae.saezelda.controleur;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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
    private Link link;
    private LinkVue linkVue;
    @FXML
    private Label pvLink;
    @FXML
    private GridPane inventaireGrid;

    private InventaireVue inventaireVue;
    private Potion potion;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Terrain terrain = new Terrain();
        TerrainVue terrainVue = new TerrainVue(terrain, panneauDeJeu);
        link = new Link(terrain);
        linkVue = new LinkVue(link, paneJeu, terrainVue);
        pvLink.textProperty().bind(link.getPvProperties().asString());

        Pierre pierre1 = new Pierre(80, 50);
        ObstacleVue pierreVue = new ObstacleVue(paneJeu, pierre1);
        terrain.ajouterObstacle(pierre1);

        // feature inventaire
        inventaireVue = new InventaireVue(inventaireGrid,link);
        link.getInventaire().addListener( new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                inventaireVue.dessinePane();
            }
        });

        // feature zombie :

        epee = new Epee();
        potion = new Potion("Vrai Potion");



        coffre1 = new Coffre(epee,12*32,2*32,terrain);
        coffreVue = new CoffreVue(coffre1,paneJeu , terrainVue);


        Zombie zombie = new Zombie(terrain);
        ZombieVue zombieVue = new ZombieVue(zombie, paneJeu, terrainVue);
        gameLoop = new GameLoop(link, linkVue, zombie, zombieVue);
        gameLoop.startGameLoop();
    }

    @FXML
    public void touchePresser(KeyEvent event) {

        KeyCode code = event.getCode();
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
        else if (code == KeyCode.E) { // pour le coffre
            if (coffreDansZone() != null) link.utiliser(coffreDansZone().ouvrir());
        }
        changerDirectionLink();
//        else if(code == KeyCode.E) {
//            link.pousser();
//        }

    } // TODO ici analyse quelle touche, et dire à link sa direction

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
        // faire une boucle des qu'il y aura plus de coffre
//        int tuileCoffre = terrain.getIndiceTuile(coffre1.getX(),coffre1.getY());

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

        //TODO ajouter les if else pour gérer les diagonales et l'arrêt

        if (hPresser) {
            link.setDirectionValue(Direction.UP);
        }
        if (bPresser) {
            link.setDirectionValue(Direction.DOWN);
        }
        if (gPresser) {
            link.setDirectionValue(Direction.LEFT);
        }
        if (dPresser) {
            link.setDirectionValue(Direction.RIGHT);
        }
    }




}
