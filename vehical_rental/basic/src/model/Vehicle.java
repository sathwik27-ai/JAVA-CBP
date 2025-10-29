package model;

public class Vehicle {
    private int id;
    private String model;
    private String type;
    private double pricePerDay;
    private boolean available;
    private int capacity;

    public Vehicle(int id, String model, String type, double pricePerDay, boolean available, int capacity) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.pricePerDay = pricePerDay;
        this.available = available;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return model + " (" + type + ", " + capacity + " seats) - ₹" + pricePerDay + "/day - "
                + (available ? "Available" : "Booked");
    }

    public String getDisplayText() {
        return id + " - " + toString();
    }

    public int getId() { return id; }
    public String getModel() { return model; }
    public String getType() { return type; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return available; }
    public int getCapacity() { return capacity; }
}