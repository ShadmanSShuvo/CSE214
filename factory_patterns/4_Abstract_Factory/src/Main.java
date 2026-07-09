public class Main {
    public static void main(String[] args){
        CandyFactory factory = new AmericanCandyFactory();
        Starburst s = factory.createStarburst();
        Gumdrop g = factory.createGumdrop();
        System.out.println(s.name());
        System.out.println(g.name());
    }
}