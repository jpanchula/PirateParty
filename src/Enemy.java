import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Random;

public class Enemy extends Ship {
    /* Private Constants */
    private static final int BAR_WIDTH  = 60;
    private static final int BAR_HEIGHT = 8;
    private static final int BAR_OFFSET = 6;
    // Slightly slower than the player
    private static final int SPEED = 1;
    private static final int SHOOT_COOLDOWN = 180;

    /* Instance Variables */
    private int shootTimer;

    /* Constructor */
    public Enemy(OceanView window, int x, int y, int health) {
        super(window, x, y, SPEED, health);
        super.setImage(makeEnemyImage());
        shootTimer = new Random().nextInt(SHOOT_COOLDOWN);
    }

    // Returns the pirate ship image with a red tint applied
    private static Image makeEnemyImage() {
        Image src = new ImageIcon("Resources/pirate_ship.png").getImage();

        BufferedImage buf = new BufferedImage(src.getWidth(null), src.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D temp = buf.createGraphics();
        temp.drawImage(src, 0, 0, src.getWidth(null), src.getHeight(null), null);
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
        calculateRotation();
    }

    // Sets the rotation to point in the direction of velocity
    private void calculateRotation() {
        setRotation(Math.atan2(getDy(), getDx()) - (0.5 * Math.PI));
    }

    // Draws the health bar above the ship
    private void drawHealthBar(Graphics2D g) {
        int barX = (int) getX() + (WIDTH - BAR_WIDTH) / 2;
        int barY = (int) getY() - BAR_HEIGHT - BAR_OFFSET;

        double ratio = Math.max(0, (double) getHealth() / getMaxHealth());
        int filledWidth = (int) (BAR_WIDTH * ratio);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, BAR_WIDTH, BAR_HEIGHT);

        if      (ratio > 0.5)  g.setColor(Color.GREEN);
        else if (ratio > 0.25) g.setColor(Color.YELLOW);
        else                   g.setColor(Color.RED);
        g.fillRect(barX, barY, filledWidth, BAR_HEIGHT);

        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, BAR_WIDTH, BAR_HEIGHT);
    }

    @Override
    public void draw(Graphics g) {
        // Draw the health bar after drawing the ship
        super.draw(g);
        drawHealthBar((Graphics2D)g);
    }
}
