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
import javafx.scene.layout.VBox;
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
    @FXML
    private Label pvLink;
    @FXML
    private Label dialogueLabel;
    @FXML
    private Label gameOverLabel;
    @FXML
    private GridPane inventaireGrid;
    @FXML
    private GridPane porteGrid;
    @FXML
    private VBox sidePane;
    private boolean hPresser, bPresser, gPresser, dPresser;
    private Coffre coffre1;
    private Arc arc;
    private Link link;
    private LinkVue linkVue;
    private InventaireVue inventaireVue;
    private Potion potion;
    private Environnement environnement;
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


        MonObservableListeBombe observableListeBombe = new MonObservableListeBombe(paneJeu);
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

        potion = new PotionVie();
        arc = new Arc("Arc", 10, 2000);
        coffre1 = new Coffre(arc, 2 * 32, 0 * 32);
        environnement.ajouterCoffre(coffre1);

        Pnj pnj = new Pnj("Sage", 620, 170, 10, 32, 19,2, environnement, 10000);
        environnement.ajouterPnj(pnj);
        inventaireVue = new InventaireVue(sidePane,inventaireGrid,porteGrid,link);
        link.getInventaire().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                inventaireVue.dessinePane();
            }
        });

        Zombie zombie = new Zombie(environnement, 380, 50);
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
            Terrain nouveauTerrain = new Terrain();
            nouveauTerrain.setTerrain(environnement.getTerrain().getTerrain2());
            terrainVue = new TerrainVue(nouveauTerrain, panneauDeJeu, true);
            environnement.changerTerrain(nouveauTerrain);
            terrainRemplace = true;
            ajouterElementsAuNouveauTerrain();
            terrainVue.afficherTerrain();
            System.out.println("Bienvenue dans notre mini donjon !");
        }
    }

    private void ajouterElementsAuNouveauTerrain() {
        coffre1 = new Coffre(potion, 610, 0 * 32);
        environnement.ajouterCoffre(coffre1);

        Aquaman aquaman = new Aquaman(environnement, terrainActif);
        environnement.ajouterAquaman(aquaman);

        Pierre pierre1 = new Pierre(200, 150);
        environnement.ajouterObstacle(pierre1);

        Pierre pierre2 = new Pierre(300, 40);
        environnement.ajouterObstacle(pierre2);

        Zombie zombie = new Zombie(environnement, 250, 150);
        environnement.ajouterZombie(zombie);

        Zombie zombie2 = new Zombie(environnement, 15, 200);
        environnement.ajouterZombie(zombie2);
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
                    return null;
                }
                return coffre1;
            }
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



