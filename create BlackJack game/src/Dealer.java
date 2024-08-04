class Dealer extends Human {

    private boolean isOpen = false;
    
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
	
    // isOpen カードの表が見えているか
	public boolean isOpen() {
		return isOpen;
	}

	// isOpen セットする isOpen
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
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
