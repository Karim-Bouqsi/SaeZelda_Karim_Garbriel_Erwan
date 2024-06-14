package sae.saezelda.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

public class Link extends Personnage {
    private Item item;
    private Arc arc;
    private int cooldown;
    private int cooldownCompteur;
    private BooleanProperty arcEquipe;
    private BooleanProperty peutPoserBombe;
    private BooleanProperty peutTirerFLeches;
    private BooleanProperty peutAttaquerCouteau;
    private Terrain terrain;

    public Link(Environnement environnement, Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, terrain, environnement, 100);
        this.item = null;
        this.peutPoserBombe = new SimpleBooleanProperty(true);
        this.peutTirerFLeches = new SimpleBooleanProperty(true);
        this.peutAttaquerCouteau = new SimpleBooleanProperty(true);
        this.terrain = terrain;
        this.arcEquipe = new SimpleBooleanProperty(false);
    }

    public void utiliser(Item item) {
        if (item != null) {
            System.out.println("Link a ramassé " + item.getNom());
            this.item = item;
            this.arc = null;
            this.cooldown = 60;
            this.cooldownCompteur = 0;
            this.arcEquipe.set(false);
        }
    }

    public void placerBombe() {
        if (peutPoserBombe.get()) {
            Bombe bombe = new Bombe("Bombe", 50, getXValue(), getYValue(), getEnvironnement());
            getEnvironnement().ajouterBombe(bombe);
            bombe.cooldownBombeEtExplose();
            peutPoserBombe.set(false);
            activerCooldownBombe();
        } else {
            System.out.println("Doucement les bombes");
        }
    }

    public void tirerAvecArc() {
        System.out.println("print en dehors du if tirerarc");
        if (arc != null && arc.getNombreDeFleches() > 0 && peutTirerFLeches.get() && !getMortValue()) {
            System.out.println("print du if tirerarc");
            Fleche fleche = new Fleche(getXValue() + getLargeur(), getYValue() + getHauteur() / 2, getDirectionValue(), 5, getEnvironnement());
            getEnvironnement().ajouterFleche(fleche);
            arc.setNombreDeFleches(arc.getNombreDeFleches() - 1);
            peutTirerFLeches.set(false);
            activerCooldownFleche();
        } else if (arc == null) {
            System.out.println("Tu n'as pas d'arc");
        } else {
            System.out.println("Tu n'as pas de flèche");
        }
    }

    public void attaquerCouteau() {
        Couteau couteau = new Couteau("couteau", 70, getEnvironnement());
        for (Zombie zombie : getEnvironnement().getZombies()) {
            if (getXValue() - 32 < zombie.getXValue() + zombie.getLargeur() && getXValue() + (32 * 2) > zombie.getXValue() &&
                    getYValue() - 32 < zombie.getYValue() + zombie.getHauteur() && getYValue() + (32 * 2) > zombie.getYValue()) {
                System.out.println("Zombie à proximité");
                if (peutAttaquerCouteau.get()) {
                    System.out.println("Le zombie a été attaqué au couteau");
                    zombie.recevoirDegats(couteau.getPtAtt());
                    peutAttaquerCouteau.set(false);
                    activerCooldownCouteau();
                }
            } else {
                System.out.println("Zombie non détecté à proximité");
            }
        }
    }

    public void activerCooldownBombe() {
        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> peutPoserBombe.set(true)));
        cooldownTimeline.play();
    }

    public void activerCooldownFleche() {
        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> peutTirerFLeches.set(true)));
        cooldownTimeline.play();
    }

    private void activerCooldownCouteau() {
        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> peutAttaquerCouteau.set(true)));
        cooldownTimeline.play();
    }

    public void recevoirDegats(int degats) {
        setPvValue(getPvValue() - degats);
    }

    public boolean estDansZoneBombe(int bombeX, int bombeY) {
        int linkX = getXValue();
        int linkY = getYValue();
        return linkX - 19 < bombeX + 32 && linkX + (19 * 2) > bombeX &&
                linkY - 32 < bombeY + 32 && linkY + (32 * 2) > bombeY;
    }

    public void decrementCooldown() {
        if (cooldownCompteur > 0) {
            cooldownCompteur--;
        }
    }


    public void linkMove() {
        int[] tabindice = super.move();
        linkVerification(tabindice[0], tabindice[1]);
    }

    public void pousserPierre(int direction, Obstacle pierre) {
        int newX = pierre.getXValue();
        int newY = pierre.getYValue();

        switch (direction) {
            case Direction.UP:
                newY -= 1;
                break;
            case Direction.DOWN:
                newY += 1;
                break;
            case Direction.LEFT:
                newX -= 1;
                break;
            case Direction.RIGHT:
                newX += 1;
                break;
            case Direction.UP_LEFT:
            case Direction.UP_RIGHT:
            case Direction.DOWN_LEFT:
            case Direction.DOWN_RIGHT:
                break;
        }

        if (getEnvironnement().nouvellePositionValide(newX, newY)) {
            switch (direction) {
                case Direction.UP:
                    pierre.move(0, -3);
                    break;
                case Direction.DOWN:
                    pierre.move(0, 3);
                    break;
                case Direction.LEFT:
                    pierre.move(-3, 0);
                    break;
                case Direction.RIGHT:
                    pierre.move(3, 0);
                    break;
                case Direction.UP_LEFT:
                case Direction.UP_RIGHT:
                case Direction.DOWN_LEFT:
                case Direction.DOWN_RIGHT:
                    break;
            }
        }
    }

    public void linkVerification(int x, int y) {
        Obstacle obstacle = recupererObstacle(x, y);
        if (obstacle != null) {
            pousserPierre(getDirectionValue(), obstacle);
        }

        if (detecterPierre(getDirectionValue(), x, y)) {
            System.out.println("Obstacle détecté " + getDirectionValue());
        }

        if (canMove(getDirectionValue(), x, y)) {
            setXValue(x);
            setYValue(y);
        }
    }

    public BooleanProperty getArcEquipeProperty() {
        return arcEquipe;
    }

    public boolean arcEquipeValue() {
        return arcEquipe.get();
    }

    public void setArcEquipe(boolean arcEquipe) {
        this.arcEquipe.set(arcEquipe);
    }

    public void desequiperArc() {
        this.arc = null;
        setArcEquipe(false);
    }

    public void equiperArc(Arc arc) {
        this.arc = arc;
    }

    public boolean estDansZone(Coffre coffre) {
        return getXValue() - getHauteur() < coffre.getXValue() + coffre.getLargeur() &&
                getXValue() + (getHauteur() * 2) > coffre.getXValue() &&
                getYValue() - getHauteur() < coffre.getYValue() + coffre.getHauteur() &&
                getYValue() + (getHauteur() * 2) > coffre.getYValue();
    }

}
