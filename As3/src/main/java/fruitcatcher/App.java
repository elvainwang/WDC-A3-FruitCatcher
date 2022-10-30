package fruitcatcher;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

import java.util.*;

public class App extends PApplet {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 680;
    public static final int ICONSIZE = 20;
    public static final int STATUSBAR = 80;

    public static final int NUMROW = (HEIGHT - STATUSBAR) / ICONSIZE;
    public static final int NUMCOL = WIDTH / ICONSIZE;

    public static final int FPS = 60;

    // game status
    public boolean win;
    public boolean lose;
    public int score;
    public int goal;
    public int remainingSeconds;
    public int lifeTimer;

    // the elements on the window
    public Player player;
    public ArrayList<Enemy> enemies;
    public Set<Integer> fruits;
    public int fruitCount;
    public int fruitTimer;

    // their icons
    public PImage playerImage;
    public PImage enemyImage;
    public PImage furitImage;
    public PImage wallImage;

    public Random random = new Random();
    public PFont font;
    public String winMsg = "Congratulations! You win the game!";
    public String loseMsg = "Sorry, game over! Try hard next time!";
    public String hintMsgTime = "Left time:   ";
    public String hintMsgTime2 = "seconds";
    public String hintMsgGoal = "Goal:   ";
    public String hintMsgScore = "You catched:   ";
    public String hintMsgScore2 = "X";

    public App() {}

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    public void setup() {
        surface.setTitle("Fruit Catcher");
        frameRate(FPS);
        this.win = false;
        this.lose = false;
        this.score = 0;
        this.goal = 10;
        this.remainingSeconds = 60;
        this.lifeTimer = 0;

        int numGrids = NUMROW * NUMCOL;
        int playPosition = random.nextInt(numGrids);
        int playDirection = random.nextInt(4);
        int playSpeed = 2; // # of pixels every frame
        this.player = new Player(playPosition, playDirection, playSpeed); // initial direction: right

        int numEnemy = 5;
        Set<Integer> enemyPostions = new HashSet<>();
        this.enemies = new ArrayList<>();
        for (int i=0; i<numEnemy; i++) {
            int pos = 0;
            do {
                pos = random.nextInt(numGrids);
            } while (enemyPostions.contains(pos) || pos == playPosition);
            int direction = random.nextInt(4);
            int speed = 2;
            Enemy enemy = new Enemy(pos, direction, speed);
            this.enemies.add(enemy);
        }

        this.fruits = new HashSet<>();
        fruitCount = 2 * FPS; // every 2 seconds, new a fruit
        fruitTimer = 0;

        this.playerImage = loadImage(this.getClass().getResource("player.png").getPath());
        this.enemyImage = loadImage(this.getClass().getResource("enemy.png").getPath());
        this.wallImage = loadImage(this.getClass().getResource("wall.png").getPath());
        this.furitImage = loadImage(this.getClass().getResource("fruit.png").getPath());

        this.font = createFont("Arial", 16, true);
    }

    // Draw all elements in the game by current frame.
    public void draw() {
        background(0xffffff);
        // show top bar
        // 0. show the status bottom boundary
        for (int i = 0; i < NUMCOL; i++) {
            int x = i * ICONSIZE;
            int y = STATUSBAR - ICONSIZE;
            image(wallImage, x, y);
        }
        // 1. show time
        textFont(font, 20);
        fill(0);
        text(hintMsgTime + remainingSeconds, 20, 30);
        text(hintMsgTime2, 150, 30);

        // 2. show goal
        text(hintMsgGoal + goal, 450, 40);
        text(hintMsgScore2, 550, 40);
        image(furitImage, 580, 22);

        // 3. show score
        text(hintMsgScore + score, 800, 40);
        text(hintMsgScore2, 970, 40);
        image(furitImage, 1000, 22);

        if(lose) {
            textFont(font, 50);
            text(loseMsg, 300, 300);
        }
        else if(win) {
            textFont(font, 50);
            text(winMsg, 300, 300);
        }
        else {
            // update position
            player.tick(this);
            int pos = player.position;
            int row = pos / NUMCOL, col = pos % NUMCOL;
            int x = col * ICONSIZE, y = STATUSBAR + row * ICONSIZE;
            image(playerImage, x, y);

            // check if catching any fruit
            if (fruits.contains(pos)) {
                score ++;
                fruits.remove(pos);
                if (score >= goal) {
                    this.win = true;
                    return;
                }
            }

            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).tick(this);
                pos = enemies.get(i).position;
                row = pos / NUMCOL;
                col = pos % NUMCOL;
                x = col * ICONSIZE;
                y = STATUSBAR + row * ICONSIZE;
                image(enemyImage, x, y);
            }

            // create fruits periodically and show fruits
            fruitTimer ++;
            if (fruitTimer == fruitCount) {
                do {
                    pos = random.nextInt(NUMROW * NUMCOL);
                } while (fruits.contains(pos));
                fruits.add(pos);
                fruitTimer = 0;
            }
            for (int fruitPos: fruits) {
                row = fruitPos / NUMCOL;
                col = fruitPos % NUMCOL;
                x = col * ICONSIZE;
                y = STATUSBAR + row * ICONSIZE;
                image(furitImage, x, y);
            }

            // udpate remaining time
            lifeTimer ++;
            if (lifeTimer == FPS) {
                remainingSeconds --;
                lifeTimer = 0;
                if (remainingSeconds == 0) {
                    this.lose = true;
                }
            }
        }

    }

    public void keyPressed() {
        // Left: 37
        // Up: 38
        // Right: 39
        // Down: 40
        player.pressKey(this.keyCode, this);
    }

    public static void main(String[] args) {
        PApplet.main("fruitcatcher.App");
    }
}
