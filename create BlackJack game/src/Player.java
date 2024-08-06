
class Player extends Human {
	// 手持ちのベル(残金)
	Integer pocketMoney = 0;
	// ゲームでベットしたベル
	Integer betMoney = 0;
	// スタンドしたかどうか
	boolean isStand = false;
	// バーストしたかどうか
	boolean isBurst = false;

	/*
	 * コンストラクタ
	 * pocketMoney 初期で持たせる所持ベル
	 */
	public Player(int pocketMoney) {
		this.pocketMoney = pocketMoney;
	}

	public void think(Card[][] cards) {
		// do nothing.
	}

	/*
	 * 手札の初期化
	 */
	@Override
	public void handClear() {
		super.handClear();
	}

	public void open() {
		System.out.println(point);
	}

	public boolean possibleDoubleDown() {
		boolean possibleDoubleDown = false;
		if (super.hand.size() == 2 && betMoney * 2 <= pocketMoney) {
			possibleDoubleDown = true;
		}
		return possibleDoubleDown;
	}

	// 所持ベル
	public int getPocketMoney() {
		return pocketMoney;
	}

	// pocketMoney セットする pocketMoney
	public void setPocketMoney(int pocketMoney) {
		this.pocketMoney = pocketMoney;
	}

	// betMoney ベットするベル額
	public Integer getBetMoney() {
		return betMoney;
	}

	// betMoney セットする betMoney
	public void setBetMoney(int betMoney) {
		this.betMoney = betMoney;
	}

	// isStand satand状態か
	public boolean isStand() {
		return isStand;
	}

	// isStand セットする isStand
	public void setStand(boolean isStand) {
		this.isStand = isStand;
	}

	// isBurst バーストしているか
	public boolean isBurst() {
		return isBurst;
	}

	// isBurst セットする isBurst
	public void setBurst(boolean isBurst) {
		this.isBurst = isBurst;
	}
}
