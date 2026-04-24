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
    // Frames per second for the game loop
    private static final int FPS = 60;
    private static final int FRAME_DELAY_MS = 1000 / FPS;

    public Ocean() {
        // Create the window and attach the KeyListener
        cannonBalls = new ArrayList<>();
        enemies = new ArrayList<>();
        waveNum = 1;
        shipsLeft = 0;
        state = 0; // 0 = playing

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

    public void play() {
        // Main game loop: update positions, repaint, sleep
        while (true) {
            // Update all cannon balls
            ArrayList<CannonBall> toRemove = new ArrayList<>();
            for (CannonBall cb : cannonBalls) {
                cb.updatePosition();
                if (cb.isDone()) {
                    toRemove.add(cb);
                }
            }
            cannonBalls.removeAll(toRemove);

            // Repaint the window
            window.repaint();

            // Sleep to maintain frame rate
            try {
                Thread.sleep(FRAME_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void spawnCannonBall(int spawnX, int spawnY, int endX, int endY) {
        cannonBalls.add(new CannonBall(player.getX() + 50, player.getY() + 50, endX, endY));
    }

    public void spawnExplosion(int x, int y) {
        // Placeholder for explosion effect
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

    public static void main(String[] args) {
        Ocean game = new Ocean();
        game.play();
    }
}
