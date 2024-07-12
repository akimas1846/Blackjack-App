public class Main {
    public static void main(String[] args) throws Exception {
        Card[][] deck = new Card[4][13];

        String[] cardNames = { "Clover", "Spead", "Heart", "Diamond" };
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 13; i++) {
                deck[j][i] = new Card(i + 1, cardNames[j]);
                deck[j][i].debug();
            }
        }
        Ui ui = new Ui();
        ui.show();
    }

    
}
