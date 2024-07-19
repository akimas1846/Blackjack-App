class Player extends Human {
    private Integer pocketMoney = 0;
	// ゲームでベットしたベル
	private Integer betMoney = 0;
	// スタンドしたかどうか
	private boolean isStand = false;
	// バーストしたかどうか
	private boolean isBurst = false;
	// splitを選択したかどうか
	private boolean isSplit = false;
	// split後の手札をスタンドしたか
	private boolean isSplitStand = false;
	// split後の手札がバーストしたかどうか
	private boolean isSplitBurst = false;
    
    public void handClear() {
        clear(point);
    }

    public void open(){
        System.out.println(point);
    }

    public void allOpen(){
        
    }

    public boolean possibleSplit() {
        return true;
    }

    public boolean possibleDoubleDown() {
        return true;
    }

    public void split(){

    }

    //所持ベル
    public int getPocketMoney() {
        return pocketMoney;
    }

    //pocketMoney セットする pocketMoney
    public void setPocketMoney(int pocketMoney){
        this.pocketMoney = pocketMoney;
    }

    //betMoney ベットするベル額
    public Integer getBetMoney() {
		return betMoney;
	}

    //betMoney セットする betMoney
    public void setBetMoney(int betMoney){
        this.betMoney = betMoney;
    }

    //isStand satand状態か
    public boolean isStand() {
		return isStand;
	}

    //isStand セットする isStand
	public void setStand(boolean isStand) {
		this.isStand = isStand;
	}

    //isBurst バーストしているか
    public boolean isBurst() {
        return isBurst;
    }

    //isBurst セットする isBurst
    public void setBurst(boolean isBurst) {
		this.isBurst = isBurst;
	}

    //isSplit splitを選択したか
    public boolean isSplit() {
		return isSplit;
	}

	//isSplit セットする isSplit
	public void setSplit(boolean isSplit) {
		this.isSplit = isSplit;
	}
	//分けた手札をstandしたか
	public boolean isSplitStand() {
		return isSplitStand;
	}

	//isSplitStand セットする isSplitStand
	public void setSplitStand(boolean isSplitStand) {
		this.isSplitStand = isSplitStand;
	}

	//isSplitBurst 分けた手札がバーストしたかどうか
	public boolean isSplitBurst() {
		return isSplitBurst;
	}

	//isSplitBurst セットする isSplitBurst
	public void setSplitBurst(boolean isSplitBurst) {
		this.isSplitBurst = isSplitBurst;
	}
}
