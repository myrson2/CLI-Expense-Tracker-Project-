package ui;

import service.UserService;
import util.InputValidator;

public class ConsoleUI {
    private static UserService userService = new UserService();

    public void start() {
            System.out.println("----- CLI EXPENSE TRACKER PROJECT -----");
            InputValidator.readString("(Enter to continue)");

            System.out.println("\n----- User Authentication -----");
            int choice = 0;
            do{
                choice = showLoginMenu();

                switch (choice) {
                    case 1 -> register();
                    case 2 -> login();
                }
            }while(choice != 0);
    }

    static int showLoginMenu(){
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

        System.out.println("Enter email: ");
        String email = InputValidator.readString("Enter email");
        // apply exceptions in email    

        String password = InputValidator.readString("Enter password: ");
        // apply exceptions in password

        // Assume after some Validation
        String userName = InputValidator.readString("Enter Username: ");

        // Assume after some Validation
        String expenseFileName = InputValidator.readString("Enter Expense File Name: ").trim();

        userService.registerUser(email, userName, password, expenseFileName);
    }

    static void login(){

    }

}
