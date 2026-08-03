# Design Patterns Exam Identification Guide

## How to Spot, Distinguish & Code Every Pattern

This guide covers all patterns from the slides:
- **Creational:** Factory Method, Abstract Factory, Singleton, Builder
- **Structural:** Adapter, Decorator, Composite, Bridge
- **Behavioral:** Strategy, Template Method, Observer, Mediator, Command, State

*Based on lecture slides by Nafis Tahmid, Lecturer, CSE, BUET*  
[Design pattern playlist](https://youtube.com/playlist?list=PLGhFbq-tNs18QMLuRHmZ1ez_Bm-_jxqUv&si=uXtl5x9qa3wlHZGy)

---

## Contents
1. [HOW TO CLASSIFY A PATTERN (Big Picture)](#1-how-to-classify-a-pattern-big-picture)
2. [CREATIONAL PATTERNS](#2-creational-patterns)
   - [2.1 Factory Method](#21-factory-method)
   - [2.2 Abstract Factory](#22-abstract-factory)
   - [2.3 Singleton](#23-singleton)
   - [2.4 Builder](#24-builder)
3. [STRUCTURAL PATTERNS](#3-structural-patterns)
   - [3.1 Adapter](#31-adapter)
   - [3.2 Decorator](#32-decorator)
   - [3.3 Composite](#33-composite)
   - [3.4 Bridge](#34-bridge)
4. [BEHAVIORAL PATTERNS](#4-behavioral-patterns)
   - [4.1 Strategy](#41-strategy)
   - [4.2 Template Method](#42-template-method)
   - [4.3 Observer](#43-observer)
   - [4.4 Mediator](#44-mediator)
   - [4.5 Command](#45-command)
   - [4.6 State](#46-state)
5. [THE ULTIMATE CONFUSION GUIDE](#5-the-ultimate-confusion-guide)
   - [5.1 Factory Method vs Abstract Factory vs Builder](#51-factory-method-vs-abstract-factory-vs-builder)
   - [5.2 Adapter vs Decorator vs Proxy](#52-adapter-vs-decorator-vs-proxy)
   - [5.3 Observer vs Mediator](#53-observer-vs-mediator)
   - [5.4 Strategy vs Template Method vs State](#54-strategy-vs-template-method-vs-state)
   - [5.5 Command vs Strategy](#55-command-vs-strategy)
6. [QUICK EXAM IDENTIFICATION FLOWCHART](#6-quick-exam-identification-flowchart)

---

## 1 HOW TO CLASSIFY A PATTERN (Big Picture)

Before memorizing individual patterns, use this 3-step filter in the exam:

**Step 1 — Read the scenario and ask:**
* **Is the problem about creating objects? → Creational**
  * *Keyword signals:* “new keyword used too much”, “don’t know which class to instantiate”, “only one instance”, “build step by step”
* **Is the problem about fitting classes/objects together? → Structural**
  * *Keyword signals:* “incompatible interfaces”, “wrap”, “add behavior dynamically”, “tree structure”, “split hierarchy”
* **Is the problem about how objects communicate? → Behavioral**
  * *Keyword signals:* “notify”, “swap algorithm”, “states”, “request as object”, “reduce dependencies between objects”

### Master Cheat Table

| Pattern | Type | One-line exam trigger |
| :--- | :--- | :--- |
| **Factory Method** | Creational | Superclass defines interface, subclasses decide which object to create |
| **Abstract Factory** | Creational | Create families of related objects without specifying concrete classes |
| **Singleton** | Creational | Only one instance must exist globally |
| **Builder** | Creational | Construct complex object step by step (many optional parts) |
| **Adapter** | Structural | Make incompatible interfaces work together |
| **Decorator** | Structural | Add behavior to object at runtime by wrapping |
| **Composite** | Structural | Treat individual objects and groups uniformly (tree) |
| **Bridge** | Structural | Split abstraction from implementation so both evolve independently |
| **Strategy** | Behavioral | Swap algorithm at runtime; same context, different behavior |
| **Template Method** | Behavioral | Skeleton fixed in superclass, specific steps overridden in subclasses |
| **Observer** | Behavioral | One object changes → notify many others automatically |
| **Mediator** | Behavioral | Objects don’t talk directly; all go through a central hub |
| **Command** | Behavioral | Wrap a request in an object; supports undo/queue |
| **State** | Behavioral | Object changes behavior based on its internal state |

---

## 2 CREATIONAL PATTERNS

### 2.1 Factory Method

**What is the exam scenario?** — You have a superclass/framework that needs to create objects, but it doesn’t (and shouldn’t) know which concrete class to instantiate. Subclasses make that decision.

**Exact exam signals:**
- “The main class needs a Document but doesn’t know if it’s PDF or DOCX”
- “Each subclass decides what product to make”
- “The creator is decoupled from the product”
- Inheritance is involved in the creation

**Structure in one sentence** — Creator (abstract class) has `factoryMethod()` that subclasses override to return a specific Product.

```java
// Product interface
interface Document {
    void open();
}

// Concrete Products
class PDFDocument implements Document {
    public void open() { System.out.println("Opening PDF"); }
}
class WordDocument implements Document {
    public void open() { System.out.println("Opening Word Doc"); }
}

// Creator (abstract) - has the factory method
abstract class Application {
    // THE factory method - subclasses must override this
    public abstract Document createDocument(); // <-- this is the pattern

    public void newDocument() {
        Document doc = createDocument(); // doesn't know the type!
        doc.open();
    }
}

// Concrete Creators - each decides which product
class PDFApplication extends Application {
    @Override
    public Document createDocument() { return new PDFDocument(); }
}
class WordApplication extends Application {
    @Override
    public Document createDocument() { return new WordDocument(); }
}

// Client
Application app = new PDFApplication();
app.newDocument(); // Output: Opening PDF
```

**DON’T confuse with Abstract Factory:**
- **Factory Method** = one product, decided by inheritance (subclass overrides method)
- **Abstract Factory** = family of products, decided by composition (inject factory object)
- *Simple rule:* If a class extends something to decide what to create → Factory Method.

---

### 2.2 Abstract Factory

**What is the exam scenario?** — You need to create families of related objects that must be used together, without coupling to their concrete classes.

**Exact exam signals:**
- “The system must work on both Windows and Mac” (UI components as a family)
- “Create matching sets of objects” (Button + Checkbox must both be Windows or both Mac)
- “Switch entire product family at runtime”
- The client is written against an abstract factory and composed with a real one

```java
// Abstract products
interface Button { void render(); }
interface Checkbox { void render(); }

// Concrete products - Windows family
class WinButton implements Button {
    public void render() { System.out.println("Windows Button"); }
}
class WinCheckbox implements Checkbox {
    public void render() { System.out.println("Windows Checkbox"); }
}

// Concrete products - Mac family
class MacButton implements Button {
    public void render() { System.out.println("Mac Button"); }
}
class MacCheckbox implements Checkbox {
    public void render() { System.out.println("Mac Checkbox"); }
}

// Abstract Factory interface - creates a FAMILY
interface GUIFactory {
    Button createButton(); // <-- creates product A
    Checkbox createCheckbox(); // <-- creates product B
}

// Concrete factories
class WinFactory implements GUIFactory {
    public Button createButton() { return new WinButton(); }
    public Checkbox createCheckbox() { return new WinCheckbox(); }
}
class MacFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

// Client - doesn't know which factory it has
class Application {
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) { // factory injected!
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }
    public void render() { button.render(); checkbox.render(); }
}

// Usage: swap entire family by changing factory
Application app = new Application(new WinFactory()); // or MacFactory
app.render();
```

**Abstract Factory vs Factory Method — The Real Difference:**
- **Factory Method:** uses inheritance — you subclass the creator
- **Abstract Factory:** uses composition — you inject a factory object
- Factory Method creates one type of product
- Abstract Factory creates multiple related products (a whole family)

---

### 2.3 Singleton

**What is the exam scenario?** — Exactly one instance of a class must exist globally. Everyone accesses the same object.

**Exact exam signals:**
- “Only one database connection pool should exist”
- “The logger must be shared across the system”
- “Exactly one instance, global access point”

```java
class DatabasePool {
    // The single instance
    private static DatabasePool instance;

    // Private constructor - no one can call new DatabasePool()
    private DatabasePool() {
        System.out.println("Connecting to database...");
    }

    // Global access point
    public static synchronized DatabasePool getInstance() {
        if (instance == null) {
            instance = new DatabasePool(); // created only once
        }
        return instance;
    }

    public void query(String sql) { 
        System.out.println("Running: " + sql);
    }
}

// Usage - always returns the SAME object
DatabasePool pool1 = DatabasePool.getInstance();
DatabasePool pool2 = DatabasePool.getInstance();
System.out.println(pool1 == pool2); // true! same object
```

**Watch out:** The constructor is private. The instance field is static. The `getInstance()` method is static. These three together = Singleton.

---

### 2.4 Builder

**What is the exam scenario?** — Constructing a complex object with many optional parts, step by step. Different “directors” can produce different representations.

**Exact exam signals:**
- “Build a house: first foundation, then walls, then roof”
- “An object has too many constructor parameters”
- “Same construction process should produce different types”
- Director + Builder mentioned

```java
// Product
class House {
    private String foundation, walls, roof, garage;
    // setters...
    public void setFoundation(String f) { this.foundation = f; }
    public void setWalls(String w) { this.walls = w; }
    public void setRoof(String r) { this.roof = r; }
}

// Builder interface
interface HouseBuilder {
    void buildFoundation();
    void buildWalls();
    void buildRoof();
    House getResult();
}

// Concrete Builder
class WoodenHouseBuilder implements HouseBuilder {
    private House house = new House();
    public void buildFoundation() { house.setFoundation("Wood posts"); }
    public void buildWalls() { house.setWalls("Wooden planks"); }
    public void buildRoof() { house.setRoof("Shingle roof"); }
    public House getResult() { return house; }
}

// Director - controls the ORDER of building
class Director {
    private HouseBuilder builder;
    public Director(HouseBuilder builder) { this.builder = builder; }

    public void constructHouse() { // defines the build sequence
        builder.buildFoundation();
        builder.buildWalls();
        builder.buildRoof();
    }
}

// Client
HouseBuilder builder = new WoodenHouseBuilder();
Director director = new Director(builder);
director.constructHouse();
House house = builder.getResult();
```

**Builder vs Abstract Factory:**
- **Builder:** constructs one complex object step by step (focus on process)
- **Abstract Factory:** creates families of different objects in one go (focus on family)
- Builder has a Director that controls the steps; Abstract Factory does not

---

## 3 STRUCTURAL PATTERNS

### 3.1 Adapter

**What is the exam scenario?** — You have an existing class (Adaptee) with a useful interface, but it’s incompatible with what the client expects (Target). The Adapter bridges them.

**Exact exam signals:**
- “We have a legacy/third-party class but its method names don’t match”
- “Make two incompatible interfaces work together”
- “We can’t modify the existing class”
- Like a power plug adapter in real life

```java
// Target interface - what the client expects
interface MediaPlayer {
    void play(String filename);
}

// Adaptee - existing class with incompatible interface
class AdvancedPlayer {
    public void playVLC(String file) { System.out.println("VLC: " + file); }
    public void playMP4(String file) { System.out.println("MP4: " + file); }
}

// Adapter - wraps AdvancedPlayer, implements MediaPlayer
class MediaAdapter implements MediaPlayer {
    private AdvancedPlayer adaptee; // composition (Object Adapter)

    public MediaAdapter() {
        this.adaptee = new AdvancedPlayer();
    }

    @Override
    public void play(String filename) { // translates the call
        if (filename.endsWith(".vlc"))
            adaptee.playVLC(filename); // delegates to adaptee
        else if (filename.endsWith(".mp4"))
            adaptee.playMP4(filename);
    }
}

// Client only knows MediaPlayer - doesn't know about AdvancedPlayer
MediaPlayer player = new MediaAdapter();
player.play("movie.vlc"); // works!
player.play("show.mp4"); // works!
```

**Object Adapter vs Class Adapter:**
- **Object Adapter:** Adapter holds an Adaptee (composition) — works in Java
- **Class Adapter:** Adapter extends both (inheritance) — requires multiple inheritance (C++ only)
- *In Java, always use Object Adapter.*

**Adapter vs Decorator:** Both wrap an object, but:
- **Adapter:** changes the interface (converts method X to method Y)
- **Decorator:** keeps the same interface, adds extra behavior

---

### 3.2 Decorator

**What is the exam scenario?** — You want to add responsibilities to an object at runtime without changing its class or subclassing it. Wrappers stack on top of each other.

**Exact exam signals:**
- “Add encryption then compression to a file stream”
- “A coffee can have milk, sugar, caramel added at runtime”
- “Attach new behaviors without changing the original class”
- “Wrap an object inside another object of the same type”

```java
// Component interface
interface Coffee {
    String getDescription();
    double getCost();
}

// Concrete Component (base object)
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple coffee"; }
    public double getCost() { return 1.0; }
}

// Base Decorator - also implements Coffee (SAME interface!)
abstract class CoffeeDecorator implements Coffee {
    protected Coffee wrappee; // holds the wrapped object

    public CoffeeDecorator(Coffee coffee) {
        this.wrappee = coffee;
    }
    public String getDescription() { return wrappee.getDescription(); }
    public double getCost() { return wrappee.getCost(); }
}

// Concrete Decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + ", Milk"; // adds to description
    }
    @Override
    public double getCost() { return wrappee.getCost() + 0.25; }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) { super(coffee); }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + ", Sugar";
    }
    @Override
    public double getCost() { return wrappee.getCost() + 0.10; }
}

// Client - stacks decorators
Coffee myCoffee = new SimpleCoffee(); // $1.00
myCoffee = new MilkDecorator(myCoffee); // $1.25
myCoffee = new SugarDecorator(myCoffee); // $1.35
System.out.println(myCoffee.getDescription()); // Simple coffee, Milk, Sugar
System.out.println(myCoffee.getCost()); // 1.35
```

**The giveaway structure of Decorator:**
1. Decorator implements the same interface as the component
2. Decorator holds (wraps) a reference to a component
3. Decorators can be stacked on each other
→ If you see `new B(new A(...))`, it’s Decorator.

**Decorator vs Composite:** Both wrap components, but:
- **Decorator:** wraps exactly one component, adds behavior
- **Composite:** manages multiple children, unifies individual + group behavior

---

### 3.3 Composite

**What is the exam scenario?** — You have a tree structure (like a file system, org chart, or GUI hierarchy) and want to treat leaf nodes and container nodes uniformly through the same interface.

**Exact exam signals:**
- “A folder can contain files or other folders”
- “Render all UI elements the same way, whether a button or a panel”
- “Tree structure where you want to treat leaves and composites the same”
- The word “recursive” often appears in the scenario

```java
import java.util.ArrayList;
import java.util.List;

// Component interface - common for both leaf and composite
interface FileSystemItem {
    void display(String indent);
    int getSize();
}

// Leaf - no children
class File implements FileSystemItem {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }
    public void display(String indent) {
        System.out.println(indent + "File: " + name + " (" + size + "KB)");
    }
    public int getSize() { return size; }
}

// Composite - has children (can be Files or other Folders)
class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> children = new ArrayList<>();

    public Folder(String name) { this.name = name; }

    public void add(FileSystemItem item) { children.add(item); }
    public void remove(FileSystemItem item) { children.remove(item); }

    public void display(String indent) {
        System.out.println(indent + "Folder: " + name);
        for (FileSystemItem child : children)
            child.display(indent + "  "); // recursion!
    }
    public int getSize() {
        int total = 0;
        for (FileSystemItem child : children)
            total += child.getSize(); // delegates to children
        return total;
    }
}

// Client treats everything as FileSystemItem - doesn't care if leaf or folder
Folder root = new Folder("root");
root.add(new File("readme.txt", 5));
Folder src = new Folder("src");
src.add(new File("Main.java", 20));
src.add(new File("Utils.java", 15));
root.add(src);

root.display(""); // recursively prints entire tree
System.out.println("Total: " + root.getSize() + "KB"); // 40
```

---

### 3.4 Bridge

**What is the exam scenario?** — A class has two dimensions of variation (e.g., shape + color, device + remote control). Without Bridge, you’d get an explosion of subclasses. Bridge splits them into two independent hierarchies.

**Exact exam signals:**
- “We have shapes (circle, square) and renderers (vector, raster) and all combinations are needed”
- “Avoid class explosion when two hierarchies must vary independently”
- “Split a large class into abstraction and implementation”
- “Switch implementations at runtime”

```java
// Implementation interface (one axis of variation)
interface Renderer {
    void renderCircle(float radius);
}

// Concrete Implementations
class VectorRenderer implements Renderer {
    public void renderCircle(float r) {
        System.out.println("Drawing vector circle of radius " + r);
    }
}
class RasterRenderer implements Renderer {
    public void renderCircle(float r) {
        System.out.println("Drawing raster circle of radius " + r);
    }
}

// Abstraction (other axis of variation)
abstract class Shape {
    protected Renderer renderer; // THE BRIDGE - holds the implementation

    public Shape(Renderer renderer) { this.renderer = renderer; }
    public abstract void draw();
}

// Refined Abstractions
class Circle extends Shape {
    private float radius;
    public Circle(Renderer renderer, float radius) {
        super(renderer);
        this.radius = radius;
    }
    public void draw() {
        renderer.renderCircle(radius); // delegates to implementation
    }
}

// Client
Shape circle1 = new Circle(new VectorRenderer(), 5);
Shape circle2 = new Circle(new RasterRenderer(), 5);
circle1.draw(); // Drawing vector circle of radius 5.0
circle2.draw(); // Drawing raster circle of radius 5.0
// Add new shape OR new renderer independently - no explosion!
```

**Bridge vs Strategy:** Both have an object holding a reference to an interface. But:
- **Bridge:** about structural decomposition — separating two class hierarchies
- **Strategy:** about behavioral swap — swapping algorithms at runtime
- *Bridge is a design decision upfront; Strategy is chosen at runtime by the client.*

---

## 4 BEHAVIORAL PATTERNS

### 4.1 Strategy

**What is the exam scenario?** — You have a family of algorithms (or behaviors) that need to be interchangeable at runtime. The client picks which algorithm to use.

**Exact exam signals (from slides):**
- “Payment method” — CreditCard, PayPal, Bitcoin are interchangeable strategies
- “Swap sorting algorithm at runtime”
- “The algorithm should vary independently from the client”
- Context holds a strategy reference; client calls `setStrategy()`

```java
// Strategy interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete Strategies (the interchangeable algorithms)
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    public CreditCardPayment(String card) { this.cardNumber = card; }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via Credit Card " + cardNumber);
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    public PayPalPayment(String email) { this.email = email; }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via PayPal (" + email + ")");
    }
}

// Context - holds a reference to the current strategy
class ShoppingCart {
    private PaymentStrategy strategy; // the strategy field

    public void setStrategy(PaymentStrategy strategy) { // swap at runtime!
        this.strategy = strategy;
    }

    public void checkout(double amount) {
        strategy.pay(amount); // delegates to the strategy
    }
}

// Client chooses the strategy
ShoppingCart cart = new ShoppingCart();
cart.setStrategy(new CreditCardPayment("1234-5678"));
cart.checkout(100.0); // Paid $100.0 via Credit Card

cart.setStrategy(new PayPalPayment("user@email.com")); // SWAP!
cart.checkout(50.0); // Paid $50.0 via PayPal
```

**Strategy vs State: This is the most commonly confused pair!**
- **Strategy:** The client explicitly swaps strategies. Strategies are usually independent (don’t know about each other).
- **State:** The object itself (or the state) decides the transition. States know about other states and trigger transitions.

*Think of it this way:*
- **Strategy** = choosing a route on Google Maps (you pick)
- **State** = a traffic light (it changes itself based on time)

**Strategy vs Template Method:**
- **Strategy:** algorithm is swapped entirely via composition (hold a reference)
- **Template Method:** algorithm skeleton is fixed in a superclass, only some steps are overridden via inheritance

---

### 4.2 Template Method

**What is the exam scenario?** — A skeleton/outline of an algorithm is fixed in a base class. Subclasses can override specific steps but cannot change the overall structure.

**Exact exam signals (from slides):**
- “Game’s loading screen” — all games load assets, initialize, then start, but each game’s specific steps differ
- “The overall sequence is fixed but individual steps vary by subclass”
- `templateMethod()` is declared `final` (cannot be overridden)
- Involves inheritance, not composition

```java
// Abstract class with the template method
abstract class Game {
    // THE TEMPLATE METHOD - final! Cannot be overridden
    public final void play() {
        initialize(); // step 1
        startPlay();  // step 2 - subclass defines this
        endPlay();    // step 3 - subclass defines this
    }

    // Steps that subclasses must implement
    protected abstract void initialize();
    protected abstract void startPlay();
    protected abstract void endPlay();
}

// Concrete class 1 - overrides the STEPS, not the template
class Chess extends Game {
    protected void initialize() {
        System.out.println("Chess: Setting up board and pieces");
    }
    protected void startPlay() {
        System.out.println("Chess: White moves first");
    }
    protected void endPlay() {
        System.out.println("Chess: Checkmate! Game over.");
    }
}

// Concrete class 2
class Cricket extends Game {
    protected void initialize() {
        System.out.println("Cricket: Toss the coin");
    }
    protected void startPlay() {
        System.out.println("Cricket: Batting begins");
    }
    protected void endPlay() {
        System.out.println("Cricket: Match complete");
    }
}

// Client - calls template method, which runs the skeleton
Game chess = new Chess();
chess.play();
// Chess: Setting up board and pieces
// Chess: White moves first
// Chess: Checkmate! Game over.
```

**Key identifier:** the `final` keyword.  
The template method is declared `final` so subclasses cannot change the order. They can only fill in the steps.

---

### 4.3 Observer

**What is the exam scenario?** — One object (Publisher/Subject) changes state, and multiple other objects (Subscribers/Observers) need to be notified automatically.

**Exact exam signals (from slides):**
- “A store sends emails to customers/subscribers when a new product arrives” (exactly as in slides)
- “When X changes, Y and Z must update automatically”
- “subscribe/unsubscribe mechanism”
- Publisher = Subject; Subscriber = Observer (as noted in slides)

```java
import java.util.ArrayList;
import java.util.List;

// Subscriber interface
interface Subscriber {
    void update(String event);
}

// Publisher - maintains subscriber list
class Store {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String latestProduct;

    // Subscribe / Unsubscribe
    public void subscribe(Subscriber s) { subscribers.add(s); }
    public void unsubscribe(Subscriber s) { subscribers.remove(s); }

    // Notify all subscribers
    private void notifySubscribers() {
        for (Subscriber s : subscribers)
            s.update(latestProduct); // calls update on each
    }

    // Business logic - triggers notification
    public void newProductArrived(String product) {
        this.latestProduct = product;
        notifySubscribers(); // automatically notifies everyone
    }
}

// Concrete Subscribers
class EmailSubscriber implements Subscriber {
    private String email;
    public EmailSubscriber(String email) { this.email = email; }

    @Override
    public void update(String product) {
        System.out.println("Email to " + email + ": New product: " + product);
    }
}

class SMSSubscriber implements Subscriber {
    private String phone;
    public SMSSubscriber(String phone) { this.phone = phone; }

    @Override
    public void update(String product) {
        System.out.println("SMS to " + phone + ": New product: " + product);
    }
}

// Client
Store store = new Store();
store.subscribe(new EmailSubscriber("alice@email.com"));
store.subscribe(new SMSSubscriber("+8801700000000"));

store.newProductArrived("iPhone 17");
// Email to alice@email.com: New product: iPhone 17
// SMS to +8801700000000: New product: iPhone 17
```

**Observer vs Mediator:** Both deal with object communication, but:
- **Observer:** One-to-many, dynamic relationship. Observers can subscribe/unsubscribe freely. Publisher doesn’t know who is listening.
- **Mediator:** Many-to-many, all communication goes through the hub. Components know the mediator but not each other. The mediator has complex coordination logic.
- *From slides:* “Observer establishes dynamic one-way one-to-many connection; Mediator eliminates mutual dependencies via a single mediator object.”

---

### 4.4 Mediator

**What is the exam scenario?** — Many objects need to communicate, but direct connections create chaotic dependencies. A mediator centralizes all communication so objects only talk to the mediator, not to each other.

**Exact exam signals (from slides):**
- “Basically the use of buttons” (UI components like buttons, checkboxes coordinating through a dialog/mediator)
- “Too many dependencies between components”
- “Objects should not know about each other”
- “Air traffic control” — planes talk to tower, not to each other
- *From slides:* “Components must not be aware of other components”

```java
// Mediator interface
interface DialogMediator {
    void notify(Component sender, String event);
}

// Components - each knows the mediator, NOT other components
abstract class Component {
    protected DialogMediator mediator;
    public Component(DialogMediator mediator) { this.mediator = mediator; }
    public void click() { mediator.notify(this, "click"); }
}

class Button extends Component {
    private String label;
    public Button(DialogMediator m, String label) {
        super(m);
        this.label = label;
    }
    public String getLabel() { return label; }
}

class Checkbox extends Component {
    private boolean checked = false;
    public Checkbox(DialogMediator m) { super(m); }
    public void toggle() {
        checked = !checked;
        mediator.notify(this, "check"); // tells mediator, NOT other components
    }
    public boolean isChecked() { return checked; }
}

class TextBox extends Component {
    private boolean enabled = true;
    public TextBox(DialogMediator m) { super(m); }
    public void setEnabled(boolean e) { this.enabled = e; }
    public boolean isEnabled() { return enabled; }
}

// Concrete Mediator - has ALL the coordination logic
class LoginDialog implements DialogMediator {
    private Checkbox rememberMe;
    private TextBox password;
    private Button login;

    // Wires up components
    public void setComponents(Checkbox cb, TextBox tb, Button btn) {
        this.rememberMe = cb;
        this.password = tb;
        this.login = btn;
    }

    @Override
    public void notify(Component sender, String event) {
        if (sender == rememberMe && event.equals("check")) {
            // When "Remember Me" is checked, disable password field
            password.setEnabled(!rememberMe.isChecked());
            System.out.println("Password field " + 
                (rememberMe.isChecked() ? "disabled" : "enabled"));
        }
        if (sender == login && event.equals("click")) {
            System.out.println("Login button clicked - validating...");
        }
    }
}

// Client
LoginDialog dialog = new LoginDialog();
Checkbox cb = new Checkbox(dialog);
TextBox tb = new TextBox(dialog);
Button btn = new Button(dialog, "Login");
dialog.setComponents(cb, tb, btn);

cb.toggle(); // triggers mediator logic, which disables textbox
btn.click(); // triggers mediator logic
```

---

### 4.5 Command

**What is the exam scenario?** — A request is turned into an object. This lets you queue requests, log them, support undo/redo, and decouple the sender from the receiver.

**Exact exam signals:**
- “The system needs to support Undo/Redo”
- “Queue or schedule requests for later execution”
- “Buttons/toolbar items should trigger operations without knowing how they work”
- “Passing a request as an argument” (from slides)
- 5 players: Client, Invoker, Command interface, Concrete Command, Receiver

```java
import java.util.Stack;

// Command interface
interface Command {
    void execute();
    void undo(); // supports undo!
}

// Receiver - the actual business logic object
class TextEditor {
    private StringBuilder text = new StringBuilder();

    public void insertText(String s) {
        text.append(s);
        System.out.println("Text: " + text);
    }
    public void deleteText(int len) {
        if (len <= text.length())
            text.delete(text.length() - len, text.length());
        System.out.println("Text: " + text);
    }
    public String getText() { return text.toString(); }
}

// Concrete Command 1
class InsertCommand implements Command {
    private TextEditor receiver;
    private String textToInsert;

    public InsertCommand(TextEditor editor, String text) {
        this.receiver = editor;
        this.textToInsert = text;
    }
    public void execute() { receiver.insertText(textToInsert); }
    public void undo() { receiver.deleteText(textToInsert.length()); }
}

// Invoker - stores and fires commands (doesn't know the receiver!)
class CommandHistory {
    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command cmd) {
        cmd.execute();
        history.push(cmd); // store for undo
    }

    public void undoLast() {
        if (!history.isEmpty())
            history.pop().undo();
    }
}

// Client - creates commands, wires receiver + invoker
TextEditor editor = new TextEditor();
CommandHistory invoker = new CommandHistory();

invoker.executeCommand(new InsertCommand(editor, "Hello "));
invoker.executeCommand(new InsertCommand(editor, "World"));
// Text: Hello 
// Text: Hello World

invoker.undoLast(); // removes "World"
// Text: Hello 
```

**Command vs Strategy:**
Both encapsulate behavior in an object, but:
- **Command:** encapsulates a specific request/action (what to do), supports undo, queuing
- **Strategy:** encapsulates an interchangeable algorithm (how to do something), no undo concept
- *“Copy” button → Command. Sorting algorithm → Strategy.*

---

### 4.6 State

**What is the exam scenario?** — An object changes its behavior based on internal state. Instead of giant if/switch blocks, each state is encapsulated in its own class.

**Exact exam signals:**
- “A vending machine behaves differently when it has no quarters vs. has quarters”
- “An order is Pending, Shipped, Delivered — each state has different behavior”
- “The object appears to change its class”
- “If the transitions are dynamic, put them in the state classes”

```java
// State interface
interface TrafficLightState {
    void handle(TrafficLight context);
    String getColor();
}

// Concrete States - each encapsulates behavior + decides next state
class RedState implements TrafficLightState {
    public void handle(TrafficLight context) {
        System.out.println("RED - Stop!");
        context.setState(new GreenState()); // decides next state!
    }
    public String getColor() { return "RED"; }
}

class GreenState implements TrafficLightState {
    public void handle(TrafficLight context) {
        System.out.println("GREEN - Go!");
        context.setState(new YellowState());
    }
    public String getColor() { return "GREEN"; }
}

class YellowState implements TrafficLightState {
    public void handle(TrafficLight context) {
        System.out.println("YELLOW - Caution!");
        context.setState(new RedState());
    }
    public String getColor() { return "YELLOW"; }
}

// Context - delegates all state-specific behavior to current state
class TrafficLight {
    private TrafficLightState state;

    public TrafficLight() {
        this.state = new RedState(); // initial state
    }

    public void setState(TrafficLightState state) { this.state = state; }
    public TrafficLightState getState() { return state; }

    // Delegates to current state
    public void change() { state.handle(this); }
}

// Client
TrafficLight light = new TrafficLight();
light.change(); // RED - Stop! -> transitions to Green
light.change(); // GREEN - Go! -> transitions to Yellow
light.change(); // YELLOW - Caution! -> transitions to Red
```

**State vs Strategy — The Most Confusing Pair:**

| Feature | Strategy | State |
| :--- | :--- | :--- |
| **Who swaps?** | Client sets the strategy | Context or State itself transitions |
| **Awareness** | Strategies are independent | States know about each other |
| **Purpose** | Choose *how* to do something | React to *what is happening internally* |
| **Example** | Payment method | Traffic light phases |

---

## 5 THE ULTIMATE CONFUSION GUIDE

### 5.1 Factory Method vs Abstract Factory vs Builder

| Feature | Factory Method | Abstract Factory | Builder |
| :--- | :--- | :--- | :--- |
| **Output** | One product type | Family of products | One complex product |
| **Mechanism** | Inheritance | Composition | Step-by-step process |
| **Who decides?** | Subclass | Injected factory object | Director |
| **Key class** | Abstract Creator | Abstract Factory interface | Director class |
| **Giveaway** | `createX()` in abstract class | Multiple `createX()` methods in interface | `buildStep()` methods + `getResult()` |

---

### 5.2 Adapter vs Decorator vs Proxy

| Feature | Adapter | Decorator |
| :--- | :--- | :--- |
| **Purpose** | Convert interface | Add behavior |
| **Interface** | Changes it (Target $
eq$ Adaptee) | Keeps same interface |
| **Wrapping** | Wraps one object | Can stack multiple wrappers |
| **Giveaway** | Two different interfaces involved | `new B(new A(...))` nesting |

---

### 5.3 Observer vs Mediator

| Feature | Observer | Mediator |
| :--- | :--- | :--- |
| **Relationship** | One publisher, many subscribers | Many components, one hub |
| **Direction** | One-way (publisher $ightarrow$ subscriber) | All directions through mediator |
| **Coupling** | Publisher doesn’t know subscribers | Mediator knows all components |
| **Dynamic?** | Yes, subscribe/unsubscribe freely | Usually fixed wiring |
| **Giveaway** | `subscribe()`, `notify()` methods | `notify(sender, event)` in mediator |

---

### 5.4 Strategy vs Template Method vs State

| Feature | Strategy | Template Method | State |
| :--- | :--- | :--- | :--- |
| **Mechanism** | Composition | Inheritance | Composition |
| **Who swaps?** | Client | N/A (subclass chosen at creation) | Object/state itself |
| **Fixed part?** | Nothing fixed | Skeleton is fixed (`final`) | State machine logic |
| **Giveaway** | `setStrategy()` method | `final templateMethod()` | States know other states |

---

### 5.5 Command vs Strategy

| Feature | Command | Strategy |
| :--- | :--- | :--- |
| **Purpose** | Encapsulate a request/action | Encapsulate an algorithm |
| **Undo?** | Yes, supports undo | No |
| **Queue?** | Yes | No |
| **Key interface** | `execute()` + `undo()` | Single method (e.g., `pay()`) |
| **Example** | Copy, Paste, Undo | PaymentMethod, SortAlgorithm |

---

## 6 QUICK EXAM IDENTIFICATION FLOWCHART

**Step 1: Identify the category**

1. **Does the scenario involve creating objects?**
   - **Yes → Creational.** Ask:
     - Only one instance needed? → **Singleton**
     - Build complex object step by step? → **Builder**
     - Create family of related objects? → **Abstract Factory**
     - Subclass decides which object to create? → **Factory Method**

2. **Does the scenario involve object structure/composition?**
   - **Yes → Structural.** Ask:
     - Incompatible interfaces need to work together? → **Adapter**
     - Add behavior at runtime by wrapping? → **Decorator**
     - Tree structure, treat leaf/group uniformly? → **Composite**
     - Two hierarchies need to vary independently? → **Bridge**

3. **Does the scenario involve object communication/behavior?**
   - **Yes → Behavioral.** Ask:
     - Swap algorithm at runtime? → **Strategy**
     - Skeleton fixed, steps overridden? → **Template Method**
     - One changes, many are notified? → **Observer**
     - All talk through a central hub? → **Mediator**
     - Request wrapped as object (undo/queue)? → **Command**
     - Behavior changes with internal state? → **State**

---

### Last-resort memory tricks for the exam:
- **Strategy** → payment method (you choose how to pay)
- **Observer** → store newsletter (you subscribe, get notified)
- **Template Method** → game loading screen (all games do same steps, different content)
- **Mediator** → buttons on a dialog (components don’t talk directly)
- **Command** → Ctrl+Z (you can undo a command)
- **State** → traffic light (changes itself automatically)
- **Singleton** → one president (only one instance)
- **Adapter** → power plug adapter (incompatible interfaces)
- **Decorator** → coffee add-ons (stack milk, sugar, caramel)
- **Composite** → file system (folder contains files or folders)
- **Builder** → building a house step by step
- **Abstract Factory** → Windows vs Mac UI kit (whole family)
