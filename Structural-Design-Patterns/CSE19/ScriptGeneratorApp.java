import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// ==========================================
// 1. TARGET INTERFACE
// ==========================================
interface ScriptGenerator {
    String generateScript(String speechAudioInput);
}

// ==========================================
// 2. EXISTING COMPONENT (Adaptee / Concrete Target)
// ==========================================
class EnglishScriptGenerator implements ScriptGenerator {
    @Override
    public String generateScript(String englishSpeech) {
        if (englishSpeech == null || englishSpeech.isEmpty()) {
            return "";
        }
        // Removes opening and closing quotation marks
        return englishSpeech.replaceAll("^\"|\"$", "");
    }
}

// ==========================================
// 3. TRANSLATION ENGINE (Utility Helper)
// ==========================================
class BanglaToEnglishTranslator {
    private static final Map<String, String> dictionary = new HashMap<>();

    static {
        dictionary.put("Ami", "I");
        dictionary.put("ami", "I");
        dictionary.put("Amra", "We");
        dictionary.put("amra", "We");
        dictionary.put("Bhat", "rice");
        dictionary.put("bhat", "rice");
        dictionary.put("Roti", "bread");
        dictionary.put("roti", "bread");
        dictionary.put("Khai", "eat");
        dictionary.put("khai", "eat");
        dictionary.put("Banai", "prepare");
        dictionary.put("banai", "prepare");
    }

    public static String translate(String banglaText) {
        // Strip punctuation/quotes first if any remain
        String cleanedText = banglaText.replaceAll("[^a-zA-Z\\s]", "");
        String[] words = cleanedText.split("\\s+");

        StringBuilder translatedSentence = new StringBuilder();

        // Simple word-by-word translation & reordering (Bangla SOV -> English SVO)
        // Words identified:
        String subject = "";
        String object = "";
        String verb = "";

        for (String word : words) {
            String translated = dictionary.getOrDefault(word, word);
            if (translated.equalsIgnoreCase("I") || translated.equalsIgnoreCase("We")) {
                subject = translated;
            } else if (translated.equalsIgnoreCase("eat") || translated.equalsIgnoreCase("prepare")) {
                verb = translated;
            } else if (translated.equalsIgnoreCase("rice") || translated.equalsIgnoreCase("bread")) {
                object = translated;
            } else {
                // Fallback for unmapped words
                if (subject.isEmpty())
                    subject = translated;
                else if (verb.isEmpty())
                    verb = translated;
                else
                    object = translated;
            }
        }

        // Reconstruct in English order: Subject + Verb + Object
        if (!subject.isEmpty())
            translatedSentence.append(subject).append(" ");
        if (!verb.isEmpty())
            translatedSentence.append(verb).append(" ");
        if (!object.isEmpty())
            translatedSentence.append(object);

        String result = translatedSentence.toString().trim();
        return result.isEmpty() ? banglaText : result + ".";
    }
}

// ==========================================
// 4. ADAPTER CLASS
// ==========================================
class BanglaToEnglishAdapter implements ScriptGenerator {
    private final EnglishScriptGenerator englishScriptGenerator;

    public BanglaToEnglishAdapter(EnglishScriptGenerator englishScriptGenerator) {
        this.englishScriptGenerator = englishScriptGenerator;
    }

    @Override
    public String generateScript(String banglaSpeech) {
        // Step 1: Remove quotation marks from Bangla speech
        String rawBanglaText = banglaSpeech.replaceAll("^\"|\"$", "");

        // Step 2: Translate Bangla speech text to English speech text
        String englishSpeech = BanglaToEnglishTranslator.translate(rawBanglaText);

        // Step 3: Delegate script generation to the original English Script Generator
        return englishScriptGenerator.generateScript("\"" + englishSpeech + "\"");
    }
}

// ==========================================
// 5. CLIENT & MAIN PROGRAM
// ==========================================
public class ScriptGeneratorApp {

    // Smart router to detect language and use appropriate processor/adapter
    public static String processAudioInput(String input, EnglishScriptGenerator englishGen,
            BanglaToEnglishAdapter adapter) {
        String cleaned = input.replaceAll("^\"|\"$", "").toLowerCase();

        // Check if input contains specific Bangla key words
        if (cleaned.contains("ami") || cleaned.contains("amra") ||
                cleaned.contains("bhat") || cleaned.contains("roti") ||
                cleaned.contains("khai") || cleaned.contains("banai")) {
            return adapter.generateScript(input);
        } else {
            return englishGen.generateScript(input);
        }
    }

    public static void main(String[] args) {
        EnglishScriptGenerator englishGenerator = new EnglishScriptGenerator();
        BanglaToEnglishAdapter banglaAdapter = new BanglaToEnglishAdapter(englishGenerator);

        // Test cases from specification
        String[] testInputs = {
                "\"Hello world!\"",
                "\"Amra bhat khai.\"",
                "\"The sky is clear today.\"",
                "\"Ami roti banai.\""
        };

        System.out.println("Input\t\t\t\tOutput");
        System.out.println("-------------------------------------------------------");

        for (String input : testInputs) {
            String result = processAudioInput(input, englishGenerator, banglaAdapter);
            System.out.printf("%-28s %s\n", input, result);
        }
    }
}
