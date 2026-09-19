# Batch 23 Online 2 (B1) - Eid User-Crafted Gift Package System (Composite Pattern)

## Problem Statement
An e-commerce company sells gift items such as chocolates, mugs, perfumes, books, flowers, and many more. The company also offers several pre-defined gift packages, each consisting of one or more individual gift items.

On the occasion of Eid, the company introduces a new feature that allows customers to create and publish their own gift packages. These user-crafted packages will be stored in the company's repository and displayed alongside the company's predefined packages for others to purchase.

Customer-designed packages can be either:
- **Personal Gift Package**: Intended for an individual recipient.
- **Corporate Gift Package**: Intended to be distributed among employees of an organization.

### Package Composition & Packaging Rules
- Customers create a package by adding **two or more individual gift items**.
- Customers may also use existing packages of the company or packages created by other customers if they wish (nested composition).
- Usually, company packages are packed in a standard gift box (adds no extra cost).
- For user-crafted packages, the company has introduced two additional packaging options:
  - **Standard Gift Box**: Adds **\$0** (default).
  - **Premium Gift Box**: Adds **\$15** and includes premium wrapping with a decorative ribbon.
  - **Eco-Friendly Gift Box**: Adds **\$8** and uses recyclable materials.
- The selected packaging style determines both the additional packaging cost and the presentation of the package.
- While creating a package, the customer must provide:
  1. Name of the package
  2. Creator name (to be displayed as the creator)
  3. Chosen packaging style

---

## Design Pattern Analysis

### Pattern Applied: **Composite Pattern (+ Strategy / Bridge Pattern)**

### Why Composite?
- **Nested Hierarchies**: A package can contain individual items (leaves) or other packages (composites), allowing arbitrary nesting (e.g., a corporate package containing an employee personal gift package plus extra books and mugs).
- **Uniform Interface**: Both single items (`GiftItem`) and packages (`GiftPackage`) implement the `GiftComponent` interface, enabling uniform price calculation and hierarchical tree rendering.
- **Validation Rule**: A package must contain $\ge 2$ individual gift items. The composite evaluates `getIndividualItemCount()` recursively across all children before allowing publication to the repository.

### Why Strategy / Bridge for Packaging?
- `PackagingStyle` decouples packaging options from the package hierarchy, making it easy to add new box styles (e.g., Velvet Gift Box) without touching `GiftPackage`.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `GiftComponent` | Interface declaring `getName()`, `getPrice()`, `print(indent)`, `getIndividualItemCount()`. |
| **Leaf** | `GiftItem` | Individual product sold by store (chocolates, mugs, perfume, etc.). |
| **Composite** | `GiftPackage` | Abstract base composite maintaining child components, packaging style, and validation logic. |
| **Concrete Composites** | `PersonalGiftPackage`, `CorporateGiftPackage`, `PredefinedPackage` | Package types with domain-specific labels. |
| **Packaging Strategy** | `PackagingStyle` | Defines `StandardGiftBox` (\$0), `PremiumGiftBox` (+\$15), `EcoFriendlyGiftBox` (+\$8). |
| **Repository** | `PackageRepository` | Central company catalog publishing and verifying packages. |
| **Client** | `Main` | Demonstrates item creation, personal packages, validation rejection, nested packages, and repository cataloging. |

---

## Class Architecture

```
                    <<interface>>
                    GiftComponent
             +getName(): String
             +getPrice(): double
             +print(indent: String): void
             +getIndividualItemCount(): int
                   ^
                   |
     +-------------+-------------+
     |                           |
  GiftItem                  GiftPackage (Abstract Composite)
  -name: String             -name: String
  -price: double            -creatorName: String
  +getPrice(): price        -packagingStyle: PackagingStyle
                            -components: List<GiftComponent>
                            +add(c: GiftComponent)
                            +remove(c: GiftComponent)
                            +getPrice(): sum(children) + packagingCost
                            +getIndividualItemCount(): sum(children)
                            +isValidForPublication(): count >= 2
                                 ^
                                 |
         +-----------------------+-----------------------+
         |                       |                       |
PersonalGiftPackage     CorporateGiftPackage     PredefinedPackage
```

---

## Solution Walkthrough

1. **Leaf (`GiftItem`)**:
   Stores `name` and `price`. `getIndividualItemCount()` returns 1.
2. **Composite Base (`GiftPackage`)**:
   Holds `List<GiftComponent>` and `PackagingStyle`.
   - Price calculation:
     ```java
     public double getPrice() {
         return getBaseComponentsPrice() + packagingStyle.getAdditionalCost();
     }
     ```
   - Validation logic:
     ```java
     public boolean isValidForPublication() {
         return getIndividualItemCount() >= 2;
     }
     ```
3. **Repository (`PackageRepository`)**:
   Guards package publication: checks `isValidForPublication()`. If fewer than 2 items are present, it logs a rejection and prevents registration.
4. **Nested Packaging in Action**:
   The Corporate package embeds a previously created Personal package (`Eid Special for Mother`) alongside a leather diary and mug, computing the complete recursive price:
   $$\text{Corporate Total} = \$14.00 + \$8.50 + \$113.00 + \$8.00 = \$143.50$$

---

## How to Compile & Run

```bash
cd Batch_23/B1_GiftPackageComposite
javac *.java
java Main
```

### Verified Sample Output
```
--- 2. Publishing Predefined Company Packages ---
✅ PUBLISHED: [Company Predefined Package] "Eid Delight Pack" by Company Store successfully added to company repository! (Total: $43.00)

--- 3. Customer Creates Personal Gift Package (Premium Gift Box) ---
✅ PUBLISHED: [Personal Gift Package] "Eid Special for Mother" by Tanvir Ahmed successfully added to company repository! (Total: $113.00)

--- 4. Validation Rule Test (Must have at least 2 individual gift items) ---
❌ REJECTED: Package "Quick Single Gift" cannot be published. A package must contain at least 2 individual gift items (current count: 1).
Validation status: Successfully Enforced

--- 5. Corporate Customer Creates Package With Nested Existing Package ---
✅ PUBLISHED: [Corporate Gift Package] "TechCorp Employee Eid Hamper" by Grameenphone HR Team successfully added to company repository! (Total: $143.50)
```
