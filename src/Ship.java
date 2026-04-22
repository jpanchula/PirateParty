import java.awt.*;

public class Ship {
    public static final int DIR_UP = 0, DIR_DOWN = 1, DIR_LEFT = 2, DIR_RIGHT = 3;

    /* Private Constant Variables */
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    private double x, y, dx, dy;
    private int speed;

    private Image image;
    private OceanView window;

    // TODO
    public Ship(OceanView window, int x, int y, int speed) {
        this.window = window;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void updatePosition() {
        x += dx;
        y += dy;
    }

    // TODO
    public void shoot(int x, int y) {

    }

    /* Setters */

    // Sets the image
    public void setImage(Image image) {
        this.image = image;
    }

    // Sets the velocity
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Gets the speed
    public int getSpeed() {
        return speed;
    }

    // Draws the ship
    public void draw(Graphics g) {
        // Cast to int, but keep double precision for smoother movement
        g.drawImage(image, (int)x, (int)y, WIDTH, HEIGHT, window);
    }
}
