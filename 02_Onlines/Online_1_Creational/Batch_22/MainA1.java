// factory method / simple factory pattern
// Common interface for all transport modes
interface Transport {
    void deliver();
}

// Concrete implementation: Truck
class Truck implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering by land in a truck.");
    }
}

// Concrete implementation: Ship
class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering by sea in a cargo ship.");
    }
}

// Transport Factory to handle object creation
class TransportFactory {
    public static Transport createTransport(String mode) {
        if (mode == null) {
            return null;
        }
        if (mode.equalsIgnoreCase("Road")) {
            return new Truck();
        } else if (mode.equalsIgnoreCase("Sea")) {
            return new Ship();
        }
        // Future extensions like "Air" or "Train" can be added here easily
        throw new IllegalArgumentException("Unknown delivery mode: " + mode);
    }
}

// Client application
public class MainA1 {
    public static void main(String[] args) {
        // Client interacts only via the Transport interface and the Factory
        Transport roadTransport = TransportFactory.createTransport("Road");
        roadTransport.deliver();

        Transport seaTransport = TransportFactory.createTransport("Sea");
        seaTransport.deliver();
    }
}