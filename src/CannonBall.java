import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CannonBall {
    /* Private Constants */
    private static final double GRAVITY = 9.8;
    private static final int RADIUS = 10;
    // Pixels per frame
    private static final int SPEED = 5;

    // Explosion sprite config
    private static final int FRAME_COUNT = 16;
    private static final int FRAME_SIZE = 64;
    private static final int FRAMES_PER_ROW = 4;
    private static final int TICKS_PER_FRAME = 2;
    private static final int EXPLOSION_SIZE = 100;

    /* Static config */

    private static BufferedImage[] explosionFrames;

    // Executes before main
    static {
        loadExplosionFrames();
    }

    // Load the explosion frames to the explosionFrames BufferedImage array
    private static void loadExplosionFrames() {
        try {
            BufferedImage sheet = ImageIO.read(Objects.requireNonNull(CannonBall.class.getResourceAsStream("explosion_spritesheet.png")));
            explosionFrames = new BufferedImage[FRAME_COUNT];
            for (int i = 0; i < FRAME_COUNT; i++) {
                int col = i % FRAMES_PER_ROW;
                int row = i / FRAMES_PER_ROW;
                explosionFrames[i] = sheet.getSubimage(col * FRAME_SIZE, row * FRAME_SIZE, FRAME_SIZE, FRAME_SIZE);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Could not load explosion_spritesheet.png: " + e.getMessage());
            explosionFrames = null;
        }
    }

    /* Instance variables */

    // Current position (floating point for smooth motion)
    private double x, y;
    private double startX, startY;
    private int finalX, finalY;
    // Per-frame velocity components
    private double dx, dy;

    // Explosion related stuff
    private boolean isExploding;
    private int frameTick;
    private int currentFrame;
    private int damage;

    private boolean done;
    private OceanView window;

    /* Constructor */
    public CannonBall(double startX, double startY, int endX, int endY, OceanView window) {
        this.x = startX;
        this.y = startY;
        this.startX = startX;
        this.startY = startY;
        this.finalX = endX;
        this.finalY = endY;
        this.isExploding = false;
        this.done = false;
        this.damage = 10;
        this.window = window;

        // Compute unit direction vector scaled to SPEED
        double dist = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        if (dist == 0) {
            dx = 0;
            dy = 0;
        } else {
            dx = (endX - startX) / dist * SPEED;
            dy = (endY - startY) / dist * SPEED;
        }
    }

    /** Returns true when the ball has reached (or passed) its target. */
    private boolean isTouching() {
        // Check if we've passed the target by seeing if the remaining distance
        // is now smaller than one step, or if we've overshot
        double remainX = finalX - x;
        double remainY = finalY - y;
        // If the dot product of remaining vector and original direction is <= 0 we've arrived
        double dot = remainX * dx + remainY * dy;
        return dot <= 0;
    }

    public void updatePosition() {
        // Handle calling updatePosition on a cannonball that is done
        if (done) return;

        // If the cannonball is touching its target
        if (isExploding) {
            // Advance the animation
            frameTick++;
            // Update the current frame
            if (frameTick >= TICKS_PER_FRAME) {
                frameTick = 0;
                currentFrame++;
                // Check if the current frame is more than the frame count
                if (currentFrame >= FRAME_COUNT)
                    done = true;
            }
        }
        else if (isTouching()) {
            // Begin exploding at that position
            x = finalX;
            y = finalY;
            isExploding = true;
        }
        else {
            x += dx;
            y += dy;
        }
    }

    // Apply damage
    public void applyDamage(ArrayList<Ship> ships) {
        if (!isExploding)
            return;

        int explosionRadius = EXPLOSION_SIZE / 2;
        for (Ship ship : ships) {
            // Use the ships center for distance check
            double shipCenterX = ship.getX() + Ship.WIDTH / 2.0;
            double shipCenterY = ship.getY() + Ship.HEIGHT / 2.0;
            double dist = Math.sqrt(Math.pow(x - shipCenterX, 2) + Math.pow(y - shipCenterY, 2));
            if (dist <= explosionRadius) {
                ship.takeDamage(damage);
            }
        }
    }

    public void draw(Graphics g) {
        if (done) return;

        if (!isExploding) {
            g.setColor(Color.BLACK);
            g.fillOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
        }
        else {
            int drawX = (int)x - EXPLOSION_SIZE / 2;
            int drawY = (int) y - EXPLOSION_SIZE / 2;
            g.drawImage(explosionFrames[Math.min(currentFrame, FRAME_COUNT - 1)],
                    drawX, drawY,
                    EXPLOSION_SIZE, EXPLOSION_SIZE, null);
        }
    }

    /* Getters */

    public boolean isDone() {
        return done;
    }
}