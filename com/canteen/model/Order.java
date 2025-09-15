package com.canteen.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private String userEmail;
    private List<OrderItem> items;
    private double totalAmount;
    private String paymentMethod;
    private LocalDateTime orderTime;
    private int tokenNumber;
    private String status;

    public Order(int orderId, String userEmail, List<OrderItem> items, double totalAmount, 
                 String paymentMethod, int tokenNumber) {
        this.orderId = orderId;
        this.userEmail = userEmail;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.tokenNumber = tokenNumber;
        this.orderTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public int getOrderId() { return orderId; }
    public String getUserEmail() { return userEmail; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public int getTokenNumber() { return tokenNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}