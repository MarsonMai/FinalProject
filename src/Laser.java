import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Laser {
    private int xCoord;
    private int yCoord;
    private int speed;
    private BufferedImage image;
    private boolean firing;

    public Laser(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
        this.speed = 10;
        this.firing = false;

        try {
            image = ImageIO.read(new File("src/laser.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setxCoord(int x) {
        xCoord = x;
    }
    public void setyCoord(int y) {
        yCoord = y;
    }
    public void move() {
        if (firing) {
            xCoord += speed;
        }
    }

    public void startFiring() {
        firing = true;
    }
    public boolean isFiring() {
        if (firing) {
            return true;
        }
        return false;
    }

    public void stopFiring() {
        firing = false;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public BufferedImage getImage() {
        return image;
    }
    public Rectangle laserRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle(xCoord, yCoord, imageWidth, imageHeight);
        return rect;
    }
}
