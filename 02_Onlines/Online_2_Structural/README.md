# CSE-214: Online 2 - Structural Design Patterns

This repository contains comprehensive solutions, source code, and question sets for **BUET CSE-214: Object-Oriented Programming Language Sessional - Online Exam 2 (Structural Design Patterns)** across Batches 19, 21, 22, and 23.

---

## Pattern Coverage & Quick Index

| Batch | Problem / Section | Domain Scenario | Pattern(s) Applied | Subfolder Location |
| :---: | :---: | :--- | :--- | :--- |
| **23** | **A1** | Gift Shop: Gift items, gift wrapping, multi-region & multi-mode shipping | **Decorator + Bridge/Strategy** | [`Batch_23/A1_GiftShop`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_23/A1_GiftShop) |
| **23** | **B1** | Eid Gift Package System: Personal/Corporate packages, packaging options | **Composite + Strategy** | [`Batch_23/B1_GiftPackageComposite`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_23/B1_GiftPackageComposite) |
| **23** | **B2** | Meal & Grocery Bundle: Food items, set menus, and nested grocery packages | **Composite** | [`Batch_23/B2_MealGroceryBundle`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_23/B2_MealGroceryBundle) |
| **23** | **C1** | API Service: Legacy XML to JSON conversion with dynamic encryption & compression | **Adapter + Decorator** | [`Batch_23/C1_ApiResponseAdapterDecorator`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_23/C1_ApiResponseAdapterDecorator) |
| **23** | **C2** | Customer Loyalty Programme: Membership tiers and orthogonal point redemption | **Bridge** | [`Batch_23/C2_LoyaltyProgrammeBridge`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_23/C2_LoyaltyProgrammeBridge) |
| **22** | **A1** | IoT Security Device: Configurable notifications (Encryption, Priority, Logging) | **Decorator** | [`Batch_22/A1_IoTSecurityNotifierDecorator`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_22/A1_IoTSecurityNotifierDecorator) |
| **22** | **A2** | ZBazar Subscription Grocery Bundles: Single items & custom nested bundles | **Composite** | [`Batch_22/A2_ZBazarGroceryComposite`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_22/A2_ZBazarGroceryComposite) |
| **22** | **B1** | Dlachal Grocery: Event alerts (Confirmed, Dispatched, Failed) via Email/SMS/WhatsApp | **Bridge** | [`Batch_22/B1_DlachalNotificationBridge`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_22/B1_DlachalNotificationBridge) |
| **22** | **B2** | Smart Home App: Controlling 3rd-party OldSmartBulb & LegacyHeater | **Adapter** | [`Batch_22/B2_SmartHomeDeviceAdapter`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_22/B2_SmartHomeDeviceAdapter) |
| **22** | **C1** | ZBazar Ramadan Packages: Seasonal packages with Fruit, Sweet, & Gift wrap add-ons | **Decorator** | [`Batch_22/C1_RamadanPackageDecorator`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_22/C1_RamadanPackageDecorator) |
| **22** | **C2** | ZBazar Logistics: Delivery types (Standard/Express) & transports (Bike/Van/Drone/Robot) | **Bridge** | [`Batch_22/C2_ZBazarDeliveryBridge`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_22/C2_ZBazarDeliveryBridge) |
| **21** | **A1** | Weather Service: Adapting legacy weather data format to target client provider | **Adapter** | [`Batch_21/A1_WeatherServiceAdapter`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_21/A1_WeatherServiceAdapter) |
| **21** | **A2** | Computer Hardware Bundles: Individual components & nested gaming setups | **Composite** | [`Batch_21/A2_HardwareBundleComposite`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_21/A2_HardwareBundleComposite) |
| **21** | **B1** | Delivery Discounts: Stackable purchase discounts (Loyalty, Seasonal, High-Value) | **Decorator** | [`Batch_21/B1_DeliveryDiscountDecorator`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_21/B1_DeliveryDiscountDecorator) |
| **21** | **B2** | Component Add-ons: Hardware warranty, installation, and performance upgrades | **Decorator** | [`Batch_21/B2_ComponentAddonDecorator`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_21/B2_ComponentAddonDecorator) |
| **21** | **C1** | CoffeeTong Cafe: Dynamic beverage toppings and condiment customization | **Decorator** | [`Batch_21/C1_CoffeeTongDecorator`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_21/C1_CoffeeTongDecorator) |
| **21** | **C2** | Database Query: Adapting NoSQL database queries to SQL `DatabaseQuery` interface | **Adapter** | [`Batch_21/C2_DatabaseQueryAdapter`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_21/C2_DatabaseQueryAdapter) |
| **19** | **Adapter** | Audio Script Generator: Translating & adapting Bangla speech to English script | **Adapter** | [`Batch_19/ScriptGenerator_Adapter`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_19/ScriptGenerator_Adapter) |
| **19** | **Decorator** | Hogwarts Potion System: Dynamic potion recipes, costs, weights, & penalties | **Decorator + Factory** | [`Batch_19/HogwartsPotions_Decorator`](file:///Users/shuvo/CSE214/02_Onlines/Online_2_Structural/Batch_19/HogwartsPotions_Decorator) |

---

## Directory Organization

Each batch is organized into self-contained subfolders containing the problem PDF (where available) and the corresponding Java implementation. This prevents default-package class collisions (such as `HardwareComponent` in Batch 21) and allows running `javac *.java` cleanly in any folder.

```
Online_2_Structural/
├── README.md
├── Batch_19/
│   ├── HogwartsPotions_Decorator/
│   └── ScriptGenerator_Adapter/
├── Batch_21/
│   ├── soln.md
│   ├── A1_WeatherServiceAdapter/
│   ├── A2_HardwareBundleComposite/
│   ├── B1_DeliveryDiscountDecorator/
│   ├── B2_ComponentAddonDecorator/
│   ├── C1_CoffeeTongDecorator/
│   └── C2_DatabaseQueryAdapter/
├── Batch_22/
│   ├── CSE-214 Online on Structural Patterns.pdf
│   ├── StructuralPatternsAllOnline22.pdf
│   ├── soln.md
│   ├── A1_IoTSecurityNotifierDecorator/
│   ├── A2_ZBazarGroceryComposite/
│   ├── B1_DlachalNotificationBridge/
│   ├── B2_SmartHomeDeviceAdapter/
│   ├── C1_RamadanPackageDecorator/
│   └── C2_ZBazarDeliveryBridge/
└── Batch_23/
    ├── A1_GiftShop/
    ├── B1_GiftPackageComposite/
    ├── B2_MealGroceryBundle/
    ├── C1_ApiResponseAdapterDecorator/
    └── C2_LoyaltyProgrammeBridge/
```

---

## How to Compile & Run Any Solution

Navigate to any problem folder and compile:
```bash
# Example: Batch 23 B1
cd Batch_23/B1_GiftPackageComposite
javac *.java
java Main

# Example: Batch 23 C2
cd Batch_23/C2_LoyaltyProgrammeBridge
javac *.java
java Main
```
