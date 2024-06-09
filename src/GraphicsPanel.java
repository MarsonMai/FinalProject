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
        enemy = new Enemy("src/Enemy.png",1);
        laser = new Laser(player.getxCoord() + 150, player.getyCoord() + 78);
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
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), 200, 200, null);
        for (Enemy enemy : enemies) {
            enemy.move();
            g.drawImage(enemy.getPlayerImage(), enemy.getxCoord(), enemy.getyCoord(), 230, 230, null);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString(player.getName() + "'s Score: " + player.getScore(), 20, 40);
        g.drawString("Time: " + time, 20, 70);

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
                laser = new Laser(player.getxCoord() + 150, player.getyCoord() + 78);
                x++;
            }
            laser.startFiring();
        }

        if (laser.isFiring()) {
            laser.move();
            g.setColor(Color.RED);
            g.drawImage(laser.getImage(), laser.getxCoord(), laser.getyCoord(), 50, 50, null);
            Rectangle laserRect = new Rectangle(laser.getxCoord(), laser.getyCoord(), 50, 50);

            for (Enemy enemy : enemies) {
                Rectangle enemyRect = new Rectangle(enemy.getxCoord(), enemy.getyCoord(), 230, 230);

                if (laserRect.intersects(enemyRect)) {
                    player.collectCoin();
                    enemy.resetPosition();
                    laser.stopFiring();
                    laser.setxCoord(enemy.getxCoord());
                    laser.setyCoord(enemy.getyCoord());
                    break;
                }
            }

            if (laser.getxCoord() >= 1900 && pressedKeys[KeyEvent.VK_SPACE]) {
                laser.stopFiring();
                x = 1;
            }
        }
    }
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
            if (time % 2 == 0) {
                int randomY = (int) (Math.random() * (getHeight() - 230));
                Enemy newEnemy = new Enemy("src/Enemy.png",1);
                newEnemy.setyCoord(randomY);
                addEnemy(newEnemy);
            }
            repaint();
        }
    }
}