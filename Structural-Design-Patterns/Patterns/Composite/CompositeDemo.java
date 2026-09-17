import java.util.ArrayList;
import java.util.List;

// Component interface - common for both leaf and composite
interface FileSystemItem {
    void display(String indent);

    int getSize();
}

// Leaf - no children
class File implements FileSystemItem {
    private final String name;
    private final int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "File: " + name + " (" + size + " KB)");
    }

    @Override
    public int getSize() {
        return size;
    }
}

// Composite - has children (can be Files or other Folders)
class Folder implements FileSystemItem {
    private final String name;
    private final List<FileSystemItem> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemItem item) {
        children.add(item);
    }

    public void remove(FileSystemItem item) {
        children.remove(item);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Folder: " + name);

        for (FileSystemItem child : children) {
            child.display(indent + "    "); // recursion
        }
    }

    @Override
    public int getSize() {
        int total = 0;

        for (FileSystemItem child : children) {
            total += child.getSize(); // delegates to children
        }

        return total;
    }
}

// Client
public class CompositeDemo {
    public static void main(String[] args) {

        Folder root = new Folder("root");

        root.add(new File("readme.txt", 5));

        Folder src = new Folder("src");
        src.add(new File("Main.java", 20));
        src.add(new File("Utils.java", 15));

        root.add(src);

        root.display("");

        System.out.println("Total: " + root.getSize() + " KB");
    }
}
