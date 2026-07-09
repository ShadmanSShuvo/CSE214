public class StarburstStore extends CandyStore {
    @Override
    protected Candy createCandy() {
        return new Starburst();
    }
}