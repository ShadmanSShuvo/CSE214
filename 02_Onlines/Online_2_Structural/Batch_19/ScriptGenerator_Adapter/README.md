# Batch 19 Online 2 - Audio Script Generator (Adapter Pattern)

## Problem Statement
An audio processing system utilizes a legacy `EnglishScriptGenerator` that implements the `ScriptGenerator` interface to process English speech audio input and generate text scripts (stripping outer quotations and formatting text).

A new requirement arises to support **Bangla speech audio input** (e.g., phonetic inputs such as `"Amra bhat khai."`, `"Ami roti banai."`). The existing `EnglishScriptGenerator` cannot process Bangla speech directly, and its core implementation must not be modified.

The task is to seamlessly integrate a `BanglaToEnglishTranslator` with the existing `EnglishScriptGenerator` using an appropriate structural design pattern so that clients can treat both English and Bangla audio script generation uniformly through the standard `ScriptGenerator` target interface.

---

## Design Pattern Analysis

### Pattern Applied: **Adapter Pattern (Object Adapter)**

### Why Adapter?
- **Incompatible Interface / Data Contract**: The existing script generator engine expects English speech, while incoming audio feeds contain Bangla speech.
- **Open/Closed Principle**: We cannot and should not modify the existing `EnglishScriptGenerator` class.
- **Target Interface Compliance**: The client code expects any script processor to implement the `ScriptGenerator` interface (`generateScript(String speechAudioInput)`).
- The Adapter (`BanglaToEnglishAdapter`) implements `ScriptGenerator`, intercepts Bangla input, translates it via `BanglaToEnglishTranslator`, and delegates script generation to the wrapped `EnglishScriptGenerator`.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Target** | `ScriptGenerator` | The standard interface expected by client code (`generateScript`). |
| **Adaptee / Concrete Target** | `EnglishScriptGenerator` | The existing class that generates scripts from English speech. |
| **Helper / Translator** | `BanglaToEnglishTranslator` | Translates and reorders Bangla phonetic grammar (SOV $\rightarrow$ SVO). |
| **Adapter** | `BanglaToEnglishAdapter` | Implements `ScriptGenerator`, adapts Bangla speech for `EnglishScriptGenerator`. |
| **Client** | `ScriptGeneratorApp` | Inspects audio input and routes to appropriate generator uniformly. |

---

## Class Architecture

```
         <<interface>>
        ScriptGenerator
       +generateScript(input)
          ^          ^
          |          |
  +-------+          +-----------------------+
  |                                          |
EnglishScriptGenerator             BanglaToEnglishAdapter
  +generateScript(english)          -englishScriptGenerator: EnglishScriptGenerator
                                    +generateScript(bangla)
                                              | delegates to
                                              v
                                   BanglaToEnglishTranslator
```

---

## Solution Walkthrough

1. **Target Interface**:
   ```java
   interface ScriptGenerator {
       String generateScript(String speechAudioInput);
   }
   ```
2. **Adaptee (`EnglishScriptGenerator`)**:
   Cleans up and strips surrounding quotes from English speech.
3. **Translation Engine (`BanglaToEnglishTranslator`)**:
   Maintains a vocabulary dictionary and converts Subject-Object-Verb (Bangla) into Subject-Verb-Object (English).
   - `"Amra bhat khai"` $\rightarrow$ `"We eat rice."`
   - `"Ami roti banai"` $\rightarrow$ `"I prepare bread."`
4. **Adapter (`BanglaToEnglishAdapter`)**:
   Receives raw Bangla speech, translates it to English, and forwards the translated speech to `EnglishScriptGenerator.generateScript(...)`.
5. **Smart Routing in Driver**:
   `processAudioInput` detects whether the input is Bangla or English and dispatches to the generator transparently.

---

## How to Compile & Run

```bash
cd Batch_19/ScriptGenerator_Adapter
javac *.java
java ScriptGeneratorApp
```

### Expected Output
```
Input                        Output
-------------------------------------------------------
"Hello world!"               Hello world!
"Amra bhat khai."            We eat rice.
"The sky is clear today."    The sky is clear today.
"Ami roti banai."            I prepare bread.
```
