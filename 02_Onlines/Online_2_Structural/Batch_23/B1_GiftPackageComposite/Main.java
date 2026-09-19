/**
 * Main demonstration for CSE-214 Online 2 (B1) - Structural Design Patterns.
 * Demonstrates:
 * 1. Composite Pattern for uniform treatment of individual GiftItems and
 * composite GiftPackages.
 * 2. Nested composition: user-crafted packages containing other packages and
 * items.
 * 3. Packaging options (Standard, Premium Gift Box +$15, Eco-Friendly Gift Box
 * +$8).
 * 4. Personal vs. Corporate gift packages.
 * 5. Repository publication and validation rule (>= 2 individual items).
 */
public class Main {
        public static void main(String[] args) {
                System.out.println("================================================================================");
                System.out.println("          CSE-214 ONLINE 2 (B1) - EID GIFT PACKAGE SYSTEM (COMPOSITE)           ");
                System.out.println(
                                "================================================================================\n");

                PackageRepository repository = new PackageRepository();

                // 1. Available Individual Gift Items in Store
                System.out.println("--- 1. Individual Gift Items Available in Store ---");
                GiftItem chocolates = new GiftItem("Ferrero Rocher Box", 18.00);
                GiftItem mug = new GiftItem("Ceramic Coffee Mug", 8.50);
                GiftItem perfume = new GiftItem("Oud Royal Perfume", 55.00);
                GiftItem book = new GiftItem("Bestseller Hardcover Book", 22.00);
                GiftItem flowers = new GiftItem("Fresh Red Roses Bouquet", 25.00);
                GiftItem diary = new GiftItem("Leather Bound Diary", 14.00);

                chocolates.print("  ");
                mug.print("  ");
                perfume.print("  ");
                book.print("  ");
                flowers.print("  ");
                diary.print("  ");
                System.out.println();

                // 2. Company Predefined Packages
                System.out.println("--- 2. Publishing Predefined Company Packages ---");
                PredefinedPackage eidDelight = new PredefinedPackage("Eid Delight Pack");
                eidDelight.add(chocolates);
                eidDelight.add(flowers);
                repository.publish(eidDelight);

                PredefinedPackage intellectualPack = new PredefinedPackage("Scholar & Coffee Pack");
                intellectualPack.add(book);
                intellectualPack.add(mug);
                repository.publish(intellectualPack);
                System.out.println();

                // 3. Customer 1 (Tanvir) creates a Personal Gift Package
                System.out.println("--- 3. Customer Creates Personal Gift Package (Premium Gift Box) ---");
                PersonalGiftPackage tanvirPackage = new PersonalGiftPackage(
                                "Eid Special for Mother",
                                "Tanvir Ahmed",
                                new PackagingStyle.PremiumGiftBox());
                tanvirPackage.add(perfume);
                tanvirPackage.add(flowers);
                tanvirPackage.add(chocolates);
                repository.publish(tanvirPackage);
                System.out.println();

                // 4. Validation Test: Attempting to create an invalid package with only 1 item
                System.out.println("--- 4. Validation Rule Test (Must have at least 2 individual gift items) ---");
                PersonalGiftPackage invalidPackage = new PersonalGiftPackage(
                                "Quick Single Gift",
                                "Sadik",
                                new PackagingStyle.StandardGiftBox());
                invalidPackage.add(mug); // only 1 item!
                boolean published = repository.publish(invalidPackage);
                System.out.printf("Validation status: %s%n%n",
                                published ? "Unexpectedly Succeeded" : "Successfully Enforced");

                // 5. Customer 2 (Corporate Client) creates a Corporate Gift Package
                // Uses individual items AND uses Tanvir's previously created package (nested
                // composite)
                System.out.println("--- 5. Corporate Customer Creates Package With Nested Existing Package ---");
                CorporateGiftPackage corpPackage = new CorporateGiftPackage(
                                "TechCorp Employee Eid Hamper",
                                "Grameenphone HR Team",
                                new PackagingStyle.EcoFriendlyGiftBox());
                corpPackage.add(diary);
                corpPackage.add(mug);
                // Reuse Tanvir's user-crafted package from the repository!
                GiftPackage existingPackage = repository.findByName("Eid Special for Mother");
                if (existingPackage != null) {
                        corpPackage.add(existingPackage);
                }
                repository.publish(corpPackage);
                System.out.println();

                // 6. Display Complete Repository Catalog
                System.out.println("--- 6. Browsing Full Company Repository Catalog ---");
                repository.displayCatalog();

                // 7. Verify Cost Calculation
                System.out.println("--- 7. Price Calculation Verification ---");
                System.out.printf("Predefined '%s' Total: $%.2f (Items: $18.00 + $25.00, Standard: $0)%n",
                                eidDelight.getName(), eidDelight.getPrice());
                System.out.printf(
                                "Personal '%s' Total: $%.2f (Items: $55.00 + $25.00 + $18.00 = $98.00, Premium Ribbon: +$15.00)%n",
                                tanvirPackage.getName(), tanvirPackage.getPrice());
                System.out.printf(
                                "Corporate '%s' Total: $%.2f (Items: $14.00 + $8.50 + $113.00 = $135.50, Eco-friendly: +$8.00)%n",
                                corpPackage.getName(), corpPackage.getPrice());
        }
}
