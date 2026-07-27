// Implementation interface (one axis of variation)
interface Renderer {
    void renderCircle(float radius);
}

// Concrete Implementations
class VectorRenderer implements Renderer {

    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing vector circle of radius " + radius);
    }
}

class RasterRenderer implements Renderer {

    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing raster circle of radius " + radius);
    }
}

// Abstraction (other axis of variation)
abstract class Shape {

    protected Renderer renderer;

    public Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void draw();
}

// Refined Abstraction
class Circle extends Shape {

    private float radius;

    public Circle(Renderer renderer, float radius) {
        super(renderer);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius); // delegates to implementation
    }
}

// Client
public class BridgeDemo {

    public static void main(String[] args) {

        Shape circle1 = new Circle(new VectorRenderer(), 5);
        Shape circle2 = new Circle(new RasterRenderer(), 5);

        circle1.draw();
        circle2.draw();
    }
}
