// A2: Computer Model System - Abstract Factory Pattern
// Task: Two independent product families (Processor, Display), each with
// two variants (Xeon/ARM, IPS/OLED), assembled consistently per model.

// ---------- Abstract Products ----------
interface Processor {
    String getDescription();
}

interface Display {
    String getDescription();
}

// ---------- Concrete Products: Processor family ----------
class IntelXeonProcessor implements Processor {
    @Override
    public String getDescription() {
        return "Intel Xeon Processor";
    }
}

class ArmProcessor implements Processor {
    @Override
    public String getDescription() {
        return "ARM Processor";
    }
}

// ---------- Concrete Products: Display family ----------
class IpsDisplay implements Display {
    @Override
    public String getDescription() {
        return "IPS Display";
    }
}

class OledDisplay implements Display {
    @Override
    public String getDescription() {
        return "OLED Display";
    }
}

// ---------- Abstract Factory ----------
interface ComputerPartsFactory {
    Processor createProcessor();
    Display createDisplay();
}

// ---------- Concrete Factory: WorkPro ----------
class WorkProPartsFactory implements ComputerPartsFactory {
    @Override
    public Processor createProcessor() {
        return new IntelXeonProcessor();
    }

    @Override
    public Display createDisplay() {
        return new IpsDisplay();
    }
}

// ---------- Concrete Factory: LiteMax ----------
class LiteMaxPartsFactory implements ComputerPartsFactory {
    @Override
    public Processor createProcessor() {
        return new ArmProcessor();
    }

    @Override
    public Display createDisplay() {
        return new OledDisplay();
    }
}

// ---------- Computer (assembled product) ----------
class Computer {
    private String modelName;
    private Processor processor;
    private Display display;

    public Computer(String modelName, ComputerPartsFactory factory) {
        this.modelName = modelName;
        this.processor = factory.createProcessor();
        this.display = factory.createDisplay();
    }

    public void printDescription() {
        System.out.println("Model: " + modelName);
        System.out.println("  Processor: " + processor.getDescription());
        System.out.println("  Display: " + display.getDescription());
    }
}

// ---------- Client ----------
public class A2_ComputerAbstractFactory {

    public static Computer createComputer(String modelChoice) {
        ComputerPartsFactory factory;
        String modelName;

        switch (modelChoice) {
            case "WorkPro":
                factory = new WorkProPartsFactory();
                modelName = "WorkPro";
                break;
            case "LiteMax":
                factory = new LiteMaxPartsFactory();
                modelName = "LiteMax";
                break;
            default:
                throw new IllegalArgumentException("Unknown model: " + modelChoice);
        }

        return new Computer(modelName, factory);
    }

    public static void main(String[] args) {
        Computer workPro = createComputer("WorkPro");
        workPro.printDescription();

        Computer liteMax = createComputer("LiteMax");
        liteMax.printDescription();
    }
}
