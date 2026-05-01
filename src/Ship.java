import java.awt.*;

public class Ship {
    /* Public constants */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    /* Private constants */
    private static final int I_FRAMES = 60;
    private static final int BAR_WIDTH  = 60;
    private static final int BAR_HEIGHT = 8;
    private static final int BAR_OFFSET = 6;

    private double x, y, dx, dy;
    private int speed;
    private int health;
    private int maxHealth;

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
        this.maxHealth = health;
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

    public int    getHealth()      { return health; }

    public int    getMaxHealth()   { return maxHealth; }

    private void drawHealthBar(Graphics2D g) {
        int barX = (int) x + (WIDTH - BAR_WIDTH) / 2;
        int barY = (int) y - BAR_HEIGHT - BAR_OFFSET;

        double ratio = Math.max(0, (double) health / maxHealth);
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

    // Draws the ship
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        if (!canTakeDamage) {
            // 50% transparent during i-frames
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        // Draw the ship
        g2d.drawImage(image, (int)x, (int)y, WIDTH, HEIGHT, window);

        // Reset composite
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        drawHealthBar(g2d);

    }
}