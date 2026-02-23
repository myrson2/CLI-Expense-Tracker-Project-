package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import auth.AuthManager;
import model.Expense;
import util.InputValidator;

public class ExpenseRepo {
    private AuthManager authManager;
    private ArrayList<Expense> expenseList = new ArrayList<>();

    public ExpenseRepo(AuthManager authManager){
        this.authManager = authManager;
    }

    public void add(Expense expense){
        expenseList.add(expense);
        savetoFile(expense);
    }

    public void savetoFile(Expense expense){
        File userExpenseFile = authManager.createExpenseFile(authManager.getUserService().getUser().getExpenseFileName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userExpenseFile, true))){
            String format = String.format("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s\n", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());
            writer.append(format);

        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public boolean update(int id){
        File userExpenseFile = authManager.createExpenseFile(authManager.getUserService().getUser().getExpenseFileName());
        boolean isUpdated = false;
        for (Expense expense : expenseList) {
            if (id == expense.getId()) {
                String description = InputValidator.readString("Enter New Description: ");
                expense.setDescription(description);
                double amount = InputValidator.readDouble("Enter New Amount: ");
                expense.setAmount(amount);
                InputValidator.readString("");
                
                try(BufferedReader update = new BufferedReader(new FileReader(userExpenseFile));
                    PrintWriter writer = new PrintWriter(new FileWriter("src/data/userExpenses/temp.txt"))) {
                    String line;
                    

                    while((line = update.readLine()) != null){
                        // Check if this line contains the email we're looking for
                        if(line.contains(String.valueOf(id))){
                            // Write the updated user information
                            String format = String.format("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s\n", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());

                            writer.print(format);
                        } else {
                            // Write all other lines as they are
                            writer.println(line);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                File tempFile = new File("src/data/userExpenses/temp.txt");
                if(tempFile.exists()){
                    userExpenseFile.delete();
                    tempFile.renameTo(userExpenseFile);
                }

                isUpdated = true;
                break;
            } 
        }
        return isUpdated;
    }

    public boolean delete(int id){
        File userExpenseFile = authManager.createExpenseFile(authManager.getUserService().getUser().getExpenseFileName());
        boolean isfound = expenseList.removeIf(expense -> expense.getId() == id);
        if(isfound){
            try(PrintWriter writer = new PrintWriter(userExpenseFile)){
                for (Expense expense : expenseList) {
                    String format = String.format("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s\n", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());

                    writer.write(format);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return isfound;
    }

    public void viewExpenses(){
        for (Expense expense : expenseList) {
            System.out.printf("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s\n", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());
        }
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }
}