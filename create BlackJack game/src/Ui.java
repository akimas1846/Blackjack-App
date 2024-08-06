import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JLabel cardLabel3;
    private JLabel creditLabel;
    private JLabel betLabel;
    private JLabel roundLabel;
    private int credit = 500;
    private int bet = 0;
    private int roundCount = 5;

    String resultMessage;
    ImageIcon resultImage = null;
    private JPanel resultPanel;
    private boolean nextRoundClicked = false;

    private Human[] players;
    private Human[] bufferPlayers;
    private Card[][] deck;
    private Card[][] bufferDeck;
    private JPanel buttonPanel;
    private JPanel betPanel; // ベットパネルの参照を保持


    public Ui(Human[] players, Card[][] deck) {
        // 初期画面
        frame = new JFrame();
        frame.setTitle("Blackjack Game");
        // windowsが閉じられた時
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 100, 0));
        // 複数のパネルを使う
        frame.setLayout(new CardLayout());
        // これはパネル
        createStartPanel();
        createGamePanel();
        createRulesPanel();
        createGameOverPanel();
        // スタート画面、ゲーム画面、ルール画面、ゲームオーバー画面
        frame.add(startPanel, "Start");
        frame.add(gamePanel, "Game");
        frame.add(rulesPanel, "Rules");
        frame.add(gameOverPanel, "GameOver");
        // 境界線
        frame.setUndecorated(false);
        // フルスクリーンじゃない
        frame.setResizable(false);
        frame.setSize(900, 700);
        // フレームを表示
        frame.setVisible(true);

        this.players = players;
        this.deck = deck;
        bufferDeck = deck;
        bufferPlayers = players;

    }

    // 最初の画面
    void createStartPanel() {
        // レイアウトBorderLayout
        startPanel = new JPanel();
        startPanel.setBackground(new Color(0, 100, 0));
        startPanel.setLayout(new BorderLayout());
        // テキストで
        JLabel welcomeLabel = createStyledLabel("Welcome to Blackjack");
        startPanel.add(welcomeLabel, BorderLayout.CENTER);
        // ボタンを中央
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(0, 100, 0));
        // ボタンがクリックされたらshowGamePanel
        JButton startButton = createStyledButton("Start Game");
        startButton.addActionListener(e -> showGamePanel()); // ここでshowGamePanel() [ゲーム画面]に移動
        buttonPanel.add(startButton);
        // スタイル付きボタンでView Rule
        JButton rulesButton = createStyledButton("View Rules");
        rulesButton.addActionListener(e -> showRulesPanel());// ここでshowRulesPanel() [ルール説明画面]に移動
        buttonPanel.add(rulesButton);
        // ボタンを右に追加
        startPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // GameStartの画面のラベル、位置
    private void createGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setBackground(new Color(0, 100, 0));
        gamePanel.setLayout(null);
        // クレジットを表示するラベル
        creditLabel = createStyledLabel("Credit: $" + credit);
        creditLabel.setFont(new Font("Serif", Font.BOLD, 16));
        creditLabel.setPreferredSize(new Dimension(200, 30));
        creditLabel.setBounds(20, 20, 300, 50);
        gamePanel.add(creditLabel);
        // ベットを表示するラベル
        betLabel = createStyledLabel("Bet: $" + bet);
        betLabel.setFont(new Font("Serif", Font.BOLD, 16));
        betLabel.setPreferredSize(new Dimension(200, 30));
        betLabel.setBounds(20, 80, 300, 50);
        gamePanel.add(betLabel);
        // ゲームの回数を表示するラベル
        roundLabel = createStyledLabel("Rounds : " + roundCount);
        roundLabel.setFont(new Font("Serif", Font.BOLD, 16));
        roundLabel.setPreferredSize(new Dimension(200, 30));
        roundLabel.setBounds(20, 140, 300, 50);
        gamePanel.add(roundLabel);
        // カードのラベル
        cardLabel1 = new JLabel();
        cardLabel1.setPreferredSize(new Dimension(100, 225));
        cardLabel1.setBounds(450, 50, 100, 225);
        cardLabel1.setVisible(false);
        gamePanel.add(cardLabel1);

        cardLabel2 = new JLabel();
        cardLabel2.setPreferredSize(new Dimension(100, 225));
        cardLabel2.setBounds(400, 330, 100, 225);
        cardLabel2.setVisible(false);
        gamePanel.add(cardLabel2);

        cardLabel3 = new JLabel(); // 3枚目のカードラベル
        cardLabel3.setPreferredSize(new Dimension(100, 225));
        cardLabel3.setBounds(500, 330, 100, 225); // 3枚目カードの位置
        cardLabel3.setVisible(false);
        gamePanel.add(cardLabel3);

        // タイトルボタンの追加
        JButton titleButton = createStyledButton("Title");
        titleButton.setBounds(770, 600, 70, 40);
        titleButton.addActionListener(e -> showStartPanel());
        gamePanel.add(titleButton);

        // ボタンパネルを非表示のまま作成
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setBackground(new Color(0, 100, 0));
        buttonPanel.setBounds(5, 500, 300, 80);
        buttonPanel.setVisible(false); // 初期状態では非表示
        // ゲームボタンの追加
        JButton raiseButton = createStyledButton("hit");
        raiseButton.addActionListener(e -> raiseAction());
        buttonPanel.add(raiseButton);

        JButton stayButton = createStyledButton("stay");
        stayButton.addActionListener(e -> stayAction());
        buttonPanel.add(stayButton);

        JButton doubleButton = createStyledButton("double");
        doubleButton.addActionListener(e -> doubleAction());
        buttonPanel.add(doubleButton);
        // ゲームパネルに追加
        gamePanel.add(buttonPanel);
        // ベット画像の追加
        addBetImages();
    }

    // レイズの動作
    private void raiseAction() {
        // ここにレイズの処理を追加
        players[0].drow(deck);
        System.out.println("hitが選択されました。" + " 現在のポイント" + players[0].point + "bet額" + bet);
    }

    // ステイの動作
    private void stayAction() {
        // ここにステイの処理を追加
        System.out.println("stayが選択されました。" + " 現在のポイント" + players[0].point + "bet額" + bet);

        disableGameButtons();
        dealerTurn();
    }

    // ダブルの動作
    private void doubleAction() {
        // ここにダブルの処理を追加
        players[0].drow(deck);
        deductCredit(players[0].thisBetting);
        players[0].thisBetting += players[0].thisBetting;
        bet += bet;
        System.out.println(
                "doubleが選択されました。" + " " + "現在のポイント" + players[0].point + "bet額" + bet + " " + players[0].thisBetting);

        disableGameButtons();
        dealerTurn();
    }

    private void dealerTurn() {
        // ディーラーのアクション（例としてシンプルに実装）
        while (players[1].point < 17) {
            players[1].drow(deck);
            System.out.println("ディーラーがカードを引きました。" + " 現在のポイント" + players[1].point);
        }

        // 勝敗判定を行う（シンプルな例）
        if (players[1].point > 21 || players[0].point > players[1].point) {
            resultMessage = "プレイヤーの勝ち！";
            resultImage = loadImage("create BlackJack game\\image\\win.png");
        } else if (players[0].point == players[1].point) {
            resultMessage = "引き分け！";
            resultImage = loadImage("create BlackJack game\\image\\draw.png");
        } else {
            resultMessage = "ディーラーの勝ち！";
            resultImage = loadImage("create BlackJack game\\image\\lose.png");
        }
        System.out.println(resultMessage);

        // ディーラーのターンが終わった後に結果画像を表示
        showResultImage(resultImage);
    }

    private void showResultImage(ImageIcon resultImage) {
        if (resultImage == null) {
            System.err.println("結果画像がロードできませんでした。");
            return;
        }

        System.out.println("描画準備");

        // 結果画像を表示するための新しいパネルを作成
        resultPanel = new JPanel();
        resultPanel.setBackground(new Color(0, 100, 0));
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBounds(100, 300, 100, 100);
        JLabel resultLabel = new JLabel(resultImage);
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        // Next Round ボタンを作成
        JButton nextRoundButton = createStyledButton("Next");
        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideResultImage();
                resetValuesToNextGame();
                showGamePanel();
            }
        });

        // ボタンを結果パネルに追加
        resultPanel.add(nextRoundButton, BorderLayout.SOUTH);
        System.out.println("描画準備完了");

        // ゲームパネルに追加して表示
        gamePanel.add(resultPanel);
        gamePanel.revalidate();
        gamePanel.repaint();
        System.out.println("描画完了");

        
    }

    private void hideResultImage() {
        if (resultPanel != null) {
            gamePanel.remove(resultPanel);
            gamePanel.revalidate();
            gamePanel.repaint();
            System.out.println("結果画像が非表示になりました");
        }
    }

    private ImageIcon loadImage(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("リソースが見つかりません: " + path);
            return null;
        }
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    // ボタンを無効化するメソッド
    private void disableGameButtons() {
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setEnabled(false); // ボタンを無効化
            }
        }
    }

    // ボタンを有効化するメソッド
    private void enableGameButtons() {
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setEnabled(true); // ボタンを有効化
            }
        }
    }

    // ルールの場面
    private void createRulesPanel() {
        // パネルの作成
        rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.setBackground(new Color(0, 100, 0));

        rulesLayout = new CardLayout();
        // 中央に複数切り替えて表示
        rulesContainer = new JPanel(rulesLayout);
        rulesPanel.add(rulesContainer, BorderLayout.CENTER);
        // ボタンパネルの作成
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(0, 100, 0));
        // 前にするボタンを追加
        JButton prevButton = createStyledButton("Previous");
        prevButton.addActionListener(e -> rulesLayout.previous(rulesContainer));
        buttonPanel.add(prevButton);
        // 次にするボタンを追加
        JButton nextButton = createStyledButton("Next");
        nextButton.addActionListener(e -> rulesLayout.next(rulesContainer));
        buttonPanel.add(nextButton);
        // タイトルボタンを追加
        JButton titleButton = createStyledButton("Title");
        titleButton.addActionListener(e -> showStartPanel());
        buttonPanel.add(titleButton);
        // ボタンパネルを追加
        rulesPanel.add(buttonPanel, BorderLayout.SOUTH);
        // ルールの画像
        addRuleImage("create BlackJack game\\image\\Rule1.png");
        addRuleImage("create BlackJack game\\image\\Rule2.png");
        addRuleImage("create BlackJack game\\image\\Rule3.png");
    }


    // Gameの終了画面
    private void createGameOverPanel() {
        gameOverPanel = new JPanel(new BorderLayout());
        gameOverPanel.setBackground(new Color(0, 100, 0));
        // GameOverのラベル
        JLabel gameOverLabel = createStyledLabel("Game Over");
        gameOverLabel.setFont(new Font("Serif", Font.BOLD, 48));
        gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);
        // ボタンパネル
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(0, 100, 0));

        // 再スタートするボタン
        JButton restartButton = createStyledButton("Restart");
        restartButton.addActionListener(e -> {
            resetValues();
            showGamePanel();
        });
        buttonPanel.add(restartButton);
        // タイトルのボタン

        JButton titleButton = createStyledButton("Title");
        titleButton.addActionListener(e -> {
            showStartPanel();
        });
        buttonPanel.add(titleButton);

        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // ルールの画面、背景とか
    // 画像パスから画像を読み込み、表示するラベルを作成、新しいパネルに追加
    private void addRuleImage(String imagePath) {
        ImageIcon ruleIcon = createResizedImageIcon(imagePath, 450, 650);
        JLabel ruleLabel = new JLabel(ruleIcon);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 100, 0));
        panel.add(ruleLabel, BorderLayout.CENTER);
        rulesContainer.add(panel);
    }

    // ラベルの外観追加
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        // フォント
        label.setFont(new Font("Serif", Font.BOLD, 48));
        // 文字の色
        label.setForeground(Color.WHITE);
        // 白い枠
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        label.setOpaque(true);
        label.setBackground(new Color(0, 100, 0));
        return label;
    }

    // ボタンの一貫したものを追加
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        // ボタンのサイズ
        button.setFont(new Font("Serif", Font.BOLD, 24));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        // 白い枠
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        return button;
    }

    // ゲームパネルを表示し、ゲーム開始時に2つのカードラベルにバックサイドのカード画像を設定
    private void showGamePanel() {
        // Gameパネルの表示
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Game");
        // カード
        players[0].firstDrow(deck);

        System.out.println(players[0].cardIDs.get(0));
        setCardImage(cardLabel1, "create BlackJack game\\image\\Back.png", 100, 225);
        setCardImage(cardLabel2, players[0].cardIDs.get(0), 100, 225);
        setCardImage(cardLabel3, players[0].cardIDs.get(1), 100, 225);
    }

    // ルール
    private void showRulesPanel() {
        // コンテンツペインからレイアウトマネージャーを所得
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Rules");
    }

    // スタート
    private void showStartPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "Start");
        resetValues();
        resetBetImages(); // ベット画像をリセット
    }

    // ゲームの終わり
    private void showGameOverPanel() {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), "GameOver");
    }

    // restartGameにした時初期化できるようにしている
    private void resetValues() {
        credit = 500;
        bet = 0;
        roundCount = 5;
        players = bufferPlayers;
        deck = bufferDeck;
        updateCreditDisplay();// クレジットを表示
        updateBetDisplay();// ベットを表示
        updateRoundDisplay();// ラウンドを表示
        cardLabel1.setVisible(false);
        cardLabel2.setVisible(false);
        cardLabel3.setVisible(false);
        resultImage = null;
        for (int i = 0; i < deck.length; i++) {
            for (int j = 0; j < deck[i].length; j++) {
                deck[i][j].isTurned = false; // Assuming the method to reset the card is called setTurned
            }
        }

        for (int i = 0; i < 2; i++) 
        {
            players[i].thisBetting = 0;
            players[i].point = 0;
            players[i].cardIDs.clear();
        }
        buttonPanel.setVisible(false); // 初期化時にボタンパネルを非表示にする
        enableGameButtons();
        resetBetImages(); // ベット画像をリセット
    }

    private void resetValuesToNextGame() 
    {
        players = bufferPlayers;
        deck = bufferDeck;
        updateCreditDisplay();// クレジットを表示
        updateBetDisplay();// ベットを表示
        updateRoundDisplay();// ラウンドを表示
        cardLabel1.setVisible(false);
        cardLabel2.setVisible(false);
        cardLabel3.setVisible(false);
        buttonPanel.setVisible(false); // 初期化時にボタンパネルを非表示にする
        resultImage = null;
        for (int i = 0; i < deck.length; i++) {
            for (int j = 0; j < deck[i].length; j++) {
                deck[i][j].isTurned = false; // Assuming the method to reset the card is called setTurned
            }
        }

        for (int i = 0; i < 2; i++) 
        {
            players[i].thisBetting = 0;
            players[i].point = 0;
            players[i].cardIDs.clear();
        }
    
        enableGameButtons();
        resetBetImages(); // ベット画像をリセット
    }

    // ルールの画像設定
    private ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        try {
            // image Pathから画像を読み込む
            BufferedImage image = ImageIO.read(new File(imagePath));

            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            // 画像の読み込みやリサイズの際エラーが発生したらnullを返す
            e.printStackTrace();
            return null;
        }
    }

    // カードの画像設定
    private void setCardImage(JLabel label, String imagePath, int width, int height) {
        try {
            BufferedImage cardImage = ImageIO.read(new File(imagePath));
            Image resizedImage = cardImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            // 画像をImageIconオブジェクトに変更
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
        int height = 100;
        // 画像の間隔
        betPanel = new JPanel();
        betPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        betPanel.setBackground(new Color(0, 100, 0));

        for (int i = 0; i < betImagePaths.length; i++) {
            String path = betImagePaths[i];
            int betAmount = getBetAmount(i);
            JLabel betImageLabel = new JLabel();
            // 画像をラベルに設定
            setCardImage(betImageLabel, path, width, height);
            // BetImageMouseListenerでクリックされたときの動作
            betImageLabel.addMouseListener(new BetImageMouseListener(betAmount, betImageLabel));
            betPanel.add(betImageLabel);
        }

        betPanel.setBounds(100, 550, 800, 300);
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
        roundLabel.setText("Rounds : " + roundCount);
    }

    // クレジットを見せる
    public void show() {
        frame.setVisible(true);
    }

    // クレジットを加える
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
        // クレジットが0でラウンドが0になったらやめる
        if (credit <= 0 || roundCount <= 0) {
            showGameOverPanel();
        }
    }

    // ベット数をクリックしたらの動作
    private class BetImageMouseListener extends MouseAdapter {
        private int betAmount; // クリックされたベット画像に対応するベット額
        private JLabel betImageLabel;// クリックされたベット画像

        // 初期化
        public BetImageMouseListener(int betAmount, JLabel betImageLabel) {
            this.betAmount = betAmount;
            this.betImageLabel = betImageLabel;
        }

        // マウスクリックのイベント
        public void mouseClicked(MouseEvent e) {
            if (credit >= betAmount) {
                deductCredit(betAmount);// クレジット額を減らす
                addBet(betAmount);// ベット額を増やす
                decrementRound();// ラウンド数を減らす()
                buttonPanel.setVisible(true); // ベットが選択されたらボタンパネルを表示
                players[0].thisBetting = betAmount;
                moveBetImageToRight(betImageLabel);

                cardLabel1.setVisible(true);
                cardLabel2.setVisible(true);
                cardLabel3.setVisible(true);
            }
        }
    }

    // 他のベット画像を非表示にする
    private void moveBetImageToRight(JLabel selectedBetLabel) {
        for (Component component : betPanel.getComponents()) {
            if (component instanceof JLabel) {
                // JLabelであるかの確認
                JLabel betLabel = (JLabel) component;
                if (betLabel != selectedBetLabel) {
                    // selectedBetLabel以外のJLabelは非表示
                    betLabel.setVisible(false); // 他のベット画像を非表示
                } else {
                    // 選択されたベット画像を移動
                    betLabel.setBounds(400, 400, 100, 225); // 選択したベット画像
                }
            }
        }
        betPanel.revalidate();
        // 再描画
        betPanel.repaint();
    }

    // ベット画像を初期状態に戻す
    private void resetBetImages() {
        for (Component component : betPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel betLabel = (JLabel) component;
                betLabel.setVisible(true); // 全てのベット画像を表示
                betLabel.setBounds(400, 400, 100, 225); // 元の位置に戻す（必要に応じて調整）
            }
        }
        betPanel.revalidate();
        betPanel.repaint();
    }

}