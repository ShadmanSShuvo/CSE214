# FoodFlow: Submission Package (Student ID: 2305025)

**Course:** BUET CSE 214: Software Engineering Sessional
**Assignment:** Offline 1 (Creational Design Patterns)
**Author:** Shadman S. Shuvo (`2305025`)

---

## 📦 Package Architecture

The submission is organized into modular packages adhering to the Single Responsibility Principle:

```
Submission_2305025/
├── src/
│   ├── Main.java              # Application bootstrap & CLI launcher
│   ├── cli/                   # User interface layer
│   │   ├── Cli.java           # Terminal loop and command reader
│   │   └── CommandHandler.java# Command dispatcher (load, order, view, checkout)
│   ├── io/                    # Persistence & file processing
│   │   ├── CsvMenuLoader.java # Reads menu items from data/menu.csv
│   │   └── ReceiptWriter.java # Formats and writes receipts to disk/console
│   ├── model/                 # Domain entities
│   │   ├── DeliveryType.java  # Enum: PICKUP, DELIVERY
│   │   ├── MenuItem.java      # Base catalog item (id, name, basePrice, category)
│   │   ├── Order.java         # Master Order class with static Order.Builder
│   │   ├── OrderItem.java     # Individual selected item with static OrderItem.Builder
│   │   ├── PaymentMethod.java # Enum: CASH, CARD, BKASH, NAGAD
│   │   └── Size.java          # Enum: SMALL, REGULAR, LARGE (with price multipliers)
│   ├── service/               # Business logic
│   │   ├── MenuCatalog.java   # Centralized in-memory menu store
│   │   ├── OrderService.java  # Subtotal, tax, discount, loyalty & total calculations
│   │   └── ReceiptService.java# High-level receipt generation orchestrator
│   └── util/                  # Utility classes
│       ├── MoneyUtils.java    # Precision currency rounding & formatting
│       └── TextUtils.java     # String sanitization & validation
├── data/
│   └── menu.csv               # Default catalog data
├── spec/
│   └── assignment-spec.pdf    # Official assignment specification
└── TestHarness.java           # Automated test suite verifying validation & pricing
```

---

## 🎯 Creational Pattern Implementation Details

### `Order.java` & `Order.Builder`
```java
Order order = Order.builder("ORD-101", "Rahim", "01700000000", items)
    .deliveryType(DeliveryType.DELIVERY)
    .deliveryAddress("BUET Hall, Dhaka")
    .paymentMethod(PaymentMethod.BKASH)
    .couponCode("DISCOUNT20")
    .giftWrap(true)
    .rushOrder(false)
    .build();
```
- **Validation Gates in `build()`:**
  - Mandatory fields check (`requireNonBlank` for id, name, phone).
  - Conditional address requirement: Throws `IllegalArgumentException` if `deliveryType == DELIVERY` and address is blank.
  - Defense against empty items: Ensures at least one `OrderItem` is present.
  - Immutability: Wraps `items` in `Collections.unmodifiableList(new ArrayList<>(items))`.

---

## 🧪 Compilation & Automated Testing

### 1. Compile All Files
```bash
javac src/*.java src/*/*.java TestHarness.java
```

### 2. Run Automated Verification Tests
```bash
java -cp .:src TestHarness
```

### 3. Launch Interactive Command-Line Interface
```bash
java -cp src Main
```

**Common CLI Commands:**
- `menu` — Displays the loaded menu catalog.
- `order <itemId> <size> [qty]` — Adds an item to the current order cart.
- `cart` — Displays current items and running subtotal.
- `checkout` — Prompts for delivery type, address, payment method, coupon, and prints receipt.
- `exit` — Terminates the application.
