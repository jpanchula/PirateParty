import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/* Public Constant Variables */
public class OceanView extends JFrame  {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    private static final int HUD_BAR_X      = 20;
    private static final int HUD_BAR_Y      = WINDOW_HEIGHT - 40;
    private static final int HUD_BAR_WIDTH  = 200;
    private static final int HUD_BAR_HEIGHT = 18;

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

        // Draw all enemies
        for (Enemy en : backend.getEnemies()) {
            en.draw(g);
        }

        // Draw all CannonBalls
        for (CannonBall cb : backend.getCannonBalls()) {
            cb.draw(g);
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

    private void drawHUD(Graphics g) {
        Player p = backend.getPlayer();
        double ratio = Math.max(0, (double) p.getHealth() / p.getMaxHealth());
        int filledWidth = (int) (HUD_BAR_WIDTH * ratio);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("HP", HUD_BAR_X, HUD_BAR_Y - 4);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(HUD_BAR_X + 22, HUD_BAR_Y - HUD_BAR_HEIGHT + 4, HUD_BAR_WIDTH, HUD_BAR_HEIGHT);

        if      (ratio > 0.5)  g.setColor(Color.GREEN);
        else if (ratio > 0.25) g.setColor(Color.YELLOW);
        else                   g.setColor(Color.RED);
        g.fillRect(HUD_BAR_X + 22, HUD_BAR_Y - HUD_BAR_HEIGHT + 4, filledWidth, HUD_BAR_HEIGHT);

        g.setColor(Color.WHITE);
        g.drawRect(HUD_BAR_X + 22, HUD_BAR_Y - HUD_BAR_HEIGHT + 4, HUD_BAR_WIDTH, HUD_BAR_HEIGHT);
    }



}