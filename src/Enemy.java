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
    private int difficulty;

    public Enemy(String img) {
        difficulty = 1;
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
    public void move() {
        if (difficulty == 1) {
            xCoord--;
        } else if (difficulty == 2) {
            xCoord -=2;
        } else {
            xCoord -= 3;
        }

    }
    public BufferedImage getPlayerImage() {
        return img;
    }
    public void resetPosition() {
        xCoord = 1980;
        yCoord = (int) (Math.random() * 1080);
    }
}
