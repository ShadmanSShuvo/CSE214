
// Implementor: transport technology
interface TransportMethod {
    void dispatch(String orderId);
}

class BikeCourier implements TransportMethod {
    public void dispatch(String orderId) {
        System.out.println("Order " + orderId + " dispatched via Bike");
    }
}

class VanDelivery implements TransportMethod {
    public void dispatch(String orderId) {
        System.out.println("Order " + orderId + " dispatched via Van");
    }
}

class DroneDelivery implements TransportMethod {
    public void dispatch(String orderId) {
        System.out.println("Order " + orderId + " dispatched via Drone (safety checks passed)");
    }
}

class RobotDelivery implements TransportMethod {
    public void dispatch(String orderId) {
        System.out.println("Order " + orderId + " dispatched via Robot");
    }
}

// Abstraction: delivery policy
abstract class DeliveryType {
    protected TransportMethod transport;

    DeliveryType(TransportMethod transport) {
        this.transport = transport;
    }

    abstract void deliver(String orderId);
}

class StandardDelivery extends DeliveryType {
    StandardDelivery(TransportMethod t) {
        super(t);
    }

    void deliver(String orderId) {
        System.out.println("Standard Delivery (within 24h) for " + orderId);
        transport.dispatch(orderId);
    }
}

class ExpressDelivery extends DeliveryType {
    ExpressDelivery(TransportMethod t) {
        super(t);
    }

    void deliver(String orderId) {
        System.out.println("Express Delivery (within 4h) for " + orderId);
        transport.dispatch(orderId);
    }
}

class ScheduledDelivery extends DeliveryType {
    ScheduledDelivery(TransportMethod t) {
        super(t);
    }

    void deliver(String orderId) {
        System.out.println("Scheduled Delivery for " + orderId);
        transport.dispatch(orderId);
    }
}

// Usage
public class C2Bridge {
    public static void main(String[] args) {
        DeliveryType d1 = new ExpressDelivery(new DroneDelivery());
        d1.deliver("ORD123");

        DeliveryType d2 = new StandardDelivery(new BikeCourier());
        d2.deliver("ORD124");
        // New transport (Robot) or new delivery policy can be added
        // independently without touching the other hierarchy.
    }
}
