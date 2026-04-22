import java.awt.*;

public class Ship {
    public static final int DIR_UP = 0, DIR_DOWN = 1, DIR_LEFT = 2, DIR_RIGHT = 3;

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    private int x, y, dx, dy;

    private Image image;
    private OceanView window;

    // TODO
    public Ship(OceanView window, int x, int y) {
        this.window = window;
        this.x = x;
        this.y = y;
    }

    public void setVelocity(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void updatePosition() {
        x += dx;
        y += dy;
    }

    // TODO
    public void shoot(int x, int y) {

    }

    // TODO
    public void setImage(Image image) {
        this.image = image;
    }

    // TODO
    public void draw(Graphics g) {
        g.drawImage(image, x, y, WIDTH, HEIGHT, window);
    }
}
