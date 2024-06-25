public class Card {
    private int cardsNumber;
    private char UniqueCardsNumber;
    Card(int c0)
    {
        cardsNumber = c0;
    }

    void transrate(int cardsNumber)
    {
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
            
            default:
                UniqueCardsNumber = 'K';
                break;
        }
    }
    
}
