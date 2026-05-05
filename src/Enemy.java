import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends Ship {
    // Slightly slower than the player
    private static final int SPEED = 1;
    private static final int HEALTH = 10;
    private static final int SHOOT_COOLDOWN = 180;
    private int shootTimer;

    public Enemy(OceanView window, int x, int y, int health) {
        super(window, x, y, SPEED, health);
        super.setImage(new ImageIcon("Resources/pirate_ship.png").getImage());
        shootTimer = new Random().nextInt(SHOOT_COOLDOWN);
    }

    public void tickShootTimer() {
        if (shootTimer > 0) shootTimer--;
    }

    public boolean canShoot() {
        return shootTimer <= 0;
    }

    public void resetShootTimer() {
        shootTimer = SHOOT_COOLDOWN;
    }

    /**
     * Recalculates velocity each frame so the enemy always steers
     * directly toward the player's current center position.
     */
    public void chasePlayer(double playerCenterX, double playerCenterY) {
        double dx = playerCenterX - (getX() + Ship.WIDTH / 2.0);
        double dy = playerCenterY - (getY() + Ship.HEIGHT / 2.0);
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist > 0.5) {
            // Normalize then scale by speed
            setVelocity((dx / dist) * getSpeed(), (dy / dist) * getSpeed());
        } else {
            setVelocity(0, 0);
        }
    }
}
