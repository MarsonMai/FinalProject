import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpaceShip {
    private final double MOVE_AMT = 4.5;
    private BufferedImage img;
    private double xCoord;
    private double yCoord;
    private int score;
    private String name;

    public SpaceShip(String img, String name) {
        this.name = name;
        xCoord = 50;
        yCoord = 435;
        score = 0;
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
        return (int) yCoord;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    public void moveRight() {
        if (xCoord + MOVE_AMT <= 1660) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public void moveUp() {
        if (yCoord - MOVE_AMT >= 0) {
            yCoord -= MOVE_AMT;
        }
    }

    public void moveDown() {
        if (yCoord + MOVE_AMT <= 900) {
            yCoord += MOVE_AMT;
        }
    }
    public void collectCoin() {
        score++;
    }
    public BufferedImage getPlayerImage() {
        return img;
    }
}
