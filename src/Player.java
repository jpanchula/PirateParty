import javax.swing.*;

public class Player extends Ship {
    private static final int SPEED  = 2;
    private static final int HEALTH = 100;

    private int points;

    public Player(OceanView window, int x, int y) {
        super(window, x, y, SPEED, HEALTH);
        super.setImage(new ImageIcon("Resources/pirate_ship.png").getImage());
        points = 0;
    }

    public void calculateVelocity(boolean up, boolean down, boolean left, boolean right) {
        double dy = 0, dx = 0;
        if (up)    dy -= getSpeed();
        if (down)  dy += getSpeed();
        if (left)  dx -= getSpeed();
        if (right) dx += getSpeed();

        if (dy != 0 && dx != 0) {
            dx /= Math.sqrt(2);
            dy /= Math.sqrt(2);
        }

        setVelocity(dx, dy);
    }

    public boolean isAlive()         { return !isDead(); }
    public int getPoints()           { return points; }
    public void addPoints(int pts)   { points += pts; }
}