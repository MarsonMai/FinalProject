import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Enemy {
    private BufferedImage img;
    private double xCoord;
    private double yCoord;
    private int difficulty;

    public Enemy(String img) {
        difficulty = 1;
        xCoord = 0;
        yCoord = 0;
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
        yCoord = (int) (Math.random() * 1080);
        return (int) yCoord;
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
    public Rectangle EnemyRect() {
        int imageHeight = img.getHeight();
        int imageWidth = img.getWidth();
        return new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
    }
}
