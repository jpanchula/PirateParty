// Pirate Party by Jacob Panchula and Sachin Sandhu

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ocean implements KeyListener, ActionListener {
    public static final int STATE_MENU = 0;
    public static final int STATE_PLAY = 1;
    public static final int STATE_END = 2;

    private OceanView window;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<CannonBall> cannonBalls;

    private int waveNum;
    private int shipsLeft;
    private int state;

    private boolean up, down, left, right;

    /* Constructor */
    public Ocean() {
        // Create the window and attach the KeyListener
        window = new OceanView(this);
        window.addKeyListener(this);

        player = new Player(window, OceanView.WINDOW_WIDTH / 2, OceanView.WINDOW_HEIGHT / 2);
        state = STATE_PLAY;
        waveNum = 0;
        shipsLeft = 0;

        // Reduce buffering
//        Toolkit.getDefaultToolkit().sync();

        // Create and start a timer
        Timer clock = new Timer(16, this);
        clock.start();
    }

    // TODO
    public void actionPerformed(ActionEvent e) {
        int dx = 0;
        int dy = 0;

        if (up)
            dy -= player.getSpeed();
        if (down)
            dy += player.getSpeed();
        if (left)
            dx -= player.getSpeed();
        if (right)
            dx += player.getSpeed();

        player.setVelocity(dx, dy);
        player.updatePosition();

        window.repaint();
    }

    // TODO
    public void spawnCannonBall(int spawnX, int spawnY, int endX, int endY) {

    }

    // TODO
    public void spawnExplosion(int x, int y) {

    }

    /* KeyListener methods */

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
        }
    }

    /* Getters */

    public int getState() {
        return state;
    }

    public ArrayList<CannonBall> getCannonBalls() {
        return cannonBalls;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWaveNum() {
        return waveNum;
    }

    public int getShipsLeft() {
        return shipsLeft;
    }

    /* Main */

    public static void main(String[] args) {
        Ocean game = new Ocean();
    }
}
