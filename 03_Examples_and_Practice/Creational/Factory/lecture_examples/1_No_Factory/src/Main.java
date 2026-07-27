public class Main {
    public static void main(String[] args) {
        CandyStore store = new CandyStore();
        Candy candy = store.sellCandy("Starburst");
        System.out.println(candy.name());
    }
}