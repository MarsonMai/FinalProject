import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomePanel extends JPanel implements ActionListener {
    private JTextField textField;
    private JButton submitButton;
    private JButton clearButton;
    private JFrame enclosingFrame;
    private Font titleFont;
    private Font ruleFont;
    public WelcomePanel(JFrame frame) {
        enclosingFrame = frame;
        titleFont = new Font("Arial", Font.BOLD, 24);
        ruleFont = new Font("Arial", Font.PLAIN, 16);
        textField = new JTextField(15);
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        submitButton.setFont(ruleFont);
        clearButton.setFont(ruleFont);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20)); // Add spacing
        topPanel.add(new JLabel("Enter your name:"));
        topPanel.add(textField);
        topPanel.add(submitButton);
        topPanel.add(clearButton);
        add(topPanel, BorderLayout.NORTH);
        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(titleFont);
        g.setColor(Color.RED);
        String title = "Welcome to the Game";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth) / 2, 100);
        String[] rules = {
                "- Kill 30 enemies as fast as possible",
                "- Use WASD keys to move",
                "- Press SPACE to shoot",
                "- Avoid getting hit by enemies!"
        };
        int startY = 130;
        g.setFont(ruleFont);
        g.setColor(Color.BLACK);
        for (String rule : rules) {
            int ruleWidth = g.getFontMetrics().stringWidth(rule);
            g.drawString(rule, (getWidth() - ruleWidth) / 2, startY);
            startY += 30;
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button == submitButton) {
                String playerName = textField.getText();
                MainFrame f = new MainFrame(playerName);
                enclosingFrame.setVisible(false);
            } else {
                textField.setText("");
            }
        }
    }
}
