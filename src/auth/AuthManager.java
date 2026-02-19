package auth;

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

    public void login(String email, String password){
        boolean isAuthenticated = isAuthenticated(email, password);

        if(isAuthenticated){
            System.out.println("Successfully login");
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public boolean isAuthenticated(String email, String password){
        boolean isAuthenticated = userService.loginUser(email, password);

        return isAuthenticated;
    }

    public void createExpenseFile(String expenseFileName){
        if (userService.createExpenseFile(expenseFileName)){
            System.out.println(userService.getUser().getExpenseFileName() + " expense file is created.");
        }
    }
}
