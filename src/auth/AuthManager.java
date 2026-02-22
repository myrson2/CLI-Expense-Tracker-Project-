package auth;

import java.io.File;

import exception.AccountNotFoundException;
import exception.AuthenticationException;
import service.UserService;

public class AuthManager {
    private UserService userService;

    public AuthManager(UserService userService){
        this.userService = userService;
    }

    public void registerUser(String id, String username, String password, String expenseFileName){
        userService.registerUser(id, username, password, expenseFileName);
        createExpenseFile(expenseFileName);
    }

    public boolean login(String email, String password) throws AccountNotFoundException, AuthenticationException{
        boolean isAuthenticated = isAuthenticated(email, password);

        if(isAuthenticated){ return true;}

        return false;
    }

    public UserService getUserService() {
        return userService;
    }

    public boolean isAuthenticated(String email, String password) throws AccountNotFoundException, AuthenticationException{
        boolean isAuthenticated = userService.loginUser(email, password);

        return isAuthenticated;
    }

    public File createExpenseFile(String expenseFileName){
        File createdFile = userService.createExpenseFile(expenseFileName);

        return createdFile;
    }

    public void manageAccount(String email){
        userService.manageAccount(email);
    }
}
