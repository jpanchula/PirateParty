import javax.swing.*;

public class Player extends Ship {
    private int health;
    private int points;

    // TODO
    public Player(OceanView window, int x, int y) {
        super(window, x, y, 2);
        super.setImage(new ImageIcon("Resources/pirate_ship.png").getImage());
        health = 100;
        points = 0;
    }

    // Calculates and updates the player's velocity based on input
    public void calculateVelocity(boolean up, boolean down, boolean left, boolean right) {
        // Update dy/dx
        double dy = 0, dx = 0;
        if (up)
            dy -= getSpeed();
        if (down)
            dy += getSpeed();
        if (left)
            dx -= getSpeed();
        if (right)
            dx += getSpeed();

        // Normalize the speed so diagonal movement isn't faster
        if (dy != 0 && dx != 0) {
            // Divide by sqrt(2) because of Pythagorean theorem
            dx /= Math.sqrt(2);
            dy /= Math.sqrt(2);
        }

        setVelocity(dx, dy);
    }

    public boolean isAlive() {
        return health > 0;
    }
}
