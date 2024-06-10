import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private BufferedImage img;
    private double xCoord;
    private double yCoord;
    private int enemyType;

    public Enemy(String imgPath, int enemyType,int width,int height) {
        this.enemyType = enemyType;
        try {
            this.img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        xCoord = 1980;
        yCoord = Math.random() * 1080;
    }
    public int getxCoord() {
        return (int) xCoord;
    }
    public int getyCoord() {
        return (int) yCoord;
    }
    public void setyCoord(int y) {
        yCoord = y;
    }
    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }
    public int getEnemyType() {
        return enemyType;
    }
    public void move() {
        if (enemyType == 1) {
            xCoord -= 2;
        } else {
            int rand = (int) (Math.random() * 20) + 1;
            if (rand == 1) {
                yCoord += 20;
            } else if (rand == 2) {
                yCoord -= 20;
            }
            xCoord --;
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
