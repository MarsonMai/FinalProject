import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Enemy {
    private BufferedImage img;
    private String name;
    private double xCoord;
    private double yCoord;

    public Enemy(String img,String name) {
        this.name = name;
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
    public Rectangle playerRect() {
        int imageHeight = img.getHeight();
        int imageWidth = img.getWidth();
        return new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
    }
}
