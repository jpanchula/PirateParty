import javax.swing.*;
import java.awt.*;

public class OceanView extends JFrame {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    private static final Color backgroundColor = Color.BLUE;

    private Ocean backend;

    // TODO:
    public OceanView(Ocean backend) {
        this.backend = backend;

        createBufferStrategy(2);
        this.setTitle("Pirate Party");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
    public void drawMenu(Graphics g) {

    }

    // TODO
    public void drawOcean(Graphics g) {
        backend.getPlayer().draw(g);
    }

    // TODO
    public void drawEnd(Graphics g) {

    }
}
