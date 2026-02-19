package model;

import java.time.LocalDateTime;

public class Expense {
    private int id;
    private String description;
    private double amount;
    private LocalDateTime date;
    private String userEmail;

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }
    
}
