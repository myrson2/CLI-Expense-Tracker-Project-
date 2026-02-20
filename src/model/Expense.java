package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;

public class Expense {
    private static Random random = new Random(); 
    private static HashSet<Integer> randomNums = new HashSet<Integer>();

    private int id;
    private String description;
    private double amount;
    private String date;
    private String userEmail;

    public Expense(String description, double amount, String userEmail){
        this.description = description;
        this.amount = amount;
        this.userEmail = userEmail;
        this.date = insertTime();
        this.id = insertID();
    }

    public String insertTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate;
    }

    public int insertID(){
        int id;

        do{
            id = random.nextInt(0, 10000);
        } while(randomNums.contains(id));

        randomNums.add(id);
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
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

    //setter
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
