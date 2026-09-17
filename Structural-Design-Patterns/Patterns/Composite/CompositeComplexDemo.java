import java.util.*;

// ======================================================
// Component
// ======================================================
interface OrganizationUnit {
    String getName();

    double getSalaryCost();

    void printStructure(String indent);
}

// ======================================================
// Leaf Base Class
// ======================================================
abstract class Employee implements OrganizationUnit {

    protected String name;
    protected double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getSalaryCost() {
        return salary;
    }

    @Override
    public void printStructure(String indent) {
        System.out.printf("%s- %s (%s) : $%.2f%n",
                indent,
                name,
                getClass().getSimpleName(),
                salary);
    }
}

// ======================================================
// Concrete Leaves
// ======================================================
class Developer extends Employee {

    private final String primaryLanguage;

    public Developer(String name, double salary, String language) {
        super(name, salary);
        this.primaryLanguage = language;
    }

    @Override
    public void printStructure(String indent) {
        System.out.printf(
                "%s- %s [Developer | %s] : $%.2f%n",
                indent,
                name,
                primaryLanguage,
                salary);
    }
}

class Designer extends Employee {

    private final String tool;

    public Designer(String name, double salary, String tool) {
        super(name, salary);
        this.tool = tool;
    }

    @Override
    public void printStructure(String indent) {
        System.out.printf(
                "%s- %s [Designer | %s] : $%.2f%n",
                indent,
                name,
                tool,
                salary);
    }
}

class QAEngineer extends Employee {

    public QAEngineer(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void printStructure(String indent) {
        System.out.printf(
                "%s- %s [QA Engineer] : $%.2f%n",
                indent,
                name,
                salary);
    }
}

class Manager extends Employee {

    private final double yearlyBonus;

    public Manager(String name, double salary, double yearlyBonus) {
        super(name, salary);
        this.yearlyBonus = yearlyBonus;
    }

    @Override
    public double getSalaryCost() {
        return salary + yearlyBonus;
    }

    @Override
    public void printStructure(String indent) {
        System.out.printf(
                "%s- %s [Manager] : Base=%.2f Bonus=%.2f Total=%.2f%n",
                indent,
                name,
                salary,
                yearlyBonus,
                getSalaryCost());
    }
}

// ======================================================
// Composite
// ======================================================
class Department implements OrganizationUnit {

    private final String name;

    private final List<OrganizationUnit> children = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void add(OrganizationUnit unit) {
        children.add(unit);
    }

    public void remove(OrganizationUnit unit) {
        children.remove(unit);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getSalaryCost() {

        double total = 0;

        for (OrganizationUnit unit : children)
            total += unit.getSalaryCost();

        return total;
    }

    @Override
    public void printStructure(String indent) {

        System.out.println(indent + "+ Department: " + name);

        for (OrganizationUnit unit : children)
            unit.printStructure(indent + "    ");

        System.out.printf(
                "%sTotal Cost of %s = $%.2f%n",
                indent,
                name,
                getSalaryCost());

        System.out.println();
    }
}

// ======================================================
// Client
// ======================================================
public class CompositeComplexDemo {

    public static void main(String[] args) {

        // --------------------------------------------------
        // Employees
        // --------------------------------------------------

        Developer dev1 = new Developer("Alice", 6000, "Java");

        Developer dev2 = new Developer("Bob", 6500, "Python");

        Developer dev3 = new Developer("Charlie", 6200, "Go");

        Designer designer = new Designer("David", 5000, "Figma");

        QAEngineer qa1 = new QAEngineer("Eva", 4200);

        QAEngineer qa2 = new QAEngineer("Frank", 4300);

        Manager engManager = new Manager("Grace", 9000, 3000);

        Manager designManager = new Manager("Helen", 8500, 2500);

        Manager cto = new Manager("Ian", 15000, 8000);

        // --------------------------------------------------
        // Engineering Department
        // --------------------------------------------------

        Department backend = new Department("Backend Team");

        backend.add(dev1);
        backend.add(dev2);

        Department platform = new Department("Platform Team");

        platform.add(dev3);
        platform.add(qa1);

        Department engineering = new Department("Engineering");

        engineering.add(engManager);
        engineering.add(backend);
        engineering.add(platform);

        // --------------------------------------------------
        // Design Department
        // --------------------------------------------------

        Department design = new Department("Design");

        design.add(designManager);
        design.add(designer);
        design.add(qa2);

        // --------------------------------------------------
        // Company
        // --------------------------------------------------

        Department company = new Department("TechNova Inc.");

        company.add(cto);
        company.add(engineering);
        company.add(design);

        // --------------------------------------------------
        // Output
        // --------------------------------------------------

        company.printStructure("");

        System.out.println("======================================");
        System.out.printf(
                "Company Total Salary Cost = $%.2f%n",
                company.getSalaryCost());
    }
}
