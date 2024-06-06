import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private BufferedImage background;
    private SpaceShip player;
    private Enemy enemy;
    private ArrayList<Enemy> enemies;
    private boolean[] pressedKeys;
    private Timer timer;
    private int time;
    private Laser laser;
    private int x;
    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new SpaceShip("src/spaceship.png", name);
        enemy = new Enemy("src/Enemy.png");
        laser = new Laser(player.getxCoord(), player.getyCoord());
        pressedKeys = new boolean[128];
        enemies = new ArrayList<>();
        time = 0;
        x = 1;
        timer = new Timer(1000, this);
        timer.start();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy); // Add an enemy to the collection
        repaint(); // Redraw the panel to display the new enemy
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
        for (Enemy enemy : enemies) {
            g.drawImage(enemy.getPlayerImage(), enemy.getxCoord(), enemy.getyCoord(), 230, 230, null);
        }
        enemy.move();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString(player.getName() + "'s Score: " + player.getScore(), 20, 40);
        g.drawString("Time: " + time, 20, 70);
        g.drawString("Health: " + player.getHP(),20,100);

        if (pressedKeys[KeyEvent.VK_A]) {
            player.moveLeft();
        }

        if (pressedKeys[KeyEvent.VK_D]) {
            player.moveRight();
        }

        if (pressedKeys[KeyEvent.VK_W]) {
            player.moveUp();
        }

        if (pressedKeys[KeyEvent.VK_S]) {
            player.moveDown();
        }

        if (pressedKeys[KeyEvent.VK_SPACE]) {
            if (x == 1) {
                laser = new Laser(player.getxCoord(), player.getyCoord());
                x++;
            }
            laser.startFiring();
        }

        if (laser.isFiring()) {
            laser.move();
            g.setColor(Color.RED);
            g.drawImage(laser.getImage(), laser.getxCoord(), laser.getyCoord(), 150, 150, null);
            Rectangle laserRect = new Rectangle(laser.getxCoord(), laser.getyCoord(), 150, 150);
            Rectangle enemyRect = new Rectangle(enemy.getxCoord(), enemy.getyCoord(), 230, 230);
            Rectangle playerRect = new Rectangle(player.getxCoord(),player.getyCoord());

            if (laserRect.intersects(enemyRect)) {
                player.collectCoin();
                enemy.resetPosition();
                laser.stopFiring();
                laser.setxCoord(player.getxCoord());
                laser.setyCoord(player.getyCoord());

            }
            if (laser.getxCoord() >= 1900 && pressedKeys[KeyEvent.VK_SPACE]) {
                laser.stopFiring();
                laser = new Laser(player.getxCoord(), player.getyCoord());
            }
            if (playerRect.intersects(enemyRect) || enemy.getxCoord() == 0) {
                player.setHP();
            }
        }
    }




    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Point mouseClickLocation = e.getPoint();
        }
    }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time++;
            if (time % 3 == 0) {
                Enemy newEnemy = new Enemy("src/Enemy.png");
                addEnemy(newEnemy);
            }
            repaint(); // Ensure the panel is repainted after each timer tick
        }
    }
}