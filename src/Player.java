import javax.swing.*;

public class Player extends Ship {
    /* Private constants */
    private static final int SPEED = 2;
    private static final int HEALTH = 100;

    private int points;

    public Player(OceanView window, int x, int y) {
        super(window, x, y, SPEED, HEALTH);
        super.setImage(new ImageIcon("Resources/pirate_ship.png").getImage());
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

        // Apply borders
        if (getX() + dx < 0 || getX() + dx + Ship.WIDTH > OceanView.WINDOW_WIDTH)
            dx = 0;
        if (getY() + dy < 20 || getY() + dy + Ship.HEIGHT > OceanView.WINDOW_HEIGHT)
            dy = 0;

        setVelocity(dx, dy);
    }

    // Calculates the player's rotation to point towards the mouse
    public void calculateRotation(int mouseX, int mouseY) {
        setRotation(Math.atan2(mouseY - (getY() + HEIGHT / 2.0), mouseX - (getX() + WIDTH / 2.0)) - (0.5 * Math.PI));
    }

    public void addPoints(int value) {
        points += value;
    }

    public int getScore() {
        return points;
    }
}
