# SmartHome: Full-Stack IoT Automation Dashboard

This directory houses the full-stack visual showcase version of the **SmartHome Automation Hub**.

---

## 🏗️ Architecture

```
SmartHome/
├── backend/              # Java backend models
│   ├── SmartHome.java    # Composite & Decorator domain models
│   └── SmartHomeTestRunner.java # Assertion test runner
│
└── frontend/             # Modern React 18 + TypeScript + Vite UI
    ├── src/
    │   ├── App.tsx       # Interactive smart home dashboard
    │   ├── components/   # Room cards, Device cards, Builder modal, PIN modal
    │   └── domain/       # TypeScript domain models matching Java interfaces
    ├── package.json      # Dependencies (lucide-react, tailwindcss, vite)
    └── vite.config.ts    # Build configuration
```

---

## 🚀 Running the Web Interface
```bash
cd frontend
npm install
npm run dev
```
Open `http://localhost:5173` to visually build rooms, install and upgrade devices (PIN lock, Timer, Power Throttle), activate Eco Mode or Guest Mode, and monitor real-time wattage meters.
