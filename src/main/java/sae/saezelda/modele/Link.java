package sae.saezelda.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class Link extends Personnage {
    private Item item;
    private Item arme;
    private Item armure;
    public static final int PV_MAX = 100;
    private ObservableList<Item> invetaire;
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
    private int nbBombe;

    private Terrain terrain;

    public Link(Environnement environnement, Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, environnement, 100);
        this.item = null;
        this.arme = new Couteau("bi",2,environnement);
        this.armure=null;
        this.arc=new Arc("faa",12,12);
        this.invetaire = FXCollections.observableArrayList();
        this.peutPoserBombe = new SimpleBooleanProperty(true);
        this.peutTirerFLeches = new SimpleBooleanProperty(true);
        this.peutAttaquerCouteau = new SimpleBooleanProperty(true);
        this.terrain = terrain;
        this.arcEquipe = new SimpleBooleanProperty(false);
        this.attaqueCouteau = new SimpleBooleanProperty(false);
        this.arcJeter = new SimpleBooleanProperty(false);
        this.nbBombe=2;
    }
    public ObservableList getInventaire(){
        return invetaire;
    }

    public void equiper(Item item){
        if(item instanceof Arme){
            if(this.arme==null) {
                this.arme=item;
                getInventaire().remove(item);
            }
            else {
                this.arme=this.arc;
                Item pivot = this.arme;
                this.arme=item;
                desequiperArc();
                peutTirerFLeches.setValue(false);
                getInventaire().remove(item);
                this.invetaire.add(pivot);

            }
            if(item instanceof Arc){
                setArcEquipe(true);
                peutTirerFLeches.setValue(true);
                equiperArc(arc);
            }
        }
        else if (item instanceof PotionVie){
            boire((PotionVie) item);
            getInventaire().remove(item);

        }
    }
    public void desquiperArme(){
        Item pivot = this.arme;
        this.arme=null;
        this.invetaire.add(pivot);

    }
    public void desequiperArmure(){
        Item pivot = this.armure;
        this.armure=null;
        this.invetaire.add(pivot);
    }
    public Item getArme(){
        return this.arme;
    }
    public Item getArmure(){
        return this.armure;
    }
    public void boire(PotionVie potion){
        if (getPvValue()==PV_MAX) System.out.println("Tu ne peux pas boire pv max");
        else if (getPvValue()>PV_MAX-20){
            System.out.println("Pv regenerer 100pv");
            this.setPvValue(100);

        }
        else{
            System.out.println("regene pv");
            this.setPvValue(getPvValue()+ potion.getPv());
        }
    }


    public void utiliser(Item item) {
        if (item != null) {// regler item / inventaire
            System.out.println("Link a ramassé " + item.getNom());
            this.item = item;
            invetaire.add(item);
//            if(item instanceof Arc) {
//                this.arcEquipe.set(true);
//            }
        }
    }


    public void placerBombe() {
        if (peutPoserBombe.get() && !getMortValue()&&this.nbBombe>0 && this.arme instanceof Bombe) {
            Bombe bombe = new Bombe("Bombe", 50, getXValue(), getYValue(), getEnvironnement());
            getEnvironnement().ajouterBombe(bombe);
            bombe.cooldownBombeEtExplose();
            peutPoserBombe.set(false);
            activerCooldownBombe();
            this.nbBombe--;
        } else if (nbBombe==0) {
            System.out.println("tu n'as plus de bombe");
            this.arme=null;
        } else {
            System.out.println("Doucement les bombes");
        }
    }
    public BooleanProperty arcJeterProperty() {
        return arcJeter;
    }
    public void setArcJeterValue(boolean arcJeterValue) {
        arcJeter.set(arcJeterValue);
    }

    public void creerEtAjouterFleche(int x, int y, int direction) {
        Fleche fleche = new Fleche(x, y, direction, 5, getEnvironnement());
        getEnvironnement().ajouterFleche(fleche);
    }
    public void tirerAvecArc() {
        if (arc != null && arc.getNombreDeFleches() > 0 && peutTirerFLeches.get() && !getMortValue() && !getJeterArcValue() && this.arme instanceof Arc) {
            int flecheX = 0, flecheY = 0;
            switch (getDirectionValue()) {
                case Direction.RIGHT, Direction.DOWN_RIGHT, Direction.UP_RIGHT:
                    flecheX = getXValue() + getLargeur();
                    flecheY = getYValue() + getHauteur() / 2;
                    break;
                case Direction.LEFT, Direction.DOWN_LEFT, Direction.UP_LEFT:
                    flecheX = getXValue();
                    flecheY = getYValue() + getHauteur() / 2;
                    break;
                case Direction.DOWN:
                    flecheX = getXValue() + getLargeur() / 2;
                    flecheY = getYValue() + getHauteur();
                    break;
                case Direction.UP:
                    flecheX = getXValue() + getLargeur() / 2;
                    flecheY = getYValue();
                    break;
            }
            creerEtAjouterFleche(flecheX, flecheY, getDirectionValue());
            arc.setNombreDeFleches(arc.getNombreDeFleches() - 1);
            peutTirerFLeches.set(false);
            activerCooldownFleche();
        } else {
            if (arc == null) {
                System.out.println("Tu n'as pas d'arc");
            } else {
                System.out.println("Tu n'as pas de flèche");
            }
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
        if(!getMortValue() && !getArcEquiperValue() && this.arme instanceof Couteau) {
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
        switch (direction) {
            case Direction.UP:
                deplacerPierre(pierre, 0, -3);
                break;
            case Direction.DOWN:
                deplacerPierre(pierre, 0, 3);
                break;
            case Direction.LEFT:
                deplacerPierre(pierre, -3, 0);
                break;
            case Direction.RIGHT:
                deplacerPierre(pierre, 3, 0);
                break;
            case Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT:
                break;
        }
    }


    public void deplacerPierre(Obstacle pierre, int x, int y) {
        int terrainLargeur = getEnvironnement().getLargeur();
        int terrainHauteur = getEnvironnement().getHauteur();
        int newX = pierre.getXValue() + x;
        int newY = pierre.getYValue() + y;
        if (newX >= 0 && newX <= terrainLargeur - pierre.getLargeur() && newY >= 0 && newY <= terrainHauteur - pierre.getHauteur() && getEnvironnement().nouvellePositionValide(newX, newY)) {
            pierre.move(x, y);
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