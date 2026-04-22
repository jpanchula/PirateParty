// Pirate Party by Jacob Panchula and Sachin Sandhu

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ocean implements KeyListener, ActionListener {
    /* Public Constant Variables */
    public static final int STATE_MENU = 0;
    public static final int STATE_PLAY = 1;
    public static final int STATE_END = 2;

    /* Private Constant Variables */
    private static final int DELAY_MILLISECONDS = 16;

    private OceanView window;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<CannonBall> cannonBalls;

    private int waveNum;
    private int shipsLeft;
    private int state;

    // Vertical and horizontal inputs
    private boolean up, down, left, right;
    private int vertical = 0, horizontal = 0;

    /* Constructor */
    public Ocean() {
        // Create the window and attach the KeyListener
        window = new OceanView(this);
        window.addKeyListener(this);

        player = new Player(window, OceanView.WINDOW_WIDTH / 2, OceanView.WINDOW_HEIGHT / 2);
        state = STATE_PLAY;
        waveNum = 0;
        shipsLeft = 0;

        // Create and start a timer
        Timer clock = new Timer(DELAY_MILLISECONDS, this);
        clock.start();
    }

    // Performed ecah
    public void actionPerformed(ActionEvent e) {
        // Calculate the player's velocity based on the current inputs
        player.calculateVelocity(up, down, left, right);
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
