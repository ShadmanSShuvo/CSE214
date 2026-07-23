# Workspace Organization Plan for CSE214

Organize the root `CSE214` workspace directory, which contains Object Oriented Programming / Design Patterns course material (Offlines, Onlines, practice code, and zip archives), into a clean, intuitive, and modular folder structure.

## Proposed Folder Layout

```
CSE214/
├── 01_Offlines_and_Assignments/
│   ├── Offline_1_Creational_DP/
│   │   ├── Submission_2305025/      # Student submission folder (src, data, spec, TestHarness.java)
│   │   ├── Starter_Template_off1/  # Base assignment files
│   │   └── Web_Variant_off1web/    # Additional web variant template
│   └── Offline_2_Structural_DP/     # Structural Design Pattern Assignment
│       ├── Code/                    # Smart Home demo & test runner
│       ├── Spec/                    # Assignment spec docx
│       ├── SmartHome_Assignment.md
│       └── SmartHome_Assignment.pdf
├── 02_Onlines/
│   └── Online_1/
│       └── January2026_ CSE 214_ Online_1_protected.pdf
├── 03_Examples_and_Practice/
│   ├── Builder_Pattern/
│   │   ├── lecture_examples/        # Standard builder examples (No_Builder, Standard_Builder, etc.)
│   │   ├── my_practice/             # Personal practice implementations
│   │   └── builder_pattern.py
│   └── Factory_Pattern/
│       ├── lecture_examples/        # Standard factory examples (No_Factory, Simple_Factory, etc.)
│       └── factory-method.py
└── Archives/                        # Clean storage for zip backups
    ├── 2305025.zip
    ├── Assignment 2 on Structural Design Pattern.zip
    ├── Offline_1_Creational_DP.zip
    ├── builder_patterns.zip
    └── factory_patterns.zip
```

---

## User Review Required

> [!NOTE]
> 1. Root-level `2305025/` folder is a duplicate copy of `Offline-1-Creational-Design-Pattern/2305025/`. We will consolidate submission files under `01_Offlines_and_Assignments/Offline_1_Creational_DP/Submission_2305025/`.
> 2. All loose `.zip` files currently sitting in the project root will be safely moved into an `Archives/` folder to prevent clutter while preserving backups.

---

## Proposed Changes

### Directory Re-organization

#### [MODIFY] Directory Structure of `CSE214`

- Create category top-level directories:
  - `01_Offlines_and_Assignments/`
  - `02_Onlines/`
  - `03_Examples_and_Practice/`
  - `Archives/`
- Move `Offline-1-Creational-Design-Pattern` into `01_Offlines_and_Assignments/Offline_1_Creational_DP/`
- Move `Assignment 2 on Structural DP` into `01_Offlines_and_Assignments/Offline_2_Structural_DP/`
- Move `online` into `02_Onlines/Online_1/`
- Group `builder_patterns`, `builder_patterns-mine`, `builder_pattern.py` under `03_Examples_and_Practice/Builder_Pattern/`
- Group `factory_patterns`, `factory-method.py` under `03_Examples_and_Practice/Factory_Pattern/`
- Move all `.zip` files (`2305025.zip`, `Assignment 2 on Structural Design Pattern.zip`, `Offline_1_Creational_DP.zip`, `builder_patterns.zip`, `factory_patterns.zip`) to `Archives/`.
- Clean up duplicate unzipped `2305025` directory at workspace root.

---

## Verification Plan

### Automated & Manual Verification
1. List contents of each created directory recursively to ensure no source files, PDFs, specs, or code files were lost.
2. Confirm the root directory contains only the top-level clean category folders.
