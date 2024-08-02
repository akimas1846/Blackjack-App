import java.util.Vector;

class Dealer extends Human {
    
    Vector<Integer> point = new Vector();

    public void open(){

    }

    private int getOpenOneScore() {
        return 0;
    }

    public void drawOver17(){
    }

    public void sayYouWin(){
        System.out.println("おめでとうございます! あなたの勝ちです。");
    }

    public void YouLose(){
        System.out.println("私の勝ちです。");
    }

    public void sayYouDraw(){
        System.out.println("引き分けです。");
    }
}
