public class CandyFactory {
    
    // The factory abstracts away which core base builder configuration you receive
    public static CandyBuilder createStarburstBuilder() {
        return new CandyBuilder("Starburst")
                    .setColor("Pink"); // sets structural defaults specific to Starbursts
    }

    public static CandyBuilder createGumdropBuilder() {
        return new CandyBuilder("Gumdrop")
                    .setColor("Red"); // sets structural defaults specific to Gumdrops
    }
}