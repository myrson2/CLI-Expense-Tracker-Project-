package service;

import model.User;

public class UserService {
    private static User user;
    
    public void registerUser(String id, String username, String password, String expenseFileName){
        user = new User(id, username, password, expenseFileName);
    }

    public void loginUser(){
        
    }

    public void validateUser(){}
    public void createExpenseFile(){}
}
