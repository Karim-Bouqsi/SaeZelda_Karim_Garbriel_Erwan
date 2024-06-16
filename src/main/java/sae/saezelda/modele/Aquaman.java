package sae.saezelda.modele;

public class Aquaman extends Personnage{
    private int delaisTir = 120;
    private Terrain terrain;
    public Aquaman(Environnement environnement, Terrain terrain) {
        super("Aquaman", 500, 200, 20, 32, 19, 0, environnement, 50);
        this.delaisTir = delaisTir;
        this.terrain = terrain;
    }

    public boolean linkAPortee(Link link){
        if(this.getPvValue()>0 && delaisTir<0) {
            if (Math.abs(link.getXValue() - this.getXValue()) <= 150 && Math.abs(link.getYValue() - this.getYValue()) <= 150) {
                System.out.printf("a portée");
                delaisTir= 120;
                return true;
            }
        }
        delaisTir--;
        return false;
    }
}
