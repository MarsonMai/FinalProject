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
    private boolean[] pressedKeys;
    private ArrayList<Coin> coins;
    private Timer timer;
    private int time;
    private Laser laser;

    public GraphicsPanel(String name) {
        try {
            background = ImageIO.read(new File("src/background.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        player = new SpaceShip("src/spaceship.png", "src/spaceship.png", name);
        laser = new Laser(player.getxCoord(), player.getyCoord());
        coins = new ArrayList<>();
        pressedKeys = new boolean[128];
        time = 0;
        timer = new Timer(1000, this); // this Timer will call the actionPerformed interface method every 1000ms = 1 second
        timer.start();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);
        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            g.drawImage(coin.getImage(), coin.getxCoord(), coin.getyCoord(), null); // draw Coin
            if (player.playerRect().intersects(coin.coinRect())) { // check for collision
                player.collectCoin();
                coins.remove(i);
                i--;
            }
        }

        // draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.drawString(player.getName() + "'s Score: " + player.getScore(), 20, 40);
        g.drawString("Time: " + time, 20, 70);

        // Handle player movement based on pressed keys
        if (pressedKeys[KeyEvent.VK_A]) {
            player.faceLeft();
            player.moveLeft();
        }

        if (pressedKeys[KeyEvent.VK_D]) {
            player.faceRight();
            player.moveRight();
        }

        if (pressedKeys[KeyEvent.VK_W]) {
            player.moveUp();
        }

        if (pressedKeys[KeyEvent.VK_S]) {
            player.moveDown();
        }
        if (pressedKeys[KeyEvent.VK_SPACE]) {
            laser.startFiring();
        }
        if (laser.isFiring()) {
            laser.move();
            g.setColor(Color.RED);
            g.drawImage(laser.getImage(), laser.getxCoord(), laser.getyCoord(),150,150,null);

        }
    }



    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }  // unimplemented

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
            Point mouseClickLocation = e.getPoint();
            Coin coin = new Coin(mouseClickLocation.x, mouseClickLocation.y);
            coins.add(coin);
        } else {
            Point mouseClickLocation = e.getPoint();
            if (player.playerRect().contains(mouseClickLocation)) {
                player.turn();
            }
        }
    }

    public void mouseEntered(MouseEvent e) { } // unimplemented

    public void mouseExited(MouseEvent e) { } // unimplemented

    // ACTIONLISTENER INTERFACE METHODS: used for buttons AND timers!
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            time++;
        }
    }
}
