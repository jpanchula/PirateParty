import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Random;

public class Enemy extends Ship {
    // Slightly slower than the player
    private static final int SPEED = 1;
    private static final int SHOOT_COOLDOWN = 180;
    private int shootTimer;

    public Enemy(OceanView window, int x, int y, int health) {
        super(window, x, y, SPEED, health);
        super.setImage(makeEnemyImage());
        shootTimer = new Random().nextInt(SHOOT_COOLDOWN);
    }

    // Returns the pirate ship image with a red tint applied
    private static Image makeEnemyImage() {
        Image src = new ImageIcon("Resources/pirate_ship.png").getImage();

        BufferedImage buf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D temp = buf.createGraphics();
        temp.drawImage(src, 0, 0, WIDTH, HEIGHT, null);
        temp.dispose();

        float[] scales = { 1.5f, 0.4f, 0.4f, 1.0f };
        float[] offsets = { 0f, 0f, 0f, 0f };
        return new RescaleOp(scales, offsets, null).filter(buf, null);
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
