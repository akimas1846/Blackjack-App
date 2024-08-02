import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Human {
    int point;

    Random rand = new Random();

    /*
	手札
	*/
	protected List<Card> hand = new ArrayList<>();
    /*
    手札を返す
    */
	public List<Card> getHand() {
		return hand;
	}
    /*
    手札をセットする 
    */
	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

    /* 手札を開示する */
    public abstract void open();

    public void allOpen(){
    }

    public void clear(int point) {
        point = 0;
    }

    /*
	手札の初期化
	*/
	public void handClear() {
		hand = new ArrayList<>();
	}

    public void drow(Card[][] cards) {
        int i, j;
        while (true) {
            int indexOfCard = rand.nextInt(13);
            int indexOfCardKind = rand.nextInt(4);

            if (cards[indexOfCardKind][indexOfCard].isTurned == false) {
                i = indexOfCardKind;
                j = indexOfCard;
                cards[i][j].debug();
                break;
            }
        }
        point += cards[i][j].returnPoint();
    }
}
