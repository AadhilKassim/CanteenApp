package com.canteen.model;

import java.time.LocalDateTime;

public class Token {
    private String tokenNumber;
    private String userEmail;
    private String foodDetails;
    private double totalAmount;
    private String paymentMethod;
    private LocalDateTime orderTime;
    private String status;
    
    public Token(String tokenNumber, String userEmail, String foodDetails, double totalAmount, 
                String paymentMethod, LocalDateTime orderTime, String status) {
        this.tokenNumber = tokenNumber;
        this.userEmail = userEmail;
        this.foodDetails = foodDetails;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.orderTime = orderTime;
        this.status = status;
    }
    
    // Getters and setters
    public String getTokenNumber() { return tokenNumber; }
    public void setTokenNumber(String tokenNumber) { this.tokenNumber = tokenNumber; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getFoodDetails() { return foodDetails; }
    public void setFoodDetails(String foodDetails) { this.foodDetails = foodDetails; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}