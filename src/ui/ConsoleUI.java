package ui;

import auth.AuthManager;
import repository.UserRepo;
import service.UserService;
import util.InputValidator;

public class ConsoleUI {
    private static UserRepo userRepo = new UserRepo();
    private static UserService userService = new UserService(userRepo);
    private static AuthManager authManager = new AuthManager(userService);

    public void start() {
            System.out.println("----- CLI EXPENSE TRACKER PROJECT -----");
            InputValidator.readString("(Enter to continue)");

            // User Authenticaiton 
            int choice = 0;
            do{
                choice = showLoginMenu();
                InputValidator.readString("");

                switch (choice) {
                    case 1 -> register();
                    case 2 -> login();
                    case 0 -> System.out.println("Exit");
                }
            }while(choice != 0);
            // End User Authentication
    }

    static int showLoginMenu(){
        System.out.println("\n----- User Authentication -----");
        System.out.println("""
                1. Register
                2. Login
                0. Exit
                """);
        int choice = InputValidator.readInt("> ");

        return choice;
    }

    static void register(){
        System.out.println("----- Registration -----\n");

        String email = InputValidator.readString("Enter email: ");
        // apply exceptions in email    

        String password = InputValidator.readString("Enter password: ");
        // apply exceptions in password

        // Assume after some Validation
        String userName = InputValidator.readString("Enter Username: ");

        // Assume after some Validation
        String expenseFileName = InputValidator.readString("Enter Expense File Name: ").trim();

        authManager.registerUser(email, userName, password, expenseFileName);
        userRepo.display();
    }

    static void login(){
        String email = InputValidator.readString("Email: ");
        String password = InputValidator.readString("Password: ");

        authManager.login(email, password);
    }

}
