# Problem Statement: E-Commerce Return and Refund Workflow

**Batch**: CSE23
**Source**: `E-commerce Return and Refund Workflow.pdf`
**Duration**: 30 Minutes
**Design Pattern**: **State Pattern**

---

## 1. Problem Description

An e-commerce platform needs to manage the lifecycle of a product return request. During processing, a return request goes through several conditions. The operations that can be performed depend strictly on its current condition:

1. **Requested**:
   - Return reason may be updated multiple times.
   - May be approved, rejected, or cancelled.
2. **Approved**:
   - Return reason can no longer be modified.
   - Request may still be cancelled if the returned item has not yet been delivered.
   - Item may be marked as delivered.
3. **Delivered**:
   - Request can no longer be cancelled.
   - Item may be inspected. If eligible, refund processing begins; otherwise, request becomes rejected.
4. **Processing Refund**:
   - Return reason cannot be modified and request cannot be cancelled.
   - Refund may succeed (moves to Refunded) or fail.
   - If refund fails, it may be attempted again later.
5. **Refunded / Rejected / Cancelled**:
   - Final (terminal) conditions. No further operations may change the request.

### Required Operations:
- `updateReason(String reason)`
- `approve()`
- `reject()`
- `cancel()`
- `itemDelivered()`
- `inspect(boolean eligible)`
- `refundSuccessful()`
- `refundFailed()`

For any operation not valid at a particular point in the workflow, the system must produce an informative message rather than performing the operation.

---

## 2. Design Pattern Justification: State Pattern

- **Why State Pattern?**:
  Each stage in the return lifecycle enforces distinct validation rules and allowed transitions.
- Encapsulating each condition into a concrete `ReturnState` class eliminates monolithic nested conditional checks in `ReturnRequest`, guarantees modularity, and makes it trivial to add new conditions (e.g., "Partial Refund", "Exchange Pending") without modifying existing state handlers.

---

## 3. State Transition Diagram

```
                 +-------------------+
                 |     Requested     |
                 +-------------------+
                 /         |         \
         reject /   cancel |          \ approve
               v           v           v
          [Rejected]  [Cancelled] +-------------------+
                                  |     Approved      |
                                  +-------------------+
                                   /         \
                           cancel /           \ itemDelivered
                                 v             v
                            [Cancelled]   +-------------------+
                                          |     Delivered     |
                                          +-------------------+
                                           /         \
                         inspect(eligible) /           \ inspect(ineligible)
                                          v             v
                             +-------------------+  [Rejected]
                             | Processing Refund |
                             +-------------------+
                                   |         |
                  refundSuccessful |         | refundFailed (retry)
                                   v         v
                              [Refunded]   (stays in Processing Refund)
```

---

## 4. How to Compile & Run

```bash
cd CSE23/EcommerceReturnRefundWorkflow
javac *.java
java Main
```
