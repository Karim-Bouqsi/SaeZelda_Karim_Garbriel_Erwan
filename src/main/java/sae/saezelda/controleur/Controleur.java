package sae.saezelda.controleur;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import sae.saezelda.GameLoop;
import sae.saezelda.modele.*;
import sae.saezelda.vue.*;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private Label dialogueLabel;
    @FXML
    private Label gameOverLabel;

    private Arc arcJete;
    private MonObservableListeBombe observableListeBombe;
    private Environnement environnement;
    private Terrain terrain;
    private ImageView arcJeteVue;

    private TerrainVue terrainVue;
    private ArrayList<Terrain> terrains = new ArrayList<>();
    private Terrain terrainActif;
    private TerrainVue terrainVueActif;

    private boolean terrainRemplace = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrainActif = new Terrain();
        terrains.add(terrainActif);
        terrainVueActif = new TerrainVue(terrainActif, panneauDeJeu, false);
        environnement = new Environnement();
        link = new Link(environnement, terrainActif);
        environnement.setLink(link);
        linkVue = new LinkVue(link, paneJeu, terrainVueActif);
        link.getMortProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afficherGameOver();
            }
        });


        MonObservableListeObstacle observableListeObstacle = new MonObservableListeObstacle(paneJeu);
        environnement.getObstacles().addListener(observableListeObstacle);

        MonObservableListeCoffre observableListeCoffre = new MonObservableListeCoffre(paneJeu);
        environnement.getCoffres().addListener(observableListeCoffre);

        MonObservableListeZombie observableListeZombie = new MonObservableListeZombie(paneJeu);
        environnement.getZombies().addListener(observableListeZombie);

        observableListeBombe = new MonObservableListeBombe(paneJeu);
        environnement.getBombes().addListener(observableListeBombe);

        MonObservableListeFleche observableListeFleche = new MonObservableListeFleche(paneJeu);
        environnement.getFleches().addListener(observableListeFleche);

        MonObservableListePnj observableListePnj = new MonObservableListePnj(paneJeu);
        environnement.getPnjs().addListener(observableListePnj);

        MonObservableListeAquaman observableListeAquaman = new MonObservableListeAquaman(paneJeu);
        environnement.getAquamen().addListener(observableListeAquaman);

        MonObservableListeProjectile observableListeProjectile = new MonObservableListeProjectile(paneJeu);
        environnement.getProjectiles().addListener(observableListeProjectile);


        pvLink.textProperty().bind(link.getPvProperties().asString());


        Pierre pierre1 = new Pierre(80, 50);
        environnement.ajouterObstacle(pierre1);

        arc = new Arc("Arc", 10, 2000);
        coffre1 = new Coffre(arc, 2 * 32, 0 * 32);
        environnement.ajouterCoffre(coffre1);

//        coffreVue = new CoffreVue(coffre1, paneJeu);


        Pnj pnj = new Pnj("Sage", 620, 170, 10, 32, 19,2, environnement, 10000);
//        Pnj pnj = new Pnj("Sage", 70, 0, 10, 32, 19,2, terrainActif, environnement, 10000);
        environnement.ajouterPnj(pnj);


        Aquaman aquaman = new Aquaman(environnement, terrainActif);
        environnement.ajouterAquaman(aquaman);

//        Projectile projectile = new Projectile(environnement, terrainActif);
//        environnement.ajouterProjectile(projectile);

        Zombie zombie = new Zombie(environnement);
        environnement.ajouterZombie(zombie);



        gameLoop = new GameLoop(link, linkVue);
        gameLoop.startGameLoop(environnement, paneJeu);

        paneJeu.setFocusTraversable(true);
        paneJeu.requestFocus();
    }
    private void afficherGameOver() {
        gameOverLabel.setVisible(true);
    }

    private void remplacerTerrain() {
        if(!terrainRemplace) {
            panneauDeJeu.getChildren().clear();

            System.out.println("Ancien terrain supp");

            Terrain nouveauTerrain = new Terrain();
            nouveauTerrain.setTerrain(environnement.getTerrain().getTerrain2());
            terrainVue = new TerrainVue(nouveauTerrain, panneauDeJeu, true);

            environnement.changerTerrain(nouveauTerrain);
            terrainRemplace = true;
            ajouterElementsAuNouveauTerrain();
            terrainVue.afficherTerrain();

            System.out.println("Nouveau terrain mis");
        }

    }

    private void ajouterElementsAuNouveauTerrain() {
        // TODO Ajouter boss ici
        Coffre coffre;
        coffre1 = new Coffre(arc, 2 * 32, 0 * 32);
        environnement.ajouterCoffre(coffre1);

        Aquaman aquaman = new Aquaman(environnement, terrainActif);
        environnement.ajouterAquaman(aquaman);

        Projectile projectile = new Projectile(environnement, terrainActif);
        environnement.ajouterProjectile(projectile);
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
            else if(link.estDansArcZone()) {
                link.recupererArcJeter();
            }
        }
        else if (code == KeyCode.B) {
            link.placerBombe();
        }
        else if(code == KeyCode.F) {
            link.attaquerCouteau();
        }
        else if(code == KeyCode.P) {
            if (link.linkEstDansZoneTeleportation() && !terrainRemplace) {
                remplacerTerrain();
            }
        }
        else if (code == KeyCode.I) {
            dialogueLabel.setText(link.parlerPnjProche());
        }
        else if (code == KeyCode.T) {
            link.jeterArc();
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
        if(!environnement.getCoffres().isEmpty()) {
            if (link.estDansZoneCoffre(coffre1)) {
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