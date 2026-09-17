public class Patient {
    private final String name;
    private final int age;
    private final double temperature;
    private final String bloodPressure;
    private String visitId;

    public Patient(String name, int age, double temperature, String bloodPressure) {
        this.name = name;
        this.age = age;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
}
