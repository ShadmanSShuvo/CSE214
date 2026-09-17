// Abstract class with the template method
abstract class Game {

    // THE TEMPLATE METHOD - cannot be overridden
    public final void play() {
        initialize(); // Step 1
        startPlay(); // Step 2
        endPlay(); // Step 3
    }

    // Steps subclasses must implement
    protected abstract void initialize();

    protected abstract void startPlay();

    protected abstract void endPlay();
}

// Concrete Class 1
class Chess extends Game {

    @Override
    protected void initialize() {
        System.out.println("Chess: Setting up board and pieces");
    }

    @Override
    protected void startPlay() {
        System.out.println("Chess: White moves first");
    }

    @Override
    protected void endPlay() {
        System.out.println("Chess: Checkmate! Game over.");
    }
}

// Concrete Class 2
class Cricket extends Game {

    @Override
    protected void initialize() {
        System.out.println("Cricket: Toss the coin");
    }

    @Override
    protected void startPlay() {
        System.out.println("Cricket: Batting begins");
    }

    @Override
    protected void endPlay() {
        System.out.println("Cricket: Match complete");
    }
}

// Client
public class TemplateMethodDemo {

    public static void main(String[] args) {

        Game chess = new Chess();
        chess.play();

        System.out.println();

        Game cricket = new Cricket();
        cricket.play();
    }
}
