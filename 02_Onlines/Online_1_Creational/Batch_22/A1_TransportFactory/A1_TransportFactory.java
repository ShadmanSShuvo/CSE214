// A1: Logistics Management - Factory Method Pattern
// Task: Create Transport objects (Truck/Ship) based on a string input,
// without the client knowing the concrete classes.

// ---------- Product Interface ----------
interface Transport {
    void deliver();
}

// ---------- Concrete Products ----------
class Truck implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering by land in a Truck.");
    }
}

class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering by sea in a Ship.");
    }
}

// Future extension example (not required now, shows extensibility):
class Airplane implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering by air in an Airplane.");
    }
}

// ---------- Creator (Factory) ----------
class TransportFactory {
    // Factory Method: decides which concrete Transport to instantiate
    public static Transport createTransport(String mode) {
        switch (mode) {
            case "Road":
                return new Truck();
            case "Sea":
                return new Ship();
            case "Air":
                return new Airplane();
            default:
                throw new IllegalArgumentException("Unknown transport mode: " + mode);
        }
    }
}

// ---------- Client ----------
public class A1_TransportFactory {
    public static void main(String[] args) {
        Transport t1 = TransportFactory.createTransport("Road");
        t1.deliver();

        Transport t2 = TransportFactory.createTransport("Sea");
        t2.deliver();

        Transport t3 = TransportFactory.createTransport("Air");
        t3.deliver();
    }
}
