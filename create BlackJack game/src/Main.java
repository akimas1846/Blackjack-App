public class Main {
    public static void main(String[] args) throws Exception {
        Card Clover[] = new Card[13],
                Spead[] = new Card[13],
                Heart[] = new Card[13],
                Diamond[] = new Card[13];
        
        for (int i = 0; i < 13; i++) {
            Clover[i] = new Card(i + 1);
            Clover[i].transrate(i);
            Spead[i] = new Card(i + 1);
            Spead[i].transrate(i);
            Heart[i] = new Card(i + 1);
            Heart[i].transrate(i);
            Diamond[i] = new Card(i + 1);
            Diamond[i].transrate(i);
        }

        
    }
}
