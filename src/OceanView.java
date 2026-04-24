import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/* Public Constant Variables */
public class OceanView extends JFrame  {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    /* Private Constant Variables */
    private static final Color BACKGROUND_COLOR = new Color(50, 50, 200);

    private Ocean backend;

    // Double buffer fields
    private Image offscreenImage;
    private Graphics offscreenGraphics;

    public OceanView(Ocean backend) {
        this.backend = backend;


        this.setTitle("Pirate Party");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        createBufferStrategy(2);
    }

    // Deals with double buffering stuff and calls myPaint
    @Override
    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            createBufferStrategy(2);
        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        } finally {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }


    // Main paint function
    public void myPaint(Graphics g) {
        switch (backend.getState()) {
            case Ocean.STATE_MENU:
                drawMenu(g);
                break;
            case Ocean.STATE_PLAY:
                drawOcean(g);
                break;
            case Ocean.STATE_END:
                drawEnd(g);
                break;
        }


        drawOcean(g);


        g.drawImage(offscreenImage, 0, 0, this);
    }

    // Suppress the default Swing clear-then-paint that causes flicker
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void drawMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Pirate Party", WINDOW_WIDTH / 2 - 90, WINDOW_HEIGHT / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Click to start", WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2 + 40);
        // TODO
    }

    public void drawOcean (Graphics g) {
        // Draw ocean background
        drawBackground(g);
        backend.getPlayer().draw(g);

        // Draw all cannon balls
        for (CannonBall cb : backend.getCannonBalls()) {
            cb.draw(g);
        }

        for (Enemy en : backend.getEnemies()) {
            en.draw(g);
        }
    }


    public void drawEnd (Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Game Over", WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2);
    }

    private void drawBackground (Graphics g){
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }



}