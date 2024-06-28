import javax.swing.JFrame;

public class Ui {
    private JFrame frame;

    public Ui() {
        frame = new JFrame();
        frame.setTitle("GUI");
        frame.setSize(400, 300); // 必要に応じてサイズを設定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // 画面の中央に表示
    }

    public void show() {
        frame.setVisible(true);
    }
}
