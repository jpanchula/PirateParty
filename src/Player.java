import javax.swing.*;
import java.awt.*;

public class Player extends Ship {
    private int health;
    private int points;

    // TODO
    public Player(OceanView window, int x, int y) {
        super(window, x, y);
        health = 100;
        points = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    // TODO
    @Override
    public void move() {

    }
}
