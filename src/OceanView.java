import javax.swing.*;
import java.awt.*;

public class OceanView extends JFrame {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

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

    // TODO
    public void paint(Graphics g) {
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
