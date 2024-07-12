import java.util.Random;

public abstract class Human {
    int point;

    Random rand = new Random();

    public abstract void open();

    public void clear(int point) {
        point = 0;
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
