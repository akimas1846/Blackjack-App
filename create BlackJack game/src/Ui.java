import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ui {

    private JFrame frame;
    private JPanel startPanel;
    private JPanel gamePanel;
    private JPanel rulesPanel;
    private JPanel gameOverPanel;
    private CardLayout rulesLayout;
    private JPanel rulesContainer;

    private JLabel cardLabel1;
    private JLabel cardLabel2;
    private JLabel creditLabel;
    private JLabel betLabel;
    private JLabel roundLabel;
    private int credit = 500;
    private int bet = 0;
    private int roundCount = 5;

    public Ui() {
        frame = new JFrame();
        frame.setTitle("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 100, 0));
        frame.setLayout(new CardLayout());

        createStartPanel();
        createGamePanel();
        createRulesPanel();
        createGameOverPanel();

        frame.add(startPanel, "Start");
        frame.add(gamePanel, "Game");
        frame.add(rulesPanel, "Rules");
        frame.add(gameOverPanel, "GameOver");
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
        gamePanel = new JPanel();
        gamePanel.setBackground(new Color(0, 100, 0));
        gamePanel.setLayout(null);

        creditLabel = createStyledLabel("Credit: $" + credit);
        creditLabel.setFont(new Font("Serif", Font.BOLD, 16));
        creditLabel.setPreferredSize(new Dimension(200, 30));
        creditLabel.setBounds(20, 20, 300, 50);
        gamePanel.add(creditLabel);

        betLabel = createStyledLabel("Bet: $" + bet);
        betLabel.setFont(new Font("Serif", Font.BOLD, 16));
        betLabel.setPreferredSize(new Dimension(200, 30));
        betLabel.setBounds(20, 80, 300, 50);
        gamePanel.add(betLabel);

        roundLabel = createStyledLabel("Rounds Left: " + roundCount);
        roundLabel.setFont(new Font("Serif", Font.BOLD, 16));
        roundLabel.setPreferredSize(new Dimension(200, 30));
        roundLabel.setBounds(20, 140, 300, 50);
        gamePanel.add(roundLabel);

        cardLabel1 = new JLabel();
        cardLabel1.setPreferredSize(new Dimension(100, 225));
        cardLabel1.setBounds(650, 100, 100, 225);
        gamePanel.add(cardLabel1);

        cardLabel2 = new JLabel();
        cardLabel2.setPreferredSize(new Dimension(100, 225));
        cardLabel2.setBounds(650, 600, 100, 225);
        gamePanel.add(cardLabel2);

        JButton titleButton = createStyledButton("Title");
        titleButton.setBounds(1400, 950, 100, 50);
        titleButton.addActionListener(e -> showStartPanel());
        gamePanel.add(titleButton);

        addBetImages();
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

    private void createGameOverPanel() {
        gameOverPanel = new JPanel(new BorderLayout());
        gameOverPanel.setBackground(new Color(0, 100, 0));

        JLabel gameOverLabel = createStyledLabel("Game Over");
        gameOverLabel.setFont(new Font("Serif", Font.BOLD, 48));
        gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(0, 100, 0));

        JButton restartButton = createStyledButton("Restart");
        restartButton.addActionListener(e -> restartGame());
        buttonPanel.add(restartButton);

        JButton titleButton = createStyledButton("Title");
        titleButton.addActionListener(e -> {
            restartGame();
            showStartPanel();
        });
        buttonPanel.add(titleButton);

        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);
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
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
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

    private void showGameOverPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "GameOver");
    }

    private void restartGame() {
        credit = 500;
        bet = 0;
        roundCount = 5;
        updateCreditDisplay();
        updateBetDisplay();
        updateRoundDisplay();
        showGamePanel();
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

    private void addBetImages() {
        String[] betImagePaths = { "..\\image\\Bet1.png", "..\\image\\Bet2.png", "..\\image\\Bet3.png",
                "..\\image\\Bet4.png" };
        int width = 100;
        int height = 225;

        JPanel betPanel = new JPanel();
        betPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        betPanel.setBackground(new Color(0, 100, 0));

        for (int i = 0; i < betImagePaths.length; i++) {
            String path = betImagePaths[i];
            int betAmount = getBetAmount(i);
            JLabel betImageLabel = new JLabel();
            setCardImage(betImageLabel, path, width, height);
            betImageLabel.addMouseListener(new BetImageMouseListener(betAmount));
            betPanel.add(betImageLabel);
        }

        betPanel.setBounds(400, 800, 800, 300);
        gamePanel.add(betPanel);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private int getBetAmount(int index) {
        switch (index) {
            case 0:
                return 10;
            case 1:
                return 20;
            case 2:
                return 50;
            case 3:
                return 100;
            default:
                return 0;
        }
    }

    private void updateCreditDisplay() {
        creditLabel.setText("Credit: $" + credit);
    }

    private void updateBetDisplay() {
        betLabel.setText("Bet: $" + bet);
    }

    private void updateRoundDisplay() {
        roundLabel.setText("Rounds Left: " + roundCount);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void addCredit(int amount) {
        credit += amount;
        updateCreditDisplay();
    }

    public void deductCredit(int amount) {
        credit -= amount;
        updateCreditDisplay();
        checkGameOver();
    }

    public void addBet(int amount) {
        bet += amount;
        updateBetDisplay();
    }

    public void decrementRound() {
        // roundCount--;
        updateRoundDisplay();
        checkGameOver();
    }

    private void checkGameOver() {
        if (credit <= 0 || roundCount <= 0) {
            showGameOverPanel();
        }
    }

    private class BetImageMouseListener extends MouseAdapter {
        private int betAmount;

        public BetImageMouseListener(int betAmount) {
            this.betAmount = betAmount;
        }

        public void mouseClicked(MouseEvent e) {
            if (credit >= betAmount) {
                deductCredit(betAmount);
                addBet(betAmount);
                decrementRound();
            }
        }
    }
}
