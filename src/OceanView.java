import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class OceanView extends JFrame {
    /* Public Constant Variables */
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    /* Private Constant Variables */
    private static final Color BACKGROUND_COLOR = new Color(50, 50, 200);

    private Ocean backend;

    // TODO:
    public OceanView(Ocean backend) {
        this.backend = backend;

        this.setTitle("Pirate Party");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        createBufferStrategy(2);
    }

    // Deals with double buffering stuff and calls myPaint
    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        }
        finally {
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
    }

    // TODO
    private void drawMenu(Graphics g) {

    }

    // TODO
    private void drawOcean(Graphics g) {
        drawBackground(g);
        backend.getPlayer().draw(g);
    }

    // TODO
    private void drawEnd(Graphics g) {

    }

    private void drawBackground(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
