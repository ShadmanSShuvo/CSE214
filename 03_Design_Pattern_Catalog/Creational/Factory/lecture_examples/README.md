# Factory Patterns: Lecture Progression Examples

This directory demonstrates the architectural progression of object instantiation patterns:

1. **`1_No_Factory/`**: Problem demonstration. Direct instantiation tightly binds the client to concrete product classes.
2. **`2_Simple_Factory/`**: Static factory method parameter-based instantiation, centralizing creation logic into a dedicated helper.
3. **`3_Factory_Method/`**: Subclasses override factory methods to decide concrete product types, adhering to the Open/Closed Principle.
4. **`4_Abstract_Factory/`**: Expanding from a single product to families of interrelated products across international candy lines (American vs. Japanese).
