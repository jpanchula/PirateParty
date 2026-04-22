import javax.swing.*;
import java.awt.*;

public class Player extends Ship {
    private int health;
    private int points;
    private int speed;

    // TODO
    public Player(OceanView window, int x, int y) {
        super(window, x, y);
        super.setImage(new ImageIcon("Resources/pirate_ship.png").getImage());
        health = 100;
        points = 0;
        speed = 2;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
