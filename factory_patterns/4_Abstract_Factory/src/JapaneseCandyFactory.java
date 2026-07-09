public class JapaneseCandyFactory implements CandyFactory {
    @Override
    public Starburst createStarburst() {
        return new JapaneseStarburst();
    }
    @Override
    public Gumdrop createGumdrop() {
        return new JapaneseGumdrop();
    }
}