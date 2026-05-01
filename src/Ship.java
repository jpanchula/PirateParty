import java.awt.*;

public class Ship {
    /* Public constants */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    /* Private constants */
    private static final int I_FRAMES = 60;

    private double x, y, dx, dy;
    private int speed;
    private int health;

    private boolean canTakeDamage;
    private int tick;

    private Image image;
    private OceanView window;

    public Ship(OceanView window, int x, int y, int speed, int health) {
        this.window = window;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        canTakeDamage = true;
    }

    // Updates the position based on dx and dy
    public void updatePosition() {
        // Update the positon
        x += dx;
        y += dy;

        // If the ship was attacked
        if (!canTakeDamage) {
            tick++;
        }
        if (tick >= I_FRAMES) {
            tick = 0;
            canTakeDamage = true;
        }
    }

    public void takeDamage(int amount) {
        if (canTakeDamage) {
            health -= amount;
            tick = 0;
            canTakeDamage = false;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    /* Setters */

    public void setImage(Image image) {
        this.image = image;
    }

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /* Getters */

    public int getSpeed() {
        return speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean canTakeDamage() {
        return canTakeDamage;
    }

    public Image getImage() {
        return image;
    }

    public OceanView getWindow() {
        return window;
    }

    public int getHealth() {
        return health;
    }

    // Draws the ship
    public void draw(Graphics g) {
        g.drawImage(image, (int)x, (int)y, WIDTH, HEIGHT, window);
    }
}