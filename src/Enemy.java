import javax.swing.*;
import java.awt.*;

public class Enemy extends Ship {
    // Slightly slower than the player
    private static final int SPEED = 1;
    private static final int HEALTH = 10;

    public Enemy(OceanView window, int x, int y) {
        super(window, x, y, SPEED, HEALTH);
        super.setImage(new ImageIcon("Resources/pirate_ship.png").getImage());
    }

    /**
     * Recalculates velocity each frame so the enemy always steers
     * directly toward the player's current center position.
     */
    public void chasePlayer(double playerCenterX, double playerCenterY) {
        double dx = playerCenterX - (getX() + 50);
        double dy = playerCenterY - (getY() + 50);
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist > 0) {
            // Normalize then scale by speed
            setVelocity((dx / dist) * getSpeed(), (dy / dist) * getSpeed());
        } else {
            setVelocity(0, 0);
        }
    }
}
