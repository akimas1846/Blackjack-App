import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Human {
    int point, thisBetting = 0;
    List<String> cardIDs = new ArrayList<String>();
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
    // NPCのカードを引く挙動    
    abstract public void think(Card[][] cards);

    // カードを引く挙動
    public void drow(Card[][] cards) {

        int i, j;
        while (true) {
            int indexOfCard = rand.nextInt(13);
            int indexOfCardKind = rand.nextInt(4);

            if (cards[indexOfCardKind][indexOfCard].isTurned == false) {
                i = indexOfCardKind;
                j = indexOfCard;
                break;
            }
        }

        point += cards[i][j].returnPoint();
        cardIDs.add(cards[i][j].returnCradName());
    }
    
    public void firstDrow(Card[][] cards) 
    {
        drow(cards);
        drow(cards);
    }
}
