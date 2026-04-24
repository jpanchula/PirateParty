import java.awt.*;

public class CannonBall {
    private static final double GRAVITY = 9.8;
    private static final int RADIUS = 10;
    private static final int SPEED = 5; // pixels per frame

    private double x, y;           // current position (floating point for smooth motion)
    private double startX, startY;
    private int finalX, finalY;
    private double dx, dy;          // per-frame velocity components
    private boolean isExploding;
    private boolean done;

    public CannonBall(double startX, double startY, int endX, int endY) {
        this.x = startX;
        this.y = startY;
        this.startX = startX;
        this.startY = startY;
        this.finalX = endX;
        this.finalY = endY;
        this.isExploding = false;
        this.done = false;



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

    public int damage() {
        return 10;
    }

    /** Returns true when the ball has reached (or passed) its target. */
    public boolean isTouching() {
        // Check if we've passed the target by seeing if the remaining distance
        // is now smaller than one step, or if we've overshot
        double remainX = finalX - x;
        double remainY = finalY - y;
        // If the dot product of remaining vector and original direction is <= 0 we've arrived
        double dot = remainX * dx + remainY * dy;
        return dot <= 0;
    }

    public boolean isDone() {
        return done;
    }

    public void updatePosition() {
        if (isExploding || done) return;

        if (isTouching()) {
            x = finalX;
            y = finalY;
            done = true;
        } else {
            x += dx;
            y += dy;
        }
    }

    public void draw(Graphics g) {
        if (done) return;
        g.setColor(Color.BLACK);
        g.fillOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
    }
}
