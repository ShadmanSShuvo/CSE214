public abstract class CandyStore {
    protected abstract Candy createCandy();
    public Candy sellCandy(){
        Candy candy = createCandy();
        System.out.println("Selling " + candy.name());
        return candy;
    }
}