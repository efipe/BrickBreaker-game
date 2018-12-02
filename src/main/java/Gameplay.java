import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
Random random = new Random();
    private boolean gameStarted = false;
    private int score = 0;
    private int totalBricks = 21;
    private MapGenerator map;
    private Timer timer;
    private int delay = 4;

    private int playerPOSX = 310;

    private int ballposX = ThreadLocalRandom.current().nextInt(30,670);   //120;
    private int ballposY = 350;   //350;
    private int ballDirX = -1;
    private int ballDirY = -2;


    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        map = new MapGenerator(3, 7);

    }

    public void paint(Graphics graphics) {
        //background of the map
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1, 1, 692, 592);

        // drawing objects in map
        map.draw((Graphics2D) graphics);

        //borders of the map
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);

        // Score HUD
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("serif",Font.ROMAN_BASELINE, 25));
        graphics.drawString("Score: "+score,590,30);


        //players paddle color
        graphics.setColor(Color.GREEN);
        graphics.fillRect(playerPOSX, 550, 100, 8);

        // ball color
        graphics.setColor(Color.BLUE);
        graphics.fillOval(ballposX, ballposY, 20, 20);

        if (totalBricks <= 0) {
            gameStarted = false;
            ballDirX = 0;
            ballDirY = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif",Font.ITALIC, 30));
            graphics.drawString("You won, your score is "+score,190,300);

            graphics.setFont(new Font("serif",Font.ITALIC, 20));
            graphics.drawString("Press ENTER to restart",230,350);
        }

        // GAME OVER hud
        if (ballposY > 570){
            gameStarted = false;
            ballDirX = 0;
            ballDirY = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif",Font.ITALIC, 30));
            graphics.drawString("GAME OVER, your score is "+score,190,300);

            graphics.setFont(new Font("serif",Font.ITALIC, 20));
            graphics.drawString("Press ENTER to restart",230,350);


        }

        graphics.dispose();
    }


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerPOSX >= 600) {
                playerPOSX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerPOSX < 10) {
                playerPOSX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!gameStarted){
                gameStarted = true;
                ballposX =  ThreadLocalRandom.current().nextInt(30,670);
                ballposY = 350;//ThreadLocalRandom.current().nextInt(30,200);
                ballDirX = -1;
                ballDirY = -2;
                playerPOSX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){

        }

    }

    public void moveRight() {
        gameStarted = true;
        playerPOSX += 20;
    }

    public void moveLeft() {
        gameStarted = true;
        playerPOSX -= 20;

    }


    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (gameStarted) {
            // detecting intersections - here with the paddle and ball

            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(playerPOSX, 550, 100, 8)) {
                ballDirY = -ballDirY;
                // ball and the bricks to get broken
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brick = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ball = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = brick;

                        // logic of intersection
                        if (ball.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            // checking the corners of the map
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballDirX = -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }
                            break A;
                        }

                    }
                }
            }

            ballposX += ballDirX;
            ballposY += ballDirY;
            if (ballposX < 0) {
                ballDirX = -ballDirX;
            }
            if (ballposY < 0) {
                ballDirY = -ballDirY;
            }
            if (ballposX > 670) {
                ballDirX = -ballDirX;
            }

        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
