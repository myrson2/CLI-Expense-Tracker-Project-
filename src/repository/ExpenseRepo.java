package repository;

import java.util.ArrayList;

import model.Expense;
import util.InputValidator;

public class ExpenseRepo {
    private ArrayList<Expense> expenseList = new ArrayList<>();

    public void add(Expense expense){
        expenseList.add(expense);
    }

    public boolean update(int id){
        boolean isUpdated = false;
        for (Expense expense : expenseList) {
            if (id == expense.getId()) {
                String description = InputValidator.readString("Enter New Description: ");
                expense.setDescription(description);
                double amount = InputValidator.readDouble("Enter New Amount: ");
                expense.setAmount(amount);
                isUpdated = true;
                break;
            } 
        }
        return isUpdated;
    }

    public boolean delete(int id){
        return expenseList.removeIf(expense -> expense.getId() == id);
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