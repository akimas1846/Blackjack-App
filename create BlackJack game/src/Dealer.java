class Dealer extends Human {
    
    
    public void open() {
        System.out.println(point);
    }
    
    public void think(Card[][] cards) 
	{
        if (point < 18) 
        {
            drow(cards);
        }
	}
	
    private int getOpenOneScore() {
        return 0;
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
