# Batch 23 Online 2 (C2) - Retail Customer Loyalty Programme (Bridge Pattern)

## Problem Statement
A retail company operates a customer loyalty programme with three membership tiers: **Regular**, **Premium**, and **Gold**. Customers earn loyalty points on every purchase. The number of points earned depends on the customer's membership tier:
- **Regular**: 1 point for every £100 spent.
- **Premium**: 2 points for every £100 spent.
- **Gold**: 3 points for every £100 spent.

Customers can redeem their accumulated loyalty points using one of the following redemption methods:
- **Cashback**: Convert the points into an instant discount on the current purchase.
- **Coupon**: Convert the points into shopping coupons for future purchases.
- **Charity Donation**: Donate the monetary value of the redeemed points to the company's charity programme.

### Core Architectural Requirements
1. **Full Orthogonality**: Any membership tier should support any redemption method. For example, a Gold member may choose cashback, while a Premium member may choose charity donation.
2. **Independent Extensibility**: The company plans to introduce:
   - New membership tiers (e.g., Platinum, Diamond)
   - New redemption methods (e.g., gift cards, airline miles, partner vouchers)
   The system must be designed so that new membership tiers and new redemption methods can be added **independently without requiring significant modifications to the existing implementation**.

---

## Design Pattern Analysis

### Pattern Applied: **Bridge Pattern**

### Why Bridge?
- **Decouple Abstraction from Implementation**:
  - **Abstraction**: `MembershipTier` (defines tier business rules, point accrual multiplier, and customer tier operations).
  - **Implementor**: `RedemptionMethod` (defines how redeemed points are converted and processed).
- **Prevents Class Explosion ($M \times N \rightarrow M + N$)**: Without Bridge, supporting $M$ tiers with $N$ redemption channels would require $M \times N$ separate classes (`RegularCashback`, `RegularCoupon`, `GoldCashback`, etc.).
- **Dynamic Runtime Switching**: A customer can switch their preferred redemption method dynamically at runtime via `tier.setRedemptionMethod(method)`.
- **Open/Closed Principle**: We can introduce `PlatinumTier` without modifying any `RedemptionMethod` class. Conversely, we can introduce `AirlineMilesRedemption` without modifying any `MembershipTier` class.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Abstraction** | `MembershipTier` | Base class defining tier logic; maintains a bridge reference to `RedemptionMethod`. |
| **Refined Abstractions** | `RegularTier`, `PremiumTier`, `GoldTier`, `PlatinumTier` | Specific membership tiers calculating points earned per £100 spent. |
| **Implementor** | `RedemptionMethod` | Interface defining `void redeem(customerName, points, monetaryValue, currentBill)`. |
| **Concrete Implementors** | `CashbackRedemption`, `CouponRedemption`, `CharityDonationRedemption`, `AirlineMilesRedemption` | Concrete channel execution logic. |
| **Context / Account** | `Customer` | Customer account holding tier, points balance, purchases, and redemption actions. |
| **Client** | `Main` | Demonstrates all cross-combinations, dynamic switching, and extensibility. |

---

## Class Architecture

```
       MembershipTier (Abstraction)   ----[bridge]---->   RedemptionMethod (Implementor)
       -redemptionMethod: RedemptionMethod                +redeem(name, pts, val, bill)
       +setRedemptionMethod(m)                                          ^
       +calculatePointsEarned(amount)                                   |
       +executeRedemption(name, pts, bill)                              |
                     ^                                                  |
       +-------------+-------------+             +------------+---------+---------+
       |             |             |             |            |         |         |
    Regular       Premium        Gold         Cashback     Coupon    Charity    AirlineMiles
  (1 pt/£100)   (2 pts/£100)  (3 pts/£100)   (Discount)  (Voucher)  (Donate)   (Partner)
```

---

## Solution Walkthrough

1. **Implementor Interface & Channels**:
   - `CashbackRedemption`: Applies points as an immediate discount to `currentBill`.
   - `CouponRedemption`: Generates a unique shopping coupon code (`CPN-LOYALTY-XXXX`) for future purchases.
   - `CharityDonationRedemption`: Donates value to company charity foundation with tax receipt (`REC-CHARITY-XXXX`).
   - `AirlineMilesRedemption`: Converts points into frequent flyer miles ($1\text{ pt} = 15\text{ miles}$).
2. **Abstraction (`MembershipTier.java`)**:
   ```java
   public abstract class MembershipTier {
       protected RedemptionMethod redemptionMethod;

       public MembershipTier(RedemptionMethod redemptionMethod) {
           this.redemptionMethod = redemptionMethod;
       }
       public void setRedemptionMethod(RedemptionMethod m) { this.redemptionMethod = m; }
       public int calculatePointsEarned(double amountSpent) {
           return ((int)(amountSpent / 100.0)) * getPointsPer100Spent();
       }
       public void executeRedemption(String customerName, int points, double currentBill) {
           redemptionMethod.redeem(customerName, points, getMonetaryValue(points), currentBill);
       }
   }
   ```
3. **Customer Operations**:
   `Customer.makePurchase(350)` computes points based on current tier multiplier and updates balance. `Customer.redeemPoints(points, currentBill)` validates balance and delegates to the bridged redemption method.

---

## How to Compile & Run

```bash
cd Batch_23/C2_LoyaltyProgrammeBridge
javac *.java
java Main
```

### Verified Sample Output
```
--- Scenario 1: Regular Member with Coupon Redemption ---
🛒 PURCHASE: Alice Walker (Regular Tier) spent £350.00 -> Earned 3 points! (New Balance: 3 points)
🛒 PURCHASE: Alice Walker (Regular Tier) spent £200.00 -> Earned 2 points! (New Balance: 5 points)
🎁 REDEEMING: Alice Walker is redeeming 5 points via [Shopping Coupon]...
  [Redemption: Shopping Coupon] Customer: Alice Walker
    • Points Redeemed: 5 points
    • Issued Voucher: CPN-LOYALTY-1001 valued at £5.00

--- Scenario 3: Gold Member with Instant Cashback ---
🛒 PURCHASE: Charlie Evans (Gold Tier) spent £1200.00 -> Earned 36 points! (New Balance: 36 points)
Charlie makes a new purchase of £150 and uses 20 points for instant discount:
🎁 REDEEMING: Charlie Evans is redeeming 20 points via [Instant Cashback]...
  [Redemption: Cashback] Customer: Charlie Evans
    • Points Redeemed: 20 points (Value: £20.00)
    • Current Bill: £150.00 | Discount Applied: £20.00
    • Final Bill Due: £130.00

--- Scenario 5: Independent Extensibility (Bridge in Action) ---
A) Adding New Implementor [Airline Miles] without modifying Membership Tiers:
🔄 PREFERENCE UPDATE: Charlie Evans switched redemption method to [Partner Airline Miles].
🎁 REDEEMING: Charlie Evans is redeeming 6 points via [Partner Airline Miles]...
  [Redemption: Airline Miles] Customer: Charlie Evans
    • Converted to: 90 Frequent Flyer Miles transferred to partner airline account

B) Adding New Abstraction [Platinum Tier] without modifying Redemption Methods:
🛒 PURCHASE: Diana Prince (Platinum (VIP) Tier) spent £1000.00 -> Earned 50 points! (New Balance: 50 points)
```
