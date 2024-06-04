package sae.saezelda.modele;

import sae.saezelda.Main;

public class Pierre extends Obstacle {

    public Pierre(int x, int y) {
        super(x, y, String.valueOf(Main.class.getResource("/image/pierre.png")), 32,32);
    }
}
