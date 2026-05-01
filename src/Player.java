import javax.swing.*;
import java.awt.*;

public class Player extends Ship {
    private static final int SPEED = 2;
    private static final int HEALTH = 100;

    private int points;
    private double rotation;

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

        setVelocity(dx, dy);
    }

    public void calculateRotation(int mouseX, int mouseY) {
        rotation = Math.atan2(mouseY - (getY() + HEIGHT / 2), mouseX - (getX() + WIDTH / 2)) - (0.5 * Math.PI);
    }

    public void addPoints(int value) {
        points += value;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        // If i-frames are active
        if (!canTakeDamage()) {
            // Set color to 50% transparent
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        // Set rotation
        g2d.rotate(rotation, getX() + WIDTH / 2, getY() + HEIGHT / 2);

        // Draw the ship
        g2d.drawImage(getImage(), (int)getX(), (int)getY(), WIDTH, HEIGHT, getWindow());

        // Reset transparency and rotation
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.rotate(-rotation, getX() + WIDTH / 2, getY() + HEIGHT / 2);
    }
}
