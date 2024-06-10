import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private BufferedImage img;
    private double xCoord;
    private double yCoord;
    private final double finalYcord;

    public Enemy(String img) {
        xCoord = 1980;
        yCoord = (int) (Math.random() * 1080);
        finalYcord = yCoord;
        try {
            this.img = ImageIO.read(new File(img));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) finalYcord;
    }

    public void setxCoord(int x) {
        xCoord = x;
    }

    public void setyCoord(int y) {
        yCoord = y;
    }

    public void move() {
         xCoord -= 2;
    }

    public BufferedImage getPlayerImage() {
        return img;
    }

    public void resetPosition() {
        xCoord = 1980;
        yCoord = (int) (Math.random() * 1080);
    }

}
