// C2: Document Generation System - Abstract Factory Pattern
// Task: Two product families (Letter, Resume), each with two style variants
// (Formal, Informal). Client selects a mode, then creates letters/resumes
// consistent with that mode.

// ---------- Abstract Products ----------
interface Letter {
    String getStyle();
}

interface Resume {
    String getStyle();
}

// ---------- Concrete Products: Formal ----------
class FormalLetter implements Letter {
    @Override
    public String getStyle() {
        return "Formal Letter (professional tone, structured layout)";
    }
}

class FormalResume implements Resume {
    @Override
    public String getStyle() {
        return "Formal Resume (professional tone, structured layout)";
    }
}

// ---------- Concrete Products: Informal ----------
class InformalLetter implements Letter {
    @Override
    public String getStyle() {
        return "Informal Letter (casual tone, relaxed layout)";
    }
}

class InformalResume implements Resume {
    @Override
    public String getStyle() {
        return "Informal Resume (casual tone, relaxed layout)";
    }
}

// ---------- Abstract Factory ----------
interface DocumentCreator {
    Letter createLetter();
    Resume createResume();
}

// ---------- Concrete Factory: Formal mode ----------
class FormalDocumentCreator implements DocumentCreator {
    @Override
    public Letter createLetter() {
        return new FormalLetter();
    }

    @Override
    public Resume createResume() {
        return new FormalResume();
    }
}

// ---------- Concrete Factory: Informal mode ----------
class InformalDocumentCreator implements DocumentCreator {
    @Override
    public Letter createLetter() {
        return new InformalLetter();
    }

    @Override
    public Resume createResume() {
        return new InformalResume();
    }
}

// ---------- Client ----------
public class C2_DocumentAbstractFactory {

    public static DocumentCreator getDocumentCreator(String mode) {
        switch (mode) {
            case "Formal":
                return new FormalDocumentCreator();
            case "Informal":
                return new InformalDocumentCreator();
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }

    public static void main(String[] args) {
        DocumentCreator formalCreator = getDocumentCreator("Formal");
        Letter formalLetter = formalCreator.createLetter();
        Resume formalResume = formalCreator.createResume();
        System.out.println(formalLetter.getStyle());
        System.out.println(formalResume.getStyle());

        DocumentCreator informalCreator = getDocumentCreator("Informal");
        Letter informalLetter = informalCreator.createLetter();
        Resume informalResume = informalCreator.createResume();
        System.out.println(informalLetter.getStyle());
        System.out.println(informalResume.getStyle());
    }
}
