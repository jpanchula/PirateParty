import javax.swing.*;
import java.awt.*;

public class Gold {
    private static final int SIZE = 50;

    private int value;
    private int x, y;
    private boolean isCollected;
    private Image image;
    private OceanView window;

    /** Constructor */
    public Gold(int x, int y, int value, OceanView window) {
        this.x = x;
        this.y = y;
        isCollected = false;
        this.value = value;
        image = new ImageIcon("Resources/gold.png").getImage();
        this.window = window;
    }

    public void checkCollision(Player player) {
        // Use the player's center
        double playerX = player.getX() + Ship.WIDTH / 2.0;
        double playerY = player.getY() + Ship.HEIGHT / 2.0;
        // Use the gold's center
        double goldX = x + SIZE / 2.0;
        double goldY = y + SIZE / 2.0;
        // Calculate distance using centers
        double dist = Math.sqrt(Math.pow(goldX - playerX, 2) + Math.pow(goldY - playerY, 2));
        // Check distance
        if (dist <= SIZE / 1.5) {
            // Update the player's points and set collected to true
            player.addPoints(value);
            isCollected = true;
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, SIZE, SIZE, window);
    }
}
