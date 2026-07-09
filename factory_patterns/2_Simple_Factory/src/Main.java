public class Main {
    public static void main(String[] args){
        CandyStore store = new CandyStore();
        Candy candy = store.sellCandy("Gumdrop");
        System.out.println(candy.name());
    }
}