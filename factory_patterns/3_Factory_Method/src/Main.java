public class Main {
    public static void main(String[] args){
        CandyStore store1 = new StarburstStore();
        CandyStore store2 = new GumdropStore();
        store1.sellCandy();
        store2.sellCandy();
    }
}