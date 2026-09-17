// C2: Game Engine Configuration Manager - Singleton Pattern
// Task: Ensure GameConfig always yields exactly one instance in memory,
// loaded once, shared by Graphics, Audio, and AI engines.

// ---------- Singleton ----------
class GameConfig {
    private static GameConfig instance;

    private String resolution;
    private int audioVolume;
    private String difficultyLevel;

    // Private constructor: simulates the expensive load-from-disk operation
    private GameConfig() {
        System.out.println("Loading configuration from disk... (expensive operation)");
        this.resolution = "1920x1080";
        this.audioVolume = 80;
        this.difficultyLevel = "Normal";
    }

    // Global access point
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public String getResolution() {
        return resolution;
    }

    public int getAudioVolume() {
        return audioVolume;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}

// ---------- Client Modules ----------
class GraphicsEngine {
    public void render() {
        GameConfig config = GameConfig.getInstance();
        System.out.println("GraphicsEngine rendering at " + config.getResolution());
    }
}

class AudioEngine {
    public void playSound() {
        GameConfig config = GameConfig.getInstance();
        System.out.println("AudioEngine playing sound at volume " + config.getAudioVolume());
    }
}

class AIEngine {
    public void adjustDifficulty() {
        GameConfig config = GameConfig.getInstance();
        System.out.println("AIEngine adjusting behavior for difficulty: " + config.getDifficultyLevel());
    }
}

// ---------- Client ----------
public class C2_GameConfigSingleton {
    public static void main(String[] args) {
        GraphicsEngine graphics = new GraphicsEngine();
        AudioEngine audio = new AudioEngine();
        AIEngine ai = new AIEngine();

        // Config is loaded only once, on the first getInstance() call
        graphics.render();
        audio.playSound();
        ai.adjustDifficulty();

        GameConfig c1 = GameConfig.getInstance();
        GameConfig c2 = GameConfig.getInstance();

        // Prove there is only ever one instance in memory
        System.out.println("c1 == c2: " + (c1 == c2));
    }
}
