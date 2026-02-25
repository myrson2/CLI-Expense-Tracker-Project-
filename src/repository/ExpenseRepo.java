package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import auth.AuthManager;
import exception.ExpenseNotFoundException;
import exception.InvalidAmountException;
import exception.DataAccessException;
import model.Expense;
import util.InputValidator;

public class ExpenseRepo {
    private AuthManager authManager;
    private ArrayList<Expense> expenseList = new ArrayList<>();
    private ArrayList<String> history = new ArrayList<>();

    public ExpenseRepo(AuthManager authManager){
        this.authManager = authManager;
    }

    public void add(Expense expense) throws InvalidAmountException, DataAccessException{
        if(expense.getAmount() <= 0) throw new InvalidAmountException("Amount should not be 0 or negative");
        expenseList.add(expense);
        savetoFile(expense);
    }

    public void savetoFile(Expense expense) throws DataAccessException{
        File userExpenseFile = authManager.createExpenseFile(authManager.getUserService().getUser().getExpenseFileName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userExpenseFile, true))){
            String format = String.format("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s\n", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());
            writer.append(format);

            String description = String.format("Saved Expense ID %d: %s -> %.2f", expense.getId(), expense.getDescription(), expense.getAmount());

            addHistory(description);
        } catch (IOException e) {
           // wrap underlying I/O problem in a checked exception for callers
           throw new DataAccessException("Unable to save expense to file.", e);
        }
    }

    public boolean update(int id) throws ExpenseNotFoundException, DataAccessException{
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
                    throw new DataAccessException("Unable to update expense file.", e);
                }
                
                File tempFile = new File("src/data/userExpenses/temp.txt");
                if(tempFile.exists()){
                    userExpenseFile.delete();
                    tempFile.renameTo(userExpenseFile);
                }

                isUpdated = true;
            } else {
                throw new ExpenseNotFoundException("Expense not found");
            }
        }

        String description = String.format("Updated Expense - ID %d", id);
        addHistory(description);
        return isUpdated;
    }

    public boolean delete(int id) throws ExpenseNotFoundException, DataAccessException {
        File userExpenseFile = authManager.createExpenseFile(authManager.getUserService().getUser().getExpenseFileName());
        boolean isfound = expenseList.removeIf(expense -> expense.getId() == id);
        if(isfound){
            try(PrintWriter writer = new PrintWriter(userExpenseFile)){
                for (Expense expense : expenseList) {
                    String format = String.format("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s\n", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());

                    writer.write(format);
                }
            } catch (IOException e) {
                throw new DataAccessException("Unable to write deletion to file.", e);
            }
        } else {
            throw new ExpenseNotFoundException("Expense not found");
        }

        String description = String.format("Deleted Expense - ID %d", id);

        addHistory(description);
        return isfound;
    }

    public void viewExpenses(){
        for (Expense expense : expenseList) {
            System.out.printf("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s\n", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());
        }
    }

    public void addHistory(String description){
        String details = historytime() + " || " + description;
        history.add(details);
    }

    public void loadHistory(){
        for (String histories : history) {
            System.out.println(histories);
        }
    }

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public String historytime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate;
    }

}