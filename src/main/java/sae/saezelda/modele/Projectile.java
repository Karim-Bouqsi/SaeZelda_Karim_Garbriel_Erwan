package sae.saezelda.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Projectile extends Personnage{

    private int directionX = 0;
    private int directionY = 0;
    private BooleanProperty attaqueLink = new SimpleBooleanProperty(false);
    private Terrain terrain;

    public Projectile(Environnement environnement, Terrain terrain) {
        super("Projectile", 0, 0, 20, 32, 19, 0, environnement, 50);
        this.directionX = directionX;
        this.directionY = directionY;
        this.terrain = terrain;

    }

    public BooleanProperty attaqueLinkProperty() {
        return attaqueLink;
    }

    public void tir(int aquamanX, int aquamanY, int linkX, int linkY){
        if(Math.abs(this.getXValue()-aquamanX)>150 || Math.abs(this.getYValue()-aquamanY)>150) {
            System.out.println("test2");
            this.setXValue(aquamanX);
            this.setYValue(aquamanY);
            this.setDirectionX(Math.round((linkX - aquamanX) / 20));
            this.setDirectionY(Math.round((linkY - aquamanY) / 20));
        }
    }

    public void bouge(){
        this.setXValue(this.getXValue()+this.directionX);
        this.setYValue(this.getYValue()+this.directionY);
        attaquerLink();
    }

    public void attaquerLink() {
        Environnement environnement = getEnvironnement();
        Link link = environnement.getLink();

        if (!super.getMortValue()) {
            int distanceX = Math.abs(getXValue() - link.getXValue());
            int distanceY = Math.abs(getYValue() - link.getYValue());
            int proximite = 2;

            if (distanceX <= proximite && distanceY <= proximite) {
                attaqueLink.set(true);
                link.recevoirDegats(10);
            } else {
                attaqueLink.set(false);
            }
        }
    }

    public void setDirectionX(int directionX){
        this.directionX = directionX;
    }

    public void setDirectionY(int directionY){
        this.directionY = directionY;
    }
}
