/**
 * Test Driver: Command Pattern Template Demonstration
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("          COMMAND DESIGN PATTERN TEMPLATE DEMO            ");
        System.out.println("==========================================================");

        // 1. Instantiate the Invoker
        RemoteControl remote = new RemoteControl(5);

        // 2. Instantiate Receivers
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        Stereo livingRoomStereo = new Stereo("Living Room");
        CeilingFan bedroomFan = new CeilingFan("Master Bedroom");

        // 3. Instantiate Concrete Commands
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);

        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(livingRoomStereo);
        StereoOffCommand stereoOff = new StereoOffCommand(livingRoomStereo);

        CeilingFanMediumCommand fanMedium = new CeilingFanMediumCommand(bedroomFan);
        CeilingFanHighCommand fanHigh = new CeilingFanHighCommand(bedroomFan);
        CeilingFanOffCommand fanOff = new CeilingFanOffCommand(bedroomFan);

        // 4. Assign Commands to Remote Slots
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, kitchenLightOn, kitchenLightOff);
        remote.setCommand(2, stereoOnWithCD, stereoOff);
        remote.setCommand(3, fanMedium, fanOff);
        remote.setCommand(4, fanHigh, fanOff);

        System.out.println(remote);

        // 5. Test Basic Invocations
        System.out.println("--- Section 1: Basic Invocations ---");
        remote.onButtonWasPushed(0);
        remote.offButtonWasPushed(0);
        remote.onButtonWasPushed(2);

        // 6. Test Undo & Redo
        System.out.println("\n--- Section 2: Undo & Redo Mechanics ---");
        remote.undoButtonWasPushed(); // Should turn off stereo
        remote.redoButtonWasPushed(); // Should re-turn on stereo

        // 7. Test State-Tracking Undo with Ceiling Fan
        System.out.println("\n--- Section 3: State-Tracking Undo (Ceiling Fan) ---");
        remote.onButtonWasPushed(3); // Medium
        remote.onButtonWasPushed(4); // High
        remote.offButtonWasPushed(4); // Off

        remote.undoButtonWasPushed(); // Reverts Off -> High
        remote.undoButtonWasPushed(); // Reverts High -> Medium
        remote.undoButtonWasPushed(); // Reverts Medium -> Off

        // 8. Test Macro / Composite Command (Party Mode)
        System.out.println("\n--- Section 4: Composite / Macro Command ---");
        Command[] partyOn = { livingRoomLightOn, kitchenLightOn, stereoOnWithCD, fanHigh };
        Command[] partyOff = { livingRoomLightOff, kitchenLightOff, stereoOff, fanOff };

        MacroCommand partyOnMacro = new MacroCommand("Party On Mode", partyOn);
        MacroCommand partyOffMacro = new MacroCommand("Party Off Mode", partyOff);

        // Assign macro to slot 0 dynamically
        remote.setCommand(0, partyOnMacro, partyOffMacro);

        remote.onButtonWasPushed(0);
        System.out.println("\nUndoing entire party macro:");
        remote.undoButtonWasPushed();

        System.out.println("\n==========================================================");
        System.out.println("          COMMAND PATTERN DEMO COMPLETED SUCCESSFULLY     ");
        System.out.println("==========================================================");
    }
}
