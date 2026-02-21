package service;

import repository.ExpenseRepo;

import auth.AuthManager;
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
        /*
         * USER ACTIVITY HISTORY LOGIC:
         *
         * 1. PURPOSE:
         *    - Track all important user actions (adding, updating, deleting expenses, changing username, login/logout, etc.)
         *    - Keep a timeline of changes with old and new values for auditing and reference.
         *
         * 2. HISTORY RECORD STRUCTURE:
         *    - User ID / Email    → Who performed the action
         *    - Action Type        → What kind of action (ADD_EXPENSE, UPDATE_USERNAME, DELETE_EXPENSE, etc.)
         *    - Details            → Description of the change (e.g., old value → new value)
         *    - Timestamp          → When the action occurred
         *
         * 3. LOGGING FLOW:
         *    a) User performs an action (e.g., update expense)
         *    b) Execute the action successfully
         *    c) Record a history entry with all relevant details
         *
         * 4. STORAGE:
         *    - Can use text file per user or a central log file
         *    - Each entry is immutable (do not modify history after writing)
         *
         * 5. BEST PRACTICES:
         *    - Only log after the action succeeds
         *    - Separate main data and history logic (e.g., ExpenseService vs HistoryService)
         *    - Include enough info to reconstruct what happened (old vs new values)
         *
         * 6. OPTIONAL:
         *    - Add extra details like date/time format, action success/failure, or user device
         *    - Consider grouping history by month or action type for reporting
         */
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
