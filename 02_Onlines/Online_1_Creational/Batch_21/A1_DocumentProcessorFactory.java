// A1: Document Editor - Factory Method Pattern
// Task: Recognize file type from file name and use the appropriate
// DocumentProcessor to load and save it.

// ---------- Product Interface ----------
interface DocumentProcessor {
    void loadDocument(String fileName);
    void saveDocument(String fileName);
}

// ---------- Concrete Products ----------
class DocxProcessor implements DocumentProcessor {
    @Override
    public void loadDocument(String fileName) {
        System.out.println("Loading DOCX document: " + fileName);
    }

    @Override
    public void saveDocument(String fileName) {
        System.out.println("Saving DOCX document: " + fileName);
    }
}

class PdfProcessor implements DocumentProcessor {
    @Override
    public void loadDocument(String fileName) {
        System.out.println("Loading PDF document: " + fileName);
    }

    @Override
    public void saveDocument(String fileName) {
        System.out.println("Saving PDF document: " + fileName);
    }
}

class TxtProcessor implements DocumentProcessor {
    @Override
    public void loadDocument(String fileName) {
        System.out.println("Loading TXT document: " + fileName);
    }

    @Override
    public void saveDocument(String fileName) {
        System.out.println("Saving TXT document: " + fileName);
    }
}

// ---------- Creator (Factory) ----------
class DocumentProcessorFactory {
    public static DocumentProcessor createProcessor(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "docx":
                return new DocxProcessor();
            case "pdf":
                return new PdfProcessor();
            case "txt":
                return new TxtProcessor();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + extension);
        }
    }
}

// ---------- Client ----------
public class A1_DocumentProcessorFactory {
    public static void main(String[] args) {
        String[] files = { "report.docx", "invoice.pdf", "notes.txt" };

        for (String fileName : files) {
            DocumentProcessor processor = DocumentProcessorFactory.createProcessor(fileName);
            processor.loadDocument(fileName);
            processor.saveDocument(fileName);
        }
    }
}
