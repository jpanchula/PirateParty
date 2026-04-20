import javax.swing.*;
import java.awt.*;

public class OceanView extends JFrame {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;
    private Ocean backend;

    // TODO:
    public OceanView(Ocean backend) {
        this.backend = backend;

        this.setTitle("Pirate Party");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // TODO
    public void paint(Graphics g) {

    }

    // TODO
    public void drawMenu(Graphics g) {

    }

    // TODO
    public void drawOcean(Graphics g) {

    }

    // TODO
    public void drawEnd(Graphics g) {

    }
}
