// Pirate Party by Jacob Panchula and Sachin Sandhu

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.nio.file.Files;

public class Ocean implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    /* Public Constant Variables */
    public static final int STATE_INSTR = 0;
    public static final int STATE_PLAY = 1;
    public static final int STATE_END = 2;

    /* Private Constant Variables */
    // Clock delay
    private static final int DELAY_MILLISECONDS = 16;
    // Spawn one enemy every this many ticks (~3 seconds at 60fps)
    private static final int ENEMY_SPAWN_INTERVAL = 180;

    /* Instance Variables */
    private OceanView window;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Gold> treasure;
    private ArrayList<CannonBall> cannonBalls;

    private int waveNum;
    private int shipsLeft;
    private int state;
    private int highscore;

    private int tickCount = 0;
    private Random random = new Random();

    // Vertical and horizontal inputs
    private boolean up, down, left, right;

    Path filePath;

    /* Constructor */
    public Ocean() {
        // Initialize the ArrayLists
        cannonBalls = new ArrayList<>();
        treasure = new ArrayList<>();
        enemies = new ArrayList<>();

        waveNum = 1;
        shipsLeft = 0;

        // Create the frontend window and attach the components
        window = new OceanView(this);
        window.addKeyListener(this);
        window.addMouseListener(this);
        window.addMouseMotionListener(this);

        player = new Player(window, OceanView.WINDOW_WIDTH / 2, OceanView.WINDOW_HEIGHT / 2);
        state = STATE_INSTR;
        waveNum = 0;
        shipsLeft = 0;

        filePath = Paths.get("Resources/highscore.txt");

        // Create and start the clock
        Timer clock = new Timer(DELAY_MILLISECONDS, this);
        clock.start();
    }

    // Called by the clock every 16 ms
    public void actionPerformed(ActionEvent e) {
        switch(state) {
            case STATE_PLAY:
                updatePlay();
                break;
            case STATE_INSTR:
            case STATE_END:
                window.repaint();
                break;
        }
    }

    /** Helper methods */

    // Play state update method
    private void updatePlay() {
        // Combined list of all ships
        ArrayList<Ship> allShips = new ArrayList<>();
        allShips.add(player);
        allShips.addAll(enemies);

        /* --- Player --- */
        player.calculateVelocity(up, down, left, right);
        player.tickCanShoot();

        /* --- CannonBalls --- */
        for (CannonBall cb : cannonBalls) {
            cb.updatePosition();
            cb.applyDamage(allShips);
        }
        // Check if the player is dead
        if (player.isDead()) {
            writeHighscore();
            state = STATE_END;
        }

        // Update cannonBalls ArrayList
        cannonBalls.removeIf(CannonBall::isDone);

        /* --- Enemies --- */
        // Find the player center
        double playerCenterX = player.getX() + Ship.HEIGHT / 2.0;
        double playerCenterY = player.getY() + Ship.WIDTH / 2.0;
        // Find the enemy
        for (Enemy en : enemies) {
            if (en.isDead())
                spawnGold(en);
            else {
                en.chasePlayer(playerCenterX, playerCenterY);
                en.tickShootTimer();
                if (en.canShoot()) {
                    double spawnX = en.getX() + Ship.WIDTH / 2.0;
                    double spawnY = en.getY() + Ship.HEIGHT / 2.0;
                    cannonBalls.add(new CannonBall(spawnX, spawnY, (int) playerCenterX, (int) playerCenterY, window));
                    en.resetShootTimer();
                }
            }
        }
        // Update ArrayList outside the loop body
        enemies.removeIf(Enemy::isDead);

        // Spawn a new enemy on an interval
        tickCount++;
        if (tickCount >= ENEMY_SPAWN_INTERVAL) {
            spawnEnemy();
            tickCount = 0;
        }

        /* --- Gold --- */
        for (Gold gold : treasure) {
            // Check collision with the player
            gold.checkCollision(player);
        }
        // Update the ArrayList outside the loop body
        treasure.removeIf(Gold::isCollected);

        /* --- Ships --- */
        // Update the position of all the ships
        for (Ship ship : allShips) {
            ship.updatePosition();
        }

        // Repaint the window after everything has been updated
        window.repaint();
    }

    private void writeHighscore() {
        try {
            // Reading from a file
            String content = Files.readString(filePath);
            highscore = Integer.parseInt(content);
            if (player.getScore() > highscore) {
                // Write the new highscore to the file
                highscore = player.getScore();
                Files.writeString(filePath, Integer.toString(highscore));
            }
        }
        catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private void reset() {
        enemies.clear();
        treasure.clear();
        cannonBalls.clear();
        player.reset();
        player.setPoints(0);
        up = false;
        down = false;
        left = false;
        right = false;
        state = STATE_INSTR;
    }

    /** Spawn methods */

    // Spawns an enemy at a random position along one of the four edges
    private void spawnEnemy() {
        int x, y;
        int side = random.nextInt(4); // 0=top, 1=bottom, 2=left, 3=right
        switch (side) {
            case 0: // top edge
                x = random.nextInt(OceanView.WINDOW_WIDTH);
                y = -100;
                break;
            case 1: // bottom edge
                x = random.nextInt(OceanView.WINDOW_WIDTH);
                y = OceanView.WINDOW_HEIGHT;
                break;
            case 2: // left edge
                x = -100;
                y = random.nextInt(OceanView.WINDOW_HEIGHT);
                break;
            default: // right edge
                x = OceanView.WINDOW_WIDTH;
                y = random.nextInt(OceanView.WINDOW_HEIGHT);
                break;
        }
        enemies.add(new Enemy(window, x, y, (int)(Math.random() * 41) + 10));
    }

    private void spawnGold(Enemy enemy) {
        // Create a new piece of gold with the value of the enemy's health
        int enemyCenterX = (int)enemy.getX() + Ship.WIDTH / 2;
        int enemyCenterY = (int)enemy.getY() + Ship.HEIGHT / 2;
        treasure.add(new Gold(enemyCenterX, enemyCenterY, enemy.getMaxHealth(), window));
    }

    // Spawns a cannonball from the center of the player ship to the target
    private void spawnCannonBall(int endX, int endY) {
        double spawnX = player.getX() + 50;
        double spawnY = player.getY() + 50;
        cannonBalls.add(new CannonBall(spawnX, spawnY, endX, endY, window));
    }

    /** KeyListener */

    @Override
    public void keyTyped(KeyEvent e) {
        // Intentionally left blank
    }

    // Ran by KeyListener when a key is released
    @Override
    public void keyReleased(KeyEvent e) {
        if (state == STATE_PLAY) {
            switch (e.getKeyCode()) {
                // Movement
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
            }
        }
    }

    // Ran by KeyListener when a key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if (state == STATE_PLAY) {
            switch (e.getKeyCode()) {
                // Movement
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    up = true;
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    down = true;
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    left = true;
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    right = true;
                    break;
            }
        }
        else if (state == STATE_END) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    reset();
                    break;
            }
        }
    }

    /** MouseListener */

    // Called by MouseListener when the mouse is clicked
    @Override
    public void mouseClicked(MouseEvent e) {
        // Intentionally left blank
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (state == STATE_INSTR) {
            // Start the game
            state = STATE_PLAY;
        }
        else if (state == STATE_PLAY) {
            if (player.canShoot()) {
                // Spawn a cannonball
                spawnCannonBall(e.getX(), e.getY());
                player.setCanShoot(false);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Intentionally left blank
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Intentionally left blank
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Intentionally left blank
    }

    /** MouseMotionListener */

    @Override
    public void mouseDragged(MouseEvent e) {
        // Intentionally left blank
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.calculateRotation(e.getX(), e.getY());
    }

    /** Getters */

    public int getState() {
        return state;
    }

    public ArrayList<CannonBall> getCannonBalls() {
        return cannonBalls;
    }

    public ArrayList<Gold> getTreasure() {
        return treasure;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public int getHighscore() {
        return highscore;
    }

    public int getWaveNum() {
        return waveNum;
    }

    public int getShipsLeft() {
        return shipsLeft;
    }

    public static void main(String[] args) {
        Ocean game = new Ocean();

    }
}