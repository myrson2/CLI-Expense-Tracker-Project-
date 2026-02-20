package service;

import repository.ExpenseRepo;

import auth.AuthManager;
import model.Expense;

public class ExpenseService {
    private Expense expense;
    private ExpenseRepo expenseRepo;
    private AuthManager authManager;

    public ExpenseService(ExpenseRepo expenseRepo, AuthManager authManager){
        this.expenseRepo = expenseRepo;
        this.authManager = authManager;
    }

    public void addExpense(String description, double amount, String email){
        expense = new Expense(description, amount, email);
        expenseRepo.add(expense);
    }   

    public boolean updateExpenses(int id){
        return expenseRepo.update(id);
    }

    public boolean deleteExpenses(int id){
        return expenseRepo.delete(id);
    }

    public void viewExpenses(){
        expenseRepo.viewExpenses();
    }

    public String expenseDetails(Expense expense){
        String stringFormatted = String.format("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());
        return stringFormatted;
    }

    public Expense getAllExpenses(){
        return expense;
    }

    public ExpenseRepo getExpenseRepo() {
        return expenseRepo;
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public Expense getExpense() {
        return expense;
    }
}
