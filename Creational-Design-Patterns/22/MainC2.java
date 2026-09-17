// singleton pattern
class GameConfig {
    // Single local runtime state instance reference
    private static GameConfig instance;

    // Configuration Parameter Metrics
    private String resolution;
    private int audioVolume;
    private String difficultyLevel;

    // Private constructor mimics expensive Disk I/O simulation once
    private GameConfig() {
        System.out.println("Executing expensive disk parsing operations... Loading game configs.");
        this.resolution = "1920x1080";
        this.audioVolume = 85;
        this.difficultyLevel = "Hard";
    }

    // Thread-safe safe validation singleton reference access point
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public void printConfigProperties() {
        System.out.println("Settings -> Resolution: " + resolution + " | Volume: " + audioVolume + " | Difficulty: " + difficultyLevel);
    }
}

// Client application
public class MainC2 {
    public static void main(String[] args) {
        // Graphics engine requests configurations
        GameConfig graphicsEngineConfig = GameConfig.getInstance();
        graphicsEngineConfig.printConfigProperties();

        // Audio engine requests configurations
        GameConfig audioEngineConfig = GameConfig.getInstance();
        
        // AI engine requests configurations
        GameConfig aiEngineConfig = GameConfig.getInstance();

        // Memory validation evaluation step
        System.out.println("Are all references matching identical instances in memory? -> " 
            + (graphicsEngineConfig == audioEngineConfig && audioEngineConfig == aiEngineConfig));
    }
}