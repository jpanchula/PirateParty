import java.util.ArrayList;

public class Ocean {
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

    // TODO
    public Ocean() {
        window = new OceanView(this);
        player = new Player(window, 0, 0);
        state = 1;
        waveNum = 0;
    }

    // TODO
    public void play() {
        while (player.isAlive()) {

        }
    }

    // TODO
    public void spawnCannonBall(int spawnX, int spawnY, int endX, int endY) {

    }

    // TODO
    public void spawnExplosion(int x, int y) {

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
