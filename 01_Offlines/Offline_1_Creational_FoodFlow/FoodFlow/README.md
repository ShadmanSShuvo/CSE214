# FoodFlow: Interactive Full-Stack Ordering System

This directory houses the full-stack showcase version of **FoodFlow**, featuring a pure Java business logic core and an interactive modern web frontend built with React, Vite, and Tailwind CSS.

---

## 🏗️ Architecture

```
FoodFlow/
├── src/                  # Core Java backend / domain engine
│   ├── Main.java         # CLI entry point
│   ├── cli/              # Terminal interaction controllers
│   ├── io/               # CSV menu ingestion & receipt generation
│   ├── model/            # Order and OrderItem domain builders
│   ├── service/          # Pricing, tax, and discount calculation
│   └── util/             # String & currency formatting utilities
│
└── frontend/             # Modern interactive web UI
    ├── src/              # React components & state management
    ├── public/           # Static web assets
    ├── package.json      # Node dependencies
    └── vite.config.ts    # Vite bundler configuration
```

---

## 🚀 Running the Web Application
```bash
cd frontend
npm install
npm run dev
```
Navigate to `http://localhost:5173` to explore the interactive visual menu, real-time cart builder, discount coupon validation, and simulated digital checkout.
