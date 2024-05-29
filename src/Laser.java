import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Laser {
    private BufferedImage image;
    private int xCoord;
    private int yCoord;
    private int velocity;
    private boolean firing; // Indicates if the laser is currently firing

    public Laser(int x, int y) {
        // Load laser image
        try {
            image = ImageIO.read(new File("src/laser.png")); // Assuming you have a laser image
        } catch (IOException e) {
            System.out.println("Error loading laser image: " + e.getMessage());
        }

        xCoord = x;
        yCoord = y;
        velocity = 5; // Set the initial velocity of the laser
        firing = false; // Laser starts not firing
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void startFiring() {
        firing = true;
    }

    public void stopFiring() {
        firing = false;
    }

    public boolean isFiring() {
        return firing;
    }

    public void move() {
        if (firing) {
            // Update the laser's position
            xCoord += velocity; // Move the laser upward (decrease y-coordinate)
        }
    }
}
