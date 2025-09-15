package com.canteen.model;

public class OrderItem {
    private int foodId;
    private String foodName;
    private int quantity;
    private double price;
    private double subtotal;

    public OrderItem(int foodId, String foodName, int quantity, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = quantity * price;
    }

    public int getFoodId() { return foodId; }
    public String getFoodName() { return foodName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getSubtotal() { return subtotal; }
}