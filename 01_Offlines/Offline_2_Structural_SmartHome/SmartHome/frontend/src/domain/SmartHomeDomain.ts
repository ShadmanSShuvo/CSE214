export interface SmartDevice {
  id: string;
  name: string;
  activate(): void;
  deactivate(): void;
  getPowerUsage(): number;
  getStatus(): string;
  getDeviceType(): string;
  isActive(): boolean;
}

let idCounter = 1;
export const generateId = (prefix: string) => `${prefix}_${idCounter++}_${Math.random().toString(36).substring(2, 7)}`;

// Leaf Devices
export class SmartLight implements SmartDevice {
  id: string;
  name: string;
  on: boolean = false;

  constructor(name: string = "Smart Light") {
    this.id = generateId("light");
    this.name = name;
  }

  activate(): void {
    this.on = true;
  }

  deactivate(): void {
    this.on = false;
  }

  getPowerUsage(): number {
    return this.on ? 10.0 : 0.0;
  }

  getStatus(): string {
    return `${this.name}: ${this.on ? "ON" : "OFF"}`;
  }

  getDeviceType(): string {
    return "SmartLight";
  }

  isActive(): boolean {
    return this.on;
  }
}

export class SmartThermostat implements SmartDevice {
  id: string;
  name: string;
  on: boolean = false;

  constructor(name: string = "Smart Thermostat") {
    this.id = generateId("thermostat");
    this.name = name;
  }

  activate(): void {
    this.on = true;
  }

  deactivate(): void {
    this.on = false;
  }

  getPowerUsage(): number {
    return this.on ? 150.0 : 0.0;
  }

  getStatus(): string {
    return `${this.name}: ${this.on ? "ON" : "OFF"}`;
  }

  getDeviceType(): string {
    return "SmartThermostat";
  }

  isActive(): boolean {
    return this.on;
  }
}

export class SmartSpeaker implements SmartDevice {
  id: string;
  name: string;
  on: boolean = false;

  constructor(name: string = "Smart Speaker") {
    this.id = generateId("speaker");
    this.name = name;
  }

  activate(): void {
    this.on = true;
  }

  deactivate(): void {
    this.on = false;
  }

  getPowerUsage(): number {
    return this.on ? 5.0 : 0.0;
  }

  getStatus(): string {
    return `${this.name}: ${this.on ? "Playing" : "Idle"}`;
  }

  getDeviceType(): string {
    return "SmartSpeaker";
  }

  isActive(): boolean {
    return this.on;
  }
}

// Decorator Pattern
export abstract class DeviceDecorator implements SmartDevice {
  id: string;
  name: string;
  wrapped: SmartDevice;

  constructor(wrapped: SmartDevice) {
    this.id = wrapped.id;
    this.name = wrapped.name;
    this.wrapped = wrapped;
  }

  activate(): void {
    this.wrapped.activate();
  }

  deactivate(): void {
    this.wrapped.deactivate();
  }

  getPowerUsage(): number {
    return this.wrapped.getPowerUsage();
  }

  getStatus(): string {
    return this.wrapped.getStatus();
  }

  getDeviceType(): string {
    return this.wrapped.getDeviceType();
  }

  isActive(): boolean {
    return this.wrapped.isActive();
  }
}

export class AccessRestricted extends DeviceDecorator {
  pin: number;
  locked: boolean;

  constructor(wrapped: SmartDevice, pin: number) {
    super(wrapped);
    this.pin = pin;
    this.locked = true;
  }

  override activate(): void {
    if (!this.locked) {
      this.wrapped.activate();
    }
  }

  override deactivate(): void {
    if (!this.locked) {
      this.wrapped.deactivate();
    }
  }

  override getStatus(): string {
    let s = this.wrapped.getStatus();
    if (this.locked) {
      s += " [LOCKED]";
    }
    return s;
  }

  unlock(inputPin: number): boolean {
    if (inputPin === this.pin) {
      this.locked = false;
      return true;
    }
    return false;
  }

  lock(): void {
    this.locked = true;
  }
}

export class TimerControlled extends DeviceDecorator {
  timerSeconds: number;
  remainingSeconds: number;
  timerRunning: boolean;

  constructor(wrapped: SmartDevice, timerSeconds: number) {
    super(wrapped);
    this.timerSeconds = timerSeconds;
    this.remainingSeconds = timerSeconds;
    this.timerRunning = false;
  }

  override activate(): void {
    this.wrapped.activate();
    this.timerRunning = true;
    this.remainingSeconds = this.timerSeconds;
  }

  override deactivate(): void {
    this.wrapped.deactivate();
    this.timerRunning = false;
  }

  override getStatus(): string {
    let s = this.wrapped.getStatus();
    if (this.timerRunning) {
      s += ` (auto-off in ${this.remainingSeconds}s)`;
    }
    return s;
  }

  simulateTimerExpiry(): void {
    if (this.timerRunning) {
      this.deactivate();
    }
  }

  tick(): boolean {
    if (this.timerRunning) {
      if (this.remainingSeconds > 1) {
        this.remainingSeconds--;
        return false;
      } else {
        this.remainingSeconds = 0;
        this.deactivate();
        return true; // expired
      }
    }
    return false;
  }
}

export class PowerThrottled extends DeviceDecorator {
  powerCap: number;

  constructor(wrapped: SmartDevice, powerCap: number) {
    super(wrapped);
    this.powerCap = powerCap;
  }

  override getPowerUsage(): number {
    return Math.min(this.wrapped.getPowerUsage(), this.powerCap);
  }

  override getStatus(): string {
    let s = this.wrapped.getStatus();
    const originalPower = this.wrapped.getPowerUsage();
    if (originalPower > this.powerCap) {
      s += ` [throttled to ${this.powerCap}W]`;
    }
    return s;
  }
}

// Composite Pattern
export abstract class CompositeDevice implements SmartDevice {
  id: string;
  name: string;
  children: SmartDevice[] = [];

  constructor(name: string) {
    this.id = generateId("composite");
    this.name = name;
  }

  addDevice(device: SmartDevice): void {
    this.children.push(device);
  }

  removeDevice(deviceId: string): void {
    this.children = this.children.filter((d) => d.id !== deviceId);
  }

  getChildren(): SmartDevice[] {
    return this.children;
  }

  activate(): void {
    for (const d of this.children) {
      d.activate();
    }
  }

  deactivate(): void {
    for (const d of this.children) {
      d.deactivate();
    }
  }

  getPowerUsage(): number {
    return this.children.reduce((total, d) => total + d.getPowerUsage(), 0);
  }

  abstract getStatus(): string;
  abstract getDeviceType(): string;

  isActive(): boolean {
    return this.children.some((d) => d.isActive());
  }
}

export class Room extends CompositeDevice {
  constructor(name: string) {
    super(name);
    this.id = generateId("room");
  }

  getStatus(): string {
    let sb = `[${this.name}]`;
    for (const d of this.children) {
      sb += `\n  ${d.getStatus()}`;
    }
    return sb;
  }

  getDeviceType(): string {
    return "Room";
  }
}

export class Home extends CompositeDevice {
  constructor(name: string = "My Smart Home") {
    super(name);
    this.id = generateId("home");
  }

  addRoom(room: SmartDevice): void {
    this.addDevice(room);
  }

  getStatus(): string {
    let sb = `=== ${this.name} ===`;
    for (const d of this.children) {
      sb += `\n${d.getStatus()}`;
    }
    return sb;
  }

  getDeviceType(): string {
    return "Home";
  }
}

// Room Decorators
export class EcoMode extends DeviceDecorator {
  room: CompositeDevice;
  budget: number;

  constructor(wrapped: CompositeDevice, budget: number) {
    super(wrapped);
    this.room = wrapped;
    this.budget = budget;
    this.id = wrapped.id;
  }

  override activate(): void {
    this.wrapped.activate();
    const children = this.room.getChildren();
    let total = this.rawPower();

    for (let i = children.length - 1; i >= 0 && total > this.budget; i--) {
      const d = children[i];
      if (d.getPowerUsage() > 0) {
        d.deactivate();
        total = this.rawPower();
      }
    }
  }

  override deactivate(): void {
    this.wrapped.deactivate();
  }

  private rawPower(): number {
    return this.room.getChildren().reduce((tot, d) => tot + d.getPowerUsage(), 0);
  }

  override getPowerUsage(): number {
    return Math.min(this.rawPower(), this.budget);
  }

  override getStatus(): string {
    return `[ECO: ${this.budget}W budget]\n${this.wrapped.getStatus()}`;
  }
}

export class GuestMode extends DeviceDecorator {
  room: CompositeDevice;
  allowedDevices: Set<string>;

  constructor(wrapped: CompositeDevice, allowedDevices: string[]) {
    super(wrapped);
    this.room = wrapped;
    this.allowedDevices = new Set(allowedDevices);
    this.id = wrapped.id;
  }

  override activate(): void {
    for (const d of this.room.getChildren()) {
      if (this.allowedDevices.has(d.getDeviceType())) {
        d.activate();
      }
    }
  }

  override deactivate(): void {
    for (const d of this.room.getChildren()) {
      if (this.allowedDevices.has(d.getDeviceType())) {
        d.deactivate();
      }
    }
  }

  override getPowerUsage(): number {
    let total = 0;
    for (const d of this.room.getChildren()) {
      if (this.allowedDevices.has(d.getDeviceType())) {
        total += d.getPowerUsage();
      }
    }
    return total;
  }

  override getStatus(): string {
    let sb = `[GUEST MODE]\n[${this.room.name}]`;
    for (const d of this.room.getChildren()) {
      let s = d.getStatus();
      if (!this.allowedDevices.has(d.getDeviceType())) {
        s += " [guest-restricted]";
      }
      sb += `\n  ${s}`;
    }
    return sb;
  }
}

export function prepareForNight(entity: SmartDevice, pin: number = 0, timerSeconds: number = 3600): SmartDevice {
  return new AccessRestricted(new TimerControlled(entity, timerSeconds), pin);
}
