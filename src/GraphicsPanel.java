import javax.imageio.ImageIO;
import javax.sound.sampled.*;
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
    private ArrayList<Enemy> enemies;
    private boolean[] pressedKeys;
    private Timer timer;
    private int time;
    private Laser laser;
    private int x;
    private Clip songClip;
    private boolean gameOver;
    public GraphicsPanel(String name) {
        gameOver = false;
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new SpaceShip("src/spaceship.png", name);
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
        playMusic();
    }
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        repaint();
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
        if (player.getScore() >= 30){
            gameOver = true;
            g.setFont(new Font("Courier New",Font.ITALIC, 50));
            g.drawString("YOU WON IN " + time + "s!",1600 / 2,1080 / 2);
        }
        if (!gameOver) {
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
                if (!laser.isFiring()) {
                    playCoinMusic();
                }
                if (x == 1) {
                    laser = new Laser(player.getxCoord() + 150, player.getyCoord() + 78);
                    x++;
                }
                laser.startFiring();
            }
            if (laser.isFiring()) {
                laser.move();
                g.setColor(Color.RED);
                g.drawImage(laser.getImage(), laser.getxCoord(), laser.getyCoord(), null);
                Rectangle laserRect = new Rectangle(laser.getxCoord(), laser.getyCoord(), 50, 50);
                for (Enemy enemy : enemies) {
                    Rectangle enemyRect;
                    if (enemy.getEnemyType() == 1) {
                        enemyRect = new Rectangle(enemy.getxCoord(), enemy.getyCoord(), 230, 230);
                    } else {
                        enemyRect = new Rectangle(enemy.getxCoord(), enemy.getyCoord(),enemy.getWidth(),enemy.getHeight());
                    }
                    Rectangle playerRect = new Rectangle(player.getxCoord(), player.getyCoord(), 200, 200);
                    if (laserRect.intersects(enemyRect)) {
                        player.collectCoin();
                        enemy.resetPosition();
                        laser.stopFiring();
                        laser.setxCoord(enemy.getxCoord());
                        laser.setyCoord(enemy.getyCoord());
                        break;
                    }
                    if (playerRect.intersects(enemyRect)) {
                        System.out.println("a");
                    }
                }
                if (laser.getxCoord() >= 1900 && pressedKeys[KeyEvent.VK_SPACE]) {
                    laser.stopFiring();
                    x = 1;
                }
            }
            for (Enemy enemy : enemies) {
                Rectangle enemyRect;
                if (enemy.getEnemyType() == 1) {
                    enemyRect = new Rectangle(enemy.getxCoord(), enemy.getyCoord(), 230, 230);

                } else {
                    enemyRect = new Rectangle(enemy.getxCoord(), enemy.getyCoord(),enemy.getWidth(),enemy.getHeight());
                }
                Rectangle playerRect = new Rectangle(player.getxCoord(), player.getyCoord(), 200, 200);
                if (playerRect.intersects(enemyRect)) {
                    try {
                        background = ImageIO.read(new File("src/gameover.png"));
                        gameOver = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            songClip.stop();
        }
    }
    private void playMusic() {
        try {
            File audioFile = new File("src/backgroundMusic.wav");
            if (!audioFile.exists()) {
                System.out.println("Audio file not found.");
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            songClip = AudioSystem.getClip();
            songClip.open(audioInputStream);
            songClip.loop(Clip.LOOP_CONTINUOUSLY);
            songClip.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    private void playCoinMusic() {
        try {
            File audioFile = new File("src/laser1.wav");
            if (!audioFile.exists()) {
                System.out.println("Audio file not found.");
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            songClip = AudioSystem.getClip();
            songClip.open(audioInputStream);
            songClip.start();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
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
    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            if (e.getSource() instanceof Timer) {
                time++;
                if (time % 2 == 0) {
                    Enemy newEnemy;
                    int rand = (int) (Math.random() * 2) + 1;
                    int randomY = (int) (Math.random() * (getHeight() - 230));
                    if (randomY > 100 && randomY < 1000) {
                        if (rand == 1) {
                            newEnemy = new Enemy("src/Enemy.png", 1, 180, 180);
                        } else {
                            newEnemy = new Enemy("src/enemy2.png", 2, 200, 200);
                        }
                        newEnemy.setyCoord(randomY);
                        addEnemy(newEnemy);
                    }
                }
                repaint();
            }
        }
    }
}