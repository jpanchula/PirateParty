// Pirate Party by Jacob Panchula and Sachin Sandhu

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Ocean implements KeyListener, ActionListener, MouseListener {
    /* Public Constant Variables */
    public static final int STATE_MENU = 0;
    public static final int STATE_PLAY = 1;
    public static final int STATE_END = 2;

    private static final int DELAY_MILLISECONDS = 16;
    private static final int ENEMY_SPAWN_INTERVAL = 180;

    private OceanView window;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<CannonBall> cannonBalls;

    private int waveNum;
    private int shipsLeft;
    private int state;

    private int tickCount = 0;
    private Random random = new Random();

    private boolean up, down, left, right;

    public Ocean() {
        cannonBalls = new ArrayList<>();
        enemies = new ArrayList<>();
        waveNum = 1;
        shipsLeft = 0;
        state = 0;

        window = new OceanView(this);
        window.addKeyListener(this);
        window.addMouseListener(this);

        player = new Player(window, OceanView.WINDOW_WIDTH / 2, OceanView.WINDOW_HEIGHT / 2);
        state = STATE_PLAY;
        waveNum = 0;
        shipsLeft = 0;

        Timer clock = new Timer(DELAY_MILLISECONDS, this);
        clock.start();
    }

    public void actionPerformed(ActionEvent e) {
        switch (state) {
            case STATE_PLAY: updatePlay(); break;
            case STATE_END:  updateEnd();  break;
        }
    }

    private void updatePlay() {
        // Only enemies get hit by player cannon balls
        ArrayList<Ship> enemyShips = new ArrayList<>(enemies);

        if (!cannonBalls.isEmpty()) {
            System.out.println("Updating " + cannonBalls.size() + " cannonballs");
        }

        /* --- Player --- */
        player.calculateVelocity(up, down, left, right);
        player.updatePosition();

        /* --- CannonBalls --- */
        for (CannonBall cb : cannonBalls) {
            cb.updatePosition();
            cb.applyDamage(enemyShips);
        }
        cannonBalls.removeIf(CannonBall::isDone);

        /* --- Enemies --- */
        enemies.removeIf(Enemy::isDead);

        double playerCenterX = player.getX() + Ship.WIDTH  / 2.0;
        double playerCenterY = player.getY() + Ship.HEIGHT / 2.0;
        for (Enemy en : enemies) {
            en.chasePlayer(playerCenterX, playerCenterY);
            en.updatePosition();
        }

        if (player.isDead()) state = STATE_END;

        tickCount++;
        if (tickCount % ENEMY_SPAWN_INTERVAL == 0) spawnEnemy();

        window.repaint();
    }

    private void updateEnd() {
        window.repaint();
    }

    private void spawnEnemy() {
        int x, y;
        switch (random.nextInt(4)) {
            case 0:  x = random.nextInt(OceanView.WINDOW_WIDTH); y = -Ship.HEIGHT;            break; // top
            case 1:  x = random.nextInt(OceanView.WINDOW_WIDTH); y = OceanView.WINDOW_HEIGHT; break; // bottom
            case 2:  x = -Ship.WIDTH;           y = random.nextInt(OceanView.WINDOW_HEIGHT);  break; // left
            default: x = OceanView.WINDOW_WIDTH; y = random.nextInt(OceanView.WINDOW_HEIGHT); break; // right
        }
        enemies.add(new Enemy(window, x, y));
    }

    private void spawnCannonBall(int endX, int endY) {
        System.out.println("Spawning cannonball to: " + endX + ", " + endY);
        double spawnX = player.getX() + Ship.WIDTH  / 2.0;
        double spawnY = player.getY() + Ship.HEIGHT / 2.0;
        System.out.println("From: " + spawnX + ", " + spawnY);
        System.out.println("CannonBall list size: " + cannonBalls.size());
        cannonBalls.add(new CannonBall(spawnX, spawnY, endX, endY, window));
        System.out.println("CannonBall list size after: " + cannonBalls.size());
    }

    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: up    = false; break;
            case KeyEvent.VK_S: down  = false; break;
            case KeyEvent.VK_A: left  = false; break;
            case KeyEvent.VK_D: right = false; break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: up    = true; break;
            case KeyEvent.VK_S: down  = true; break;
            case KeyEvent.VK_A: left  = true; break;
            case KeyEvent.VK_D: right = true; break;
        }
    }

    @Override public void mouseClicked(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point offset = window.getContentPane().getLocation();
        spawnCannonBall(e.getX() + offset.x, e.getY() + offset.y);
    }

    public int getState()                         { return state; }
    public ArrayList<CannonBall> getCannonBalls() { return cannonBalls; }
    public ArrayList<Enemy> getEnemies()          { return enemies; }
    public Player getPlayer()                     { return player; }
    public int getWaveNum()                       { return waveNum; }
    public int getShipsLeft()                     { return shipsLeft; }

    public static void main(String[] args) {
        new Ocean();
    }
}