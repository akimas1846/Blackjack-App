public class Card {
    private int cardNumber;
    private char UniqueCardsNumber;
    private int cardPoint;

    Card(int c0) {
        cardNumber = c0;
        cardPoint = c0;
    }

    void transrate(int cardsNumber) {
        switch (cardsNumber) {
            case 1:
                UniqueCardsNumber = 'A';
                break;

            case 11:
                UniqueCardsNumber = 'J';
                break;

            case 12:
                UniqueCardsNumber = 'Q';
                break;

            case 13:
                UniqueCardsNumber = 'K';
                break;
            
            default:
                UniqueCardsNumber = ' ';
                break;
        }
    }

    void debug() {
        System.out.println(cardNumber);
        System.out.println(UniqueCardsNumber);
        System.out.println(cardPoint);
    }

}
