package repository;

import java.util.ArrayList;

import model.Expense;

public class ExpenseRepo {
    private static ArrayList<Expense> expenseList = new ArrayList<>();

    public void add(){
        
    }

    public static ArrayList<Expense> getExpenseList() {
        return expenseList;
    }
}