public class GumdropStore extends CandyStore {
    @Override
    protected Candy createCandy() {
        return new Gumdrop();
    }
}