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
    private BooleanProperty attaqueCouteau;
    private BooleanProperty arcJeter;
    private int arcPositionX = 0;
    private int arcPositionY = 0;

    private Terrain terrain;

    public Link(Environnement environnement, Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, terrain, environnement, 100);
        this.item = null;
        this.peutPoserBombe = new SimpleBooleanProperty(true);
        this.peutTirerFLeches = new SimpleBooleanProperty(true);
        this.peutAttaquerCouteau = new SimpleBooleanProperty(true);
        this.terrain = terrain;
        this.arcEquipe = new SimpleBooleanProperty(false);
        this.attaqueCouteau = new SimpleBooleanProperty(false);
        this.arcJeter = new SimpleBooleanProperty(false);
    }

    public void utiliser(Item item) {
        if (item != null) {
            System.out.println("Link a ramassé " + item.getNom());
            this.item = item;
            if(item instanceof Arc) {
                this.arcEquipe.set(true);
            }
        }
    }


    public void placerBombe() {
        if (peutPoserBombe.get() && !getMortValue()) {
            Bombe bombe = new Bombe("Bombe", 50, getXValue(), getYValue(), getEnvironnement());
            getEnvironnement().ajouterBombe(bombe);
            bombe.cooldownBombeEtExplose();
            peutPoserBombe.set(false);
            activerCooldownBombe();
        } else {
            System.out.println("Doucement les bombes");
        }
    }
    public BooleanProperty arcJeterProperty() {
        return arcJeter;
    }


//    public void tirerAvecArc() {
//        System.out.println("print en dehors du if tirerarc");
//        if (arc != null && arc.getNombreDeFleches() > 0 && peutTirerFLeches.get() && !getMortValue()) {
//            System.out.println("print du if tirerarc");
//            Fleche fleche = new Fleche(getXValue() + getLargeur(), getYValue() + getHauteur() / 2, getDirectionValue(), 5, getEnvironnement());
//            getEnvironnement().ajouterFleche(fleche);
//            arc.setNombreDeFleches(arc.getNombreDeFleches() - 1);
//            peutTirerFLeches.set(false);
//            activerCooldownFleche();
//        } else if (arc == null) {
//            System.out.println("Tu n'as pas d'arc");
//        } else {
//            System.out.println("Tu n'as pas de flèche");
//        }
//    }

    public void tirerAvecArc() {
        Fleche fleche;
        if (arc != null && arc.getNombreDeFleches() > 0 && peutTirerFLeches.get() && !getMortValue() && !getJeterArcValue()) {
            switch (getDirectionValue()) {
                case Direction.RIGHT, Direction.DOWN_RIGHT, Direction.UP_RIGHT :
                    fleche = new Fleche(getXValue() + getLargeur(), getYValue() + getHauteur() / 2, getDirectionValue(), 5, getEnvironnement());
                    getEnvironnement().ajouterFleche(fleche);
                    break;
                case Direction.LEFT, Direction.DOWN_LEFT, Direction.UP_LEFT:
                    fleche = new Fleche(getXValue(), getYValue() + getHauteur() / 2, getDirectionValue(), 5, getEnvironnement());
                    getEnvironnement().ajouterFleche(fleche);
                    break;
                case Direction.DOWN:
                    fleche = new Fleche(getXValue() + getLargeur()/2, getYValue() + getHauteur(), getDirectionValue(), 5, getEnvironnement());
                    getEnvironnement().ajouterFleche(fleche);
                    break;
                case Direction.UP:
                    fleche = new Fleche(getXValue() + getLargeur()/2, getYValue(), getDirectionValue(), 5, getEnvironnement());
                    getEnvironnement().ajouterFleche(fleche);
                    break;
            }
            arc.setNombreDeFleches(arc.getNombreDeFleches() - 1);
            peutTirerFLeches.set(false);
            activerCooldownFleche();
        } else if (arc == null) {
            System.out.println("Tu n'as pas d'arc");
        } else {
            System.out.println("Tu n'as pas de flèche");
        }
    }
    public void jeterArc() {
        if (arc != null && arcEquipeValue()) {
            arcJeter.set(true);
            arcPositionX = getXValue();
            arcPositionY = getYValue();
            arc = null;
            setArcEquipe(false);
            System.out.println("Arc jeter.");
        }
        else
            System.out.println("Vous n'avez pas d'arc.");
    }

    public void recupererArcJeter() {
        if (arcJeter.get() && estDansArcZone()) {
            arcJeter.set(false);
            setArcEquipe(true);
            arc = new Arc("arc", 50, 10);
            System.out.println("Arc recup.");
        }
    }
    public boolean getArcEquiperValue() {
        return arcEquipe.getValue();
    }
    public boolean getCouteauAttaqueValue() {
        return attaqueCouteau.getValue();
    }

    public void setCouteauAttaqueValue(boolean couteauValue) {
        this.attaqueCouteau.set(couteauValue);
    }
    public void attaquerCouteau() {
        if(!getMortValue() && !getArcEquiperValue()) {
            attaqueCouteau.set(true);
            Couteau couteau = new Couteau("couteau", 70, getEnvironnement());
            for (Zombie zombie : getEnvironnement().getZombies()) {
                if (getXValue() - 32 < zombie.getXValue() + zombie.getLargeur() && getXValue() + (32 * 2) > zombie.getXValue() &&
                        getYValue() - 32 < zombie.getYValue() + zombie.getHauteur() && getYValue() + (32 * 2) > zombie.getYValue()) {
                    System.out.println("Zombie à proximité");
                    if (peutAttaquerCouteau.get()) {
                        System.out.println("Le zombie a été attaqué au couteau");
                        zombie.recevoirDegats(couteau.getPtAtt());
                        peutAttaquerCouteau.set(false);
                    }
                } else {
                    System.out.println("Zombie non détecté à proximité");
                }
            }
            activerCooldownCouteau();
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
    public void activerCooldownCouteau() {
        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            attaqueCouteau.set(false);
            peutAttaquerCouteau.set(true);
        }));
        cooldownTimeline.play();
    }


//    private void activerCooldownCouteau() {
//        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> peutAttaquerCouteau.set(true)));
//        cooldownTimeline.play();
//    }

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
        if(getPvValue() <= 0) {
            setMortValue(true);
        }
        if(!getMortValue()) {
            int[] tabindice = super.move();
            linkVerification(tabindice[0], tabindice[1]);
        }

    }


    public void pousserPierre(int direction, Obstacle pierre) {
        int newX = pierre.getXValue();
        int newY = pierre.getYValue();
        int terrainLargeur = getEnvironnement().getLargeur();
        int terrainHauteur = getEnvironnement().getHauteur();

        switch (direction) {
            case Direction.UP:
                newY -= 3;
                break;
            case Direction.DOWN:
                newY += 3;
                break;
            case Direction.LEFT:
                newX -= 3;
                break;
            case Direction.RIGHT:
                newX += 3;
                break;
            case Direction.UP_LEFT:
            case Direction.UP_RIGHT:
            case Direction.DOWN_LEFT:
            case Direction.DOWN_RIGHT:
                break;
        }


        if (newX >= 0 && newX <= terrainLargeur - pierre.getLargeur() && newY >= 0 && newY <= terrainHauteur - pierre.getHauteur() && getEnvironnement().nouvellePositionValide(newX, newY)) {
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
        } else {
            if (direction == Direction.LEFT && newX >= 0) {
                pierre.move(-3, 0);
            } else if (direction == Direction.RIGHT && newX <= terrainLargeur - pierre.getLargeur()) {
                pierre.move(3, 0);
            } else if (direction == Direction.UP && newY >= 0) {
                pierre.move(0, -3);
            } else if (direction == Direction.DOWN && newY <= terrainHauteur - pierre.getHauteur()) {
                pierre.move(0, 3);
            } else {
                System.out.println("Deplacement impossible hors terrain");
            }
        }
    }



    public void linkVerification(int x, int y) {
        Obstacle obstacle = recupererObstacle(x, y);
        if (obstacle != null) {
            pousserPierre(getDirectionValue(), obstacle);
        }

        if (canMove(getDirectionValue(), x, y)) {
            setXValue(x);
            setYValue(y);
        }
    }
    public String parlerPnjProche() {
        for(int i = 0; i < getEnvironnement().getPnjs().size();i++) {
            if(estDansZonePnj(getEnvironnement().getPnjs().get(i).getXValue(),getEnvironnement().getPnjs().get(i).getYValue())) {
                return getEnvironnement().getPnjs().get(i).parler();
            }
        }
        return null;
    }

    public boolean linkEstDansZoneTeleportation() {
        // Coordonne pour les tests

        int minX = 600;
        int maxX = 625;
        int minY = 280;
        int maxY = 320;

        // Vrai coordonnee ( en bas a droite de la map )


        int linkX = getXValue();
        int linkY = getYValue();
        System.out.println(linkX);
        System.out.println(linkY);
        return (linkX >= minX && linkX <= maxX && linkY >= minY && linkY <= maxY);
    }
    public BooleanProperty getArcEquipeProperty() {
        return arcEquipe;
    }
    public BooleanProperty getAttaqueCouteauProperty() {
        return attaqueCouteau;
    }
    public boolean getJeterArcValue() {
        return arcJeter.getValue();
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

    public boolean estDansZoneCoffre(Coffre coffre) {
        return getXValue() - getHauteur() < coffre.getXValue() + coffre.getLargeur() &&
                getXValue() + (getHauteur() * 2) > coffre.getXValue() &&
                getYValue() - getHauteur() < coffre.getYValue() + coffre.getHauteur() &&
                getYValue() + (getHauteur() * 2) > coffre.getYValue();
    }
    public boolean estDansArcZone() {
        return getXValue() - getHauteur() < arcPositionX + 32 &&
                getXValue() + (getHauteur() * 2) > arcPositionX &&
                getYValue() - getHauteur() < arcPositionY + 32 &&
                getYValue() + (getHauteur() * 2) > arcPositionY;
    }

    public boolean estDansZonePnj(int pnjX, int pnjY) {
        int linkX = getXValue();
        int linkY = getYValue();
        return linkX - 19 < pnjX + 32 && linkX + (19 * 2) > pnjX &&
                linkY - 32 < pnjY + 32 && linkY + (32 * 2) > pnjY;
    }
}