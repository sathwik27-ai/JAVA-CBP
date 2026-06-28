package model;

public class Driver {
    private int id;
    private String name;
    private double rating;

    public Driver(int id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (⭐ " + String.format("%.2f", rating) + ")";
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getRating() { return rating; }
}