package sae.saezelda.modele;
public class Link extends Personnage {
    private Terrain terrain;
    private Item item;


    public Link(Terrain terrain) {
        super("Link", 0, 0, 10, 32, 19, 3, terrain, 100);
        this.terrain = terrain;
        this.item = null;

    }

    public void utiliser(Item item){
        if(item!=null){
            System.out.println("Link a ramassé " + item.getNom());
            this.item = item;
        }
    }
    public void placerBombe() {
        Bombe bombe = new Bombe("Bombe", 50, getXValue(), getYValue(), getTerrain());
        getTerrain().ajouterBombe(bombe);
        bombe.cooldownBombeEtExplose();
    }

    public void recevoirDegats(int degats) {
        setPvValue(getPvValue() - degats);
    }

    public boolean estDansZoneBombe(int bombeX, int bombeY) {
        int linkX = getXValue();
        int linkY = getYValue();

        System.out.println(linkX);
        System.out.println(linkY);
        return linkX - 19 < bombeX + 32 && linkX + (19 * 2) > bombeX &&
                linkY - 32 <bombeY + 32 && linkY + (32 * 2) > bombeY;
    }

    public boolean estDansZone(Coffre coffre){
        return getXValue()-getHauteur() < coffre.getX()+coffre.getLargeur() && getXValue()+(getHauteur()*2)>coffre.getX() && getYValue()-getHauteur()<coffre.getY()+coffre.getHauteur() && getYValue()+(getHauteur()*2)>coffre.getY();
    }
    public Terrain getTerrain() {
        return terrain;
    }





}