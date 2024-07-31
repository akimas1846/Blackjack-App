import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ui {

    private JFrame frame;
    private JPanel startPanel;
    private JPanel gamePanel;
    private JPanel rulesPanel;
    private CardLayout rulesLayout;
    private JPanel rulesContainer;

    private JLabel cardLabel1;
    private JLabel cardLabel2;

    public Ui() {
        frame = new JFrame();
        frame.setTitle("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 100, 0));
        frame.setLayout(new CardLayout());

        createStartPanel();
        createGamePanel();
        createRulesPanel();

        frame.add(startPanel, "Start");
        frame.add(gamePanel, "Game");
        frame.add(rulesPanel, "Rules");

        frame.setUndecorated(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);

        frame.setVisible(true);
    }

    private void createStartPanel() {
        startPanel = new JPanel();
        startPanel.setBackground(new Color(0, 100, 0));
        startPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = createStyledLabel("Welcome to Blackjack");
        startPanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(0, 100, 0));

        JButton startButton = createStyledButton("Start Game");
        startButton.addActionListener(e -> showGamePanel());
        buttonPanel.add(startButton);

        JButton rulesButton = createStyledButton("View Rules");
        rulesButton.addActionListener(e -> showRulesPanel());
        buttonPanel.add(rulesButton);

        startPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createGamePanel() {
        gamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gamePanel.setBackground(new Color(0, 100, 0));

        cardLabel1 = new JLabel();
        cardLabel1.setPreferredSize(new Dimension(100, 225));
        gamePanel.add(cardLabel1);

        cardLabel2 = new JLabel();
        cardLabel2.setPreferredSize(new Dimension(100, 225));
        gamePanel.add(cardLabel2);
    }

    private void createRulesPanel() {
        rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.setBackground(new Color(0, 100, 0));

        rulesLayout = new CardLayout();
        rulesContainer = new JPanel(rulesLayout);
        rulesPanel.add(rulesContainer, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(0, 100, 0));

        JButton prevButton = createStyledButton("Previous");
        prevButton.addActionListener(e -> rulesLayout.previous(rulesContainer));
        buttonPanel.add(prevButton);

        JButton nextButton = createStyledButton("Next");
        nextButton.addActionListener(e -> rulesLayout.next(rulesContainer));
        buttonPanel.add(nextButton);

        JButton titleButton = createStyledButton("Title");
        titleButton.addActionListener(e -> showStartPanel());
        buttonPanel.add(titleButton);

        rulesPanel.add(buttonPanel, BorderLayout.SOUTH);

        addRuleImage("..\\image\\Rule1.png");
        addRuleImage("..\\image\\Rule2.png");
        addRuleImage("..\\image\\Rule3.png");
    }

    private void addRuleImage(String imagePath) {
        ImageIcon ruleIcon = createResizedImageIcon(imagePath, 600, 800);
        JLabel ruleLabel = new JLabel(ruleIcon);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 100, 0));
        panel.add(ruleLabel, BorderLayout.CENTER);
        rulesContainer.add(panel);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 48));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        label.setOpaque(true);
        label.setBackground(new Color(0, 100, 0));
        label.setPreferredSize(new Dimension(800, 200));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.BOLD, 24));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        return button;
    }

    private void showGamePanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Game");

        setCardImage(cardLabel1, "..\\image\\Back.png", 100, 225);
        setCardImage(cardLabel2, "..\\image\\Back.png", 100, 225);
    }

    private void showRulesPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Rules");
    }

    private void showStartPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Start");
    }

    private ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setCardImage(JLabel label, String imagePath, int width, int height) {
        try {
            BufferedImage cardImage = ImageIO.read(new File(imagePath));
            Image resizedImage = cardImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(resizedImage);
            label.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Ui();
    }
}
