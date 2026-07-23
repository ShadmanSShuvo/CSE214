# Workspace Organization Walkthrough

The **CSE214** directory structure has been completely reorganized into clean, intuitive, and modular subdirectories.

## Reorganized Layout

```
CSE214/
├── 01_Offlines_and_Assignments/
│   ├── Offline_1_Creational_DP/
│   │   ├── Submission_2305025/          # Active Java submission (src, data, spec, TestHarness.java)
│   │   ├── Starter_Template_off1/       # Offline 1 base template
│   │   └── Web_Variant_off1web/         # Offline 1 web variant template
│   └── Offline_2_Structural_DP/         # Offline 2 (Smart Home Assignment)
│       ├── Code/                        # Demo & test runner Java files
│       ├── Spec/                        # Spec docx
│       ├── SmartHome_Assignment.md
│       └── SmartHome_Assignment.pdf
├── 02_Onlines/
│   └── Online_1/
│       └── January2026_ CSE 214_ Online_1_protected.pdf
├── 03_Examples_and_Practice/
│   ├── Builder_Pattern/
│   │   ├── lecture_examples/            # 1_No_Builder, 2_Standard_Builder, 3_Fluent_Builder, 4_Factory_And_Builder
│   │   ├── my_practice/                 # Builder Pattern practice solution
│   │   └── builder_pattern.py           # Python reference implementation
│   └── Factory_Pattern/
│       ├── lecture_examples/            # 1_No_Factory, 2_Simple_Factory, 3_Factory_Method, 4_Abstract_Factory
│       └── factory-method.py            # Python reference implementation
└── Archives/                            # Clean storage for zip backups
    ├── 2305025.zip
    ├── Assignment 2 on Structural Design Pattern.zip
    ├── Offline_1_Creational_DP.zip
    ├── builder_patterns.zip
    └── factory_patterns.zip
```

---

## Changes Completed

1. **Created Top-Level Categories**:
   - `01_Offlines_and_Assignments/`
   - `02_Onlines/`
   - `03_Examples_and_Practice/`
   - `Archives/`
2. **Consolidated Offlines & Submissions**:
   - Moved `Offline-1-Creational-Design-Pattern` components into `01_Offlines_and_Assignments/Offline_1_Creational_DP/`.
   - Cleaned up duplicate unzipped `2305025/` folder at workspace root after verifying file integrity.
   - Organized `Assignment 2 on Structural DP` files under `01_Offlines_and_Assignments/Offline_2_Structural_DP/`.
3. **Organized Online Exam Content**:
   - Moved exam PDF into `02_Onlines/Online_1/`.
4. **Grouped Study Examples & Practice**:
   - Organized Builder and Factory pattern examples, practice projects, and python scripts under `03_Examples_and_Practice/`.
5. **Archived Backups**:
   - Moved all `.zip` archives into `Archives/`.

---

## Verification

Verified using `find . -maxdepth 3 -not -path '*/.*'`. The workspace root now contains only clean category directories (`01_Offlines_and_Assignments`, `02_Onlines`, `03_Examples_and_Practice`, `Archives`).
