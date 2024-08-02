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

    private JPanel buttonPanel; // 新しく追加
    private JPanel betPanel; // ベットパネルの参照を保持

    public Ui() {
        // 初期画面
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

    // 最初の画面
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

    // GameStartの画面のラベル、位置
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

        // ボタンパネルを非表示のまま作成
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setBackground(new Color(0, 100, 0));
        buttonPanel.setBounds(100, 850, 400, 80);
        buttonPanel.setVisible(false); // 初期状態では非表示

        JButton raiseButton = createStyledButton("レイズ");
        raiseButton.addActionListener(e -> raiseAction());
        buttonPanel.add(raiseButton);

        JButton stayButton = createStyledButton("ステイ");
        stayButton.addActionListener(e -> stayAction());
        buttonPanel.add(stayButton);

        JButton doubleButton = createStyledButton("ダブル");
        doubleButton.addActionListener(e -> doubleAction());
        buttonPanel.add(doubleButton);

        gamePanel.add(buttonPanel);

        addBetImages();
    }

    // レイズの動作
    private void raiseAction() {
        // ここにレイズの処理を追加
        System.out.println("レイズが選択されました。");
    }

    // ステイの動作
    private void stayAction() {
        // ここにステイの処理を追加
        System.out.println("ステイが選択されました。");
    }

    // ダブルの動作
    private void doubleAction() {
        // ここにダブルの処理を追加
        System.out.println("ダブルが選択されました。");
    }

    // ルールの場面
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

        addRuleImage("create BlackJack game\\image\\Rule1.png");
        addRuleImage("create BlackJack game\\image\\Rule2.png");
        addRuleImage("create BlackJack game\\image\\Rule3.png");
    }

    // Gameの終了画面
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

    // ルールの画面、背景とか
    private void addRuleImage(String imagePath) {
        ImageIcon ruleIcon = createResizedImageIcon(imagePath, 600, 800);
        JLabel ruleLabel = new JLabel(ruleIcon);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 100, 0));
        panel.add(ruleLabel, BorderLayout.CENTER);
        rulesContainer.add(panel);
    }

    // ラベルの追加
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 48));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        label.setOpaque(true);
        label.setBackground(new Color(0, 100, 0));
        return label;
    }

    // ボタンの追加
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.BOLD, 24));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        return button;
    }

    // カードの後背景をGameStartで表示させている
    private void showGamePanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Game");

        setCardImage(cardLabel1, "create BlackJack game\\image\\Back.png", 100, 225);
        setCardImage(cardLabel2, "create BlackJack game\\image\\Back.png", 100, 225);
    }

    private void showRulesPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Rules");
    }

    private void showStartPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Start");
        resetBetImages(); // ベット画像をリセット
    }

    private void showGameOverPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "GameOver");
    }

    // restartGameにした時初期化できるようにしている
    private void restartGame() {
        credit = 500;
        bet = 0;
        roundCount = 5;
        updateCreditDisplay();
        updateBetDisplay();
        updateRoundDisplay();
        buttonPanel.setVisible(false); // 初期化時にボタンパネルを非表示にする
        resetBetImages(); // ベット画像をリセット
        showGamePanel();
    }

    // ルールの画像設定
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

    // カードの画像設定
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

    // Betの画像表示、位置設定
    private void addBetImages() {
        String[] betImagePaths = { "create BlackJack game\\image\\Bet1.png", "create BlackJack game\\image\\Bet2.png",
                "create BlackJack game\\image\\Bet3.png", "create BlackJack game\\image\\Bet4.png" };
        int width = 100;
        int height = 225;

        betPanel = new JPanel();
        betPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        betPanel.setBackground(new Color(0, 100, 0));

        for (int i = 0; i < betImagePaths.length; i++) {
            String path = betImagePaths[i];
            int betAmount = getBetAmount(i);
            JLabel betImageLabel = new JLabel();
            setCardImage(betImageLabel, path, width, height);
            betImageLabel.addMouseListener(new BetImageMouseListener(betAmount, betImageLabel));
            betPanel.add(betImageLabel);
        }

        betPanel.setBounds(400, 800, 800, 300);
        gamePanel.add(betPanel);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    // Betをタップしたら値を返す。
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

    // クレジットを減らす
    public void deductCredit(int amount) {
        credit -= amount;
        updateCreditDisplay();
        checkGameOver();
    }

    // amountにベットを入れて更新
    public void addBet(int amount) {
        bet += amount;
        updateBetDisplay();
    }

    // ラウンドを減らす
    public void decrementRound() {
        roundCount--;
        updateRoundDisplay();
        checkGameOver();
    }

    // Gameを終了する条件
    private void checkGameOver() {
        if (credit <= 0 || roundCount <= 0) {
            showGameOverPanel();
        }
    }

    // ベット数をクリックしたらの動作
    private class BetImageMouseListener extends MouseAdapter {
        private int betAmount;
        private JLabel betImageLabel;

        public BetImageMouseListener(int betAmount, JLabel betImageLabel) {
            this.betAmount = betAmount;
            this.betImageLabel = betImageLabel;
        }

        public void mouseClicked(MouseEvent e) {
            if (credit >= betAmount) {
                deductCredit(betAmount);
                addBet(betAmount);
                decrementRound();
                buttonPanel.setVisible(true); // ベットが選択されたらボタンパネルを表示

                // 選択したベット画像を右側に移動し、他のベット画像を非表示にする
                moveBetImageToRight(betImageLabel);
            }
        }
    }

    // ベット画像を右側に移動し、他のベット画像を非表示にする
    private void moveBetImageToRight(JLabel selectedBetLabel) {
        for (Component component : betPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel betLabel = (JLabel) component;
                if (betLabel != selectedBetLabel) {
                    betLabel.setVisible(false); // 他のベット画像を非表示
                } else {
                    betLabel.setBounds(400, 400, 100, 225); // 選択したベット画像を右側
                }
            }
        }
        betPanel.revalidate();
        betPanel.repaint();
    }

    // ベット画像を初期状態に戻す
    private void resetBetImages() {
        for (Component component : betPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel betLabel = (JLabel) component;
                betLabel.setVisible(true); // 全てのベット画像を表示
                betLabel.setBounds(0, 0, 100, 225); // 元の位置に戻す（必要に応じて調整）
            }
        }
        betPanel.revalidate();
        betPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ui::new);
    }
}
