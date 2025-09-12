package com.canteen.model;

public class FoodItem {
    private int id;
    private String name;
    private double price;
    private int availableQuantity;
    private int totalQuantity;
    private String type; // VEG or NON-VEG
    
    public FoodItem(int id, String name, double price, int availableQuantity, int totalQuantity, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.totalQuantity = totalQuantity;
        this.type = type;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
    
    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}