public class AmericanCandyFactory implements CandyFactory {
    @Override
    public Starburst createStarburst() {
        return new AmericanStarburst();
    }
    @Override
    public Gumdrop createGumdrop() {
        return new AmericanGumdrop();
    }
}