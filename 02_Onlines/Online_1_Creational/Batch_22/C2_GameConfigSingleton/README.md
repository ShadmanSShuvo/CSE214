# Batch 22 - Section C2: Game Engine Configuration Manager

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Singleton Pattern
- **Language:** Java

---

## 1. Problem Statement

In a 3D game engine, multiple subsystems (Graphics Engine, Audio Engine, AI Engine) require access to global game configuration parameters (e.g., screen resolution, audio volume, difficulty level). Reading these settings from disk repeatedly causes performance degradation, and multiple configuration objects would lead to desynchronization across engines.

Implement `GameConfig` as a **Singleton** class so that:
- The configuration file is parsed from disk exactly once upon the first request (lazy initialization).
- All game subsystems obtain the identical configuration instance in memory.
- Direct instantiation via `new GameConfig()` is prohibited.

---

## 2. Design Pattern & Architecture

### Why Singleton?
Configuration state must be globally accessible and consistent across disparate game subsystems. The **Singleton Pattern** ensures a single authoritative instance is loaded lazily and shared by all engine components.

```
+-------------------------------------------------+
|                   GameConfig                    |
+-------------------------------------------------+
| - instance: GameConfig                          |
| - resolution: String                            |
| - audioVolume: int                              |
| - difficultyLevel: String                       |
+-------------------------------------------------+
| - GameConfig()                                  | <-- private constructor
| + getInstance(): GameConfig                     | <-- global access point
| + getResolution(): String                       |
| + getAudioVolume(): int                         |
| + getDifficultyLevel(): String                  |
+-------------------------------------------------+
                         ^
      +------------------+------------------+
      |                  |                  |
+----------------+ +--------------+ +---------------+
| GraphicsEngine | | AudioEngine  | |   AIEngine    |
+----------------+ +--------------+ +---------------+
| uses:          | | uses:        | | uses:         |
| GameConfig...  | | GameConfig...| | GameConfig... |
+----------------+ +--------------+ +---------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Singleton** | `GameConfig` | Holds resolution, volume, and difficulty settings; loads data once on first request. |
| **Subsystem 1** | `GraphicsEngine` | Queries screen resolution via `GameConfig.getInstance()`. |
| **Subsystem 2** | `AudioEngine` | Queries audio volume via `GameConfig.getInstance()`. |
| **Subsystem 3** | `AIEngine` | Adjusts NPC behavior based on difficulty via `GameConfig.getInstance()`. |
| **Client / Driver** | `C2_GameConfigSingleton` | Demonstrates usage and verifies reference equality (`c1 == c2`). |

---

## 3. Solution Walkthrough

1. **Private Constructor**: Prevents arbitrary instantiation and simulates expensive disk parsing.
2. **Lazy Initialization (`getInstance`)**: Instantiates `GameConfig` only when first referenced.
3. **Subsystem Integration**:
   - `GraphicsEngine`: Reads resolution (`"1920x1080"`).
   - `AudioEngine`: Reads volume level (`80`).
   - `AIEngine`: Adjusts difficulty behavior (`"Normal"`).
4. **Identity Verification**:
   ```java
   GameConfig c1 = GameConfig.getInstance();
   GameConfig c2 = GameConfig.getInstance();
   System.out.println("c1 == c2: " + (c1 == c2)); // Prints true
   ```

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac C2_GameConfigSingleton.java
java C2_GameConfigSingleton
```

### Sample Output
```
Loading configuration from disk... (expensive operation)
GraphicsEngine rendering at 1920x1080
AudioEngine playing sound at volume 80
AIEngine adjusting behavior for difficulty: Normal
c1 == c2: true
```
