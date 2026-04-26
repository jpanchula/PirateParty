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
                drawPlay(g);
                break;
            case Ocean.STATE_END:
                drawEnd(g);
                break;
        }
    }


    private void drawMenu(Graphics g) {
        // TODO
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Pirate Party", WINDOW_WIDTH / 2 - 90, WINDOW_HEIGHT / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Click to start", WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2 + 40);
    }

    private void drawPlay (Graphics g) {
        // Draw ocean background
        drawBackground(g);

        // Draw the player
        backend.getPlayer().draw(g);

        // Draw all CannonBalls
        for (CannonBall cb : backend.getCannonBalls()) {
            cb.draw(g);
        }

        // Draw all enemies
        for (Enemy en : backend.getEnemies()) {
            en.draw(g);
        }
    }


    private void drawEnd (Graphics g) {
        // Black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        // White text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Game Over", WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2);
    }

    private void drawBackground (Graphics g){
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }



}