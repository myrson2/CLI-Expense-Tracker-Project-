package ui;
import auth.AuthManager;
import exception.AccountNotFoundException;
import exception.AuthenticationException;
import repository.ExpenseRepo;
import repository.UserRepo;
import service.ExpenseService;
import service.UserService;
import util.InputValidator;

public class ConsoleUI {
    private static UserRepo userRepo = new UserRepo();
    private static UserService userService = new UserService(userRepo);
    private static AuthManager authManager = new AuthManager(userService);

    private static ExpenseRepo expenseRepo = new ExpenseRepo();
    private static ExpenseService expenseService = new ExpenseService(expenseRepo, authManager);

    public void start() {
            System.out.println("----- CLI EXPENSE TRACKER PROJECT -----");
            InputValidator.readString("(Enter to continue)");

            // User Authenticaiton 
            int login = 0;
            do{
                login = showLoginMenu();
                InputValidator.readString("");

                switch (login) {
                    case 1: register(); break;

                    case 2: if(login()) {
                        trackerDashboard();
                    } else {
                        System.out.println("No File Existed.");
                    } 
                    break;
                    
                    case 0: System.out.println("Exit"); break;
                }
            }while(login != 0);
            // End User Authentication
    }

    public void trackerDashboard(){

        boolean islogout = false;
        do {
            int choice = displayMenu();

            switch (choice) {
                case 1: // Add Expense 
                    System.out.println("----- Add Expense -----");
                    String description = InputValidator.readString("Enter Description: ");
                    double amount = InputValidator.readDouble("Enter Amount: ");

                    expenseService.addExpense(description, amount, userService.getUser().getEmail());
                    break;

                case 2: // Update Expenses
                    // Make View Expenses First then show ids then make a implementation to get the expense using id 
                    System.out.println("----- Update Expenses -----");
                    expenseService.viewExpenses();

                    int updateExpense = InputValidator.readInt("Enter ID: ");
                    InputValidator.readString("");
                    boolean isUpdated = expenseService.updateExpenses(updateExpense);

                    if(isUpdated){
                        System.out.println("Successfully Updated");
                    } else {
                        System.out.println("Error: Not Found or Invalid Inputs");
                    }
                    break;

                case 3: // Delete expenses
                    System.out.println("----- Update Expenses -----");
                    expenseService.viewExpenses();

                    int deleteExpense = InputValidator.readInt("Enter ID: ");
                    InputValidator.readString("");
                    boolean isdeleted = expenseService.updateExpenses(deleteExpense);

                    if(isdeleted){
                        System.out.println("Successfully Updated");
                    } else {
                        System.out.println("Error: Not Found or Invalid Inputs");
                    }
                    break;

                case 4: // View expenses
                    System.out.println("----- View Expenses -----");
                    expenseService.viewExpenses();
                    break;

                case 5:
                    System.out.println("Option 5 selected");
                    break;

                case 6:
                    System.out.println("Option 6 selected");
                    break;

                case 7:
                    System.out.println("Option 7 selected");
                    break;

                case 8:
                    System.out.println("Option 8 selected");
                    break;
                case 0:
                    islogout = true;
                    break;

                default:
                    System.out.println("❌ Invalid choice.");
            }

        } while (!islogout);
    }

    static int displayMenu(){
        System.out.println("----- Expense Tracker Dashboard -----");
        System.out.println("""
            After Login:
            1. Add Expense
            2. Update Expense
            3. Delete Expense
            4. View Expenses
            5. View Summary
            6. View Monthly Summary
            7. View Expense History
            8. Manage Account / Expense File
            0. Logout
                """);

            int choice = InputValidator.readInt("Enter choice: ");
            InputValidator.readString("");

            return choice;
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
        String expenseFileName = InputValidator.readString("Enter Expense File Name: ").trim().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_\\-]", "");

        authManager.registerUser(email, userName, password, expenseFileName);
    }

    static boolean login() {
        String email = InputValidator.readString("Email: ");
        String password = InputValidator.readString("Password: ");

        boolean isLogin = false;
        try{
            isLogin = authManager.login(email, password);
        }catch(AccountNotFoundException e){
            System.out.println(e.getMessage());
        } catch(AuthenticationException e){
            System.out.println(e.getMessage());
        }

        return isLogin;
    }

}
