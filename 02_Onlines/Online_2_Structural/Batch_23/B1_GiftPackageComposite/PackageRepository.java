import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repository to store and manage predefined packages and published
 * customer-crafted packages.
 * Displays all available packages for customers to browse, purchase, or
 * incorporate into new packages.
 */
public class PackageRepository {
    private final List<GiftPackage> packages = new ArrayList<>();

    public boolean publish(GiftPackage pkg) {
        if (pkg == null) {
            System.err.println("Cannot publish a null package.");
            return false;
        }

        // Business Rule: Customers will create a package by adding two or more
        // individual gift items
        if (!pkg.isValidForPublication()) {
            System.out.printf("❌ REJECTED: Package \"%s\" cannot be published. " +
                    "A package must contain at least 2 individual gift items (current count: %d).%n",
                    pkg.getName(), pkg.getIndividualItemCount());
            return false;
        }

        packages.add(pkg);
        System.out.printf("✅ PUBLISHED: [%s] \"%s\" by %s successfully added to company repository! (Total: $%.2f)%n",
                pkg.getPackageType(), pkg.getName(), pkg.getCreatorName(), pkg.getPrice());
        return true;
    }

    public List<GiftPackage> getAllPackages() {
        return Collections.unmodifiableList(packages);
    }

    public GiftPackage findByName(String name) {
        for (GiftPackage p : packages) {
            if (p.getName().equalsIgnoreCase(name.trim())) {
                return p;
            }
        }
        return null;
    }

    public void displayCatalog() {
        System.out.println("================================================================================");
        System.out.println("                   E-COMMERCE EID GIFT PACKAGE CATALOG                          ");
        System.out.println("================================================================================");
        if (packages.isEmpty()) {
            System.out.println("No packages currently available in the repository.");
            return;
        }
        for (int i = 0; i < packages.size(); i++) {
            System.out.printf("Package #%d:%n", i + 1);
            packages.get(i).print("  ");
            System.out.println("--------------------------------------------------------------------------------");
        }
    }
}
