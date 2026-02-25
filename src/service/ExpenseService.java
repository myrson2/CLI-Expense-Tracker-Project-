package service;

import repository.ExpenseRepo;

import auth.AuthManager;
import exception.ExpenseNotFoundException;
import exception.InvalidAmountException;
import exception.DataAccessException;
import model.Expense;

import java.util.ArrayList;

public class ExpenseService {
    private Expense expense;
    private ExpenseRepo expenseRepo;
    private AuthManager authManager;

    public ExpenseService(ExpenseRepo expenseRepo, AuthManager authManager){
        this.expenseRepo = expenseRepo;
        this.authManager = authManager;
    }

    public void addExpense(String description, double amount, String email) throws InvalidAmountException, DataAccessException{
        expense = new Expense(description, amount, email);
        expenseRepo.add(expense);
    }   

    public boolean updateExpenses(int id) throws ExpenseNotFoundException, DataAccessException{
        return expenseRepo.update(id);
    }

    public boolean deleteExpenses(int id) throws ExpenseNotFoundException, DataAccessException{
        return expenseRepo.delete(id);
    }

    public void getAllExpenses(){
        expenseRepo.viewExpenses();
    }

    public String expenseDetails(Expense expense){
        return String.format("ID: %d  ||  Amount: %.2f  ||  Description: %s  ||  Date: %s  ||  Email: %s", expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUserEmail());
    }

    public String viewSummary(){
        ArrayList<Expense> expenses = expenseRepo.getExpenseList();

        double totalAmount = totalAmount(expenses);
        int count = size(expenses);
        String highest = expenseDetails(highestvalue(expenses));
        String lowest = expenseDetails(lowestvalue(expenses));

        return String.format(
                "Total Amount: %.2f || Total Count: %d expenses || Highest Expense: %s || owest Expense: %s\n"
                , totalAmount, count, highest, lowest
        );
    }

    static double totalAmount(ArrayList<Expense> expenses){
        double sum = 0;
        for(Expense e : expenses){
            sum += e.getAmount();
        }

        return sum;
    }

    static int size(ArrayList<Expense> expenses){
        return expenses.size();
    }

    static Expense highestvalue(ArrayList<Expense> expenses){
        Expense currHighExpense = expenses.getFirst();
        for(Expense e : expenses) {
            if (currHighExpense.getAmount() < e.getAmount()) {
               currHighExpense = e;
            }
        }
        return currHighExpense;
    }

    static Expense lowestvalue(ArrayList<Expense> expenses){
        Expense currLowExpense = expenses.getFirst();
        for(Expense e : expenses) {
            if (currLowExpense.getAmount() > e.getAmount()) {
                currLowExpense = e;
            }
        }
        return currLowExpense;
    }

    public void getHistory(){
        expenseRepo.loadHistory();
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
