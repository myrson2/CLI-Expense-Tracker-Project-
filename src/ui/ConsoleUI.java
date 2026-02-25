package ui;
import auth.AuthManager;
import exception.AccountNotFoundException;
import exception.AuthenticationException;
import exception.ExpenseNotFoundException;
import exception.InvalidAmountException;
import exception.DataAccessException;
import repository.ExpenseRepo;
import repository.UserRepo;
import service.ExpenseService;
import service.UserService;
import util.InputValidator;

public class ConsoleUI {
    private final UserRepo userRepo = new UserRepo();
    private final UserService userService = new UserService(userRepo);
    private final AuthManager authManager = new AuthManager(userService);

    private final ExpenseRepo expenseRepo = new ExpenseRepo(authManager);
    private final ExpenseService expenseService = new ExpenseService(expenseRepo, authManager);

    public void start() {
            System.out.println("----- CLI EXPENSE TRACKER PROJECT -----");
            InputValidator.readString("(Enter to continue)");

            // User Authentication
            int login = 0;
            do{
                login = showLoginMenu();

                switch (login) {
                    case 1: register(); break;

                    case 2: if(login()) {
                        handleUserInput();
                    } else {
                        System.out.println("No File Existed.");
                    } 
                    break;
                    
                    case 0: System.out.println("Exit"); break;
                }
            }while(login != 0);
            // End User Authentication
    }

    public void handleUserInput(){

        boolean islogout = false;
        do {
            int choice = displayMenu();

            switch (choice) {
                case 1: // Add Expense 
                    System.out.println("----- Add Expense -----");
                    try{
                        String description = InputValidator.readString("Enter Description: ");
                        double amount = InputValidator.readDouble("Enter Amount: ");

                        expenseService.addExpense(description, amount, userService.getUser().getEmail());
                    } catch(InvalidAmountException | DataAccessException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2: // Update Expenses
                    // Make View Expenses First then show ids then make a implementation to get the expense using id 
                    System.out.println("----- Update Expenses -----");
                    expenseService.getAllExpenses();

                    try {
                        int updateExpense = InputValidator.readInt("Enter ID: ");
                        InputValidator.readString("");
                        boolean isUpdated = expenseService.updateExpenses(updateExpense);
                        
                        if(isUpdated){
                            System.out.println("Successfully Updated");
                        } else {
                            System.out.println("✗ Update failed. Expense not found or invalid data.");
                        }
                    } catch (ExpenseNotFoundException | DataAccessException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3: // Delete expenses
                    System.out.println("----- Update Expenses -----");
                    expenseService.getAllExpenses();

                    try{
                         int deleteExpense = InputValidator.readInt("Enter ID: ");
                        InputValidator.readString("");
                        boolean deleted = expenseService.deleteExpenses(deleteExpense);

                        if(deleted){
                            System.out.println("Successfully Deleted");
                        } else {
                            System.out.println("Error: Not Found or Invalid Inputs");
                        }
                    } catch(ExpenseNotFoundException | DataAccessException e){
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                case 4: // View expenses
                    System.out.println("----- View Expenses -----");
                    expenseService.getAllExpenses();
                    break;

                case 5: // View Summary 
                    System.out.println("----- View Summary -----");
                    expenseService.viewSummary();
                    break;

                case 6:
                    System.out.println("----- View History -----");
                    expenseService.getHistory();
                    break;

                case 7: // Manage Account / Expense File
                    String email = InputValidator.readString("Enter email: ");

                    String password = InputValidator.readString("Enter new password: ");

                    String userName = InputValidator.readString("Enter new Username: ");

                    String expenseFileName = InputValidator.readString("Enter new Expense File Name: ").trim()
                            .replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_\\-]", "");
                    
                    try {
                        authManager.manageAccount(email, password, userName, expenseFileName);
                    } catch (DataAccessException e) {
                        System.out.println("Account update failed: " + e.getMessage());
                    }
                    break;
                case 0:
                    islogout = true;
                    break;

                default:
                    System.out.println("❌ Invalid choice.");
            }

        } while (!islogout);
    }

    int displayMenu(){
        System.out.println("----- Expense Tracker Dashboard -----");
        System.out.println("""
            After Login:
            1. Add Expense
            2. Update Expense
            3. Delete Expense
            4. View Expenses
            5. View Summary
            6. View Expense History
            7. Manage Account / Expense File
            0. Logout
                """);

            int choice = InputValidator.readInt("Enter choice: ");
            return choice;
    }

    int showLoginMenu(){
        System.out.println("\n----- User Authentication -----");
        System.out.println("""
                1. Register
                2. Login
                0. Exit
                """);
        int choice = InputValidator.readInt("> ");

        return choice;
    }

    void register(){
        System.out.println("----- Registration -----\n");

        String email = InputValidator.readString("Enter email: ");
        // apply exceptions in email    

        String password = InputValidator.readString("Enter password: ");
        // apply exceptions in password

        // Assume after some Validation
        String userName = InputValidator.readString("Enter Username: ");

        // Assume after some Validation
        String expenseFileName = InputValidator.readString("Enter Expense File Name: ").trim().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_\\-]", "");

        try {
            authManager.registerUser(email, userName, password, expenseFileName);
        } catch (DataAccessException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    boolean login() {
        String email = InputValidator.readString("Email: ");
        String password = InputValidator.readString("Password: ");

        boolean isLogin = false;
        try{
            isLogin = authManager.login(email, password);
        }catch(AccountNotFoundException | AuthenticationException e){
            System.out.println(e.getMessage());
        }

        return isLogin;
    }

}
