package auth;

import java.io.File;

import exception.AccountNotFoundException;
import exception.AuthenticationException;
import exception.DataAccessException;
import service.UserService;

public class AuthManager {
    private UserService userService;

    public AuthManager(UserService userService){
        this.userService = userService;
    }

    public void registerUser(String id, String username, String password, String expenseFileName) throws DataAccessException{
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

    public File createExpenseFile(String expenseFileName) throws DataAccessException{
        File createdFile = userService.createExpenseFile(expenseFileName);
        return createdFile;
    }

    public void manageAccount(String email, String password, String userName, String expenseFileName) throws DataAccessException{
        userService.manageAccount(email, password, userName, expenseFileName);
    }
}
