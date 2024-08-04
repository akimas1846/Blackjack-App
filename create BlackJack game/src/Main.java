import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Card[][] deck = new Card[4][13];

        String[] cardNames = { "Clover", "Spead", "Heart", "Diamond" };
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 13; i++) {
                deck[j][i] = new Card(i + 1, cardNames[j]);
            }
        }

        Human[] PL = new Human[4];
        Human dealer;

        for (int i = 0; i < 4; i++) {
            PL[i] = new Player(0);
        }

        dealer = new Dealer();

        Scanner scan = new Scanner(System.in);
        String msg = scan.nextLine();
        

        scan.close();
        
        Ui ui = new Ui(PL,deck);
        ui.show();

    }
}
