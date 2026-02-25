package service;

import repository.UserRepo;

import java.io.File;
import java.io.IOException;

import exception.AccountNotFoundException;
import exception.AuthenticationException;
import exception.DataAccessException;
import model.User;

public class UserService {
    private User user;
    private UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public void registerUser(String email, String username, String password, String expenseFileName) throws DataAccessException{
        // file exceptions here
        user = new User(email, username, password, expenseFileName);
        User addedUser = userRepo.addUser(user);
        userRepo.saveUsers(addedUser);
    }

    public boolean loginUser(String email, String password) throws AccountNotFoundException, AuthenticationException{
        User findUser = userRepo.getUserByEmail(email);
        if(findUser == null) {
            throw new AccountNotFoundException("Account Not Found :)");
        }

        boolean isValidated = validateUser(findUser, password);
        return isValidated;
    }   

    public boolean validateUser(User findUser, String password) throws AuthenticationException{
        if(findUser.getPassword().equalsIgnoreCase(password)){
            return true;
        } else {
            throw new AuthenticationException("Invalid or Incorrect Password");
        }
    }
    public File createExpenseFile(String expenseFileName) throws DataAccessException{
        File expenseFile = new File("src/data/userExpenses/" + expenseFileName + ".txt");
        boolean existsOrCreated = expenseFile.exists();

        try {
            if(!existsOrCreated){
                expenseFile.createNewFile();
            }
        } catch (IOException e) {
            throw new DataAccessException("Could not create expense file.", e);
        }

        return expenseFile;
    }

    public void manageAccount(String email, String password, String userName, String expenseFileName) throws DataAccessException {
        User findUser = userRepo.getUserByEmail(email);
        
        if(findUser == null) {
            System.out.println("User not found.");
            return;
        }
        
        // Check if expense file name has changed
        String oldExpenseFileName = findUser.getExpenseFileName();
        
        if(!oldExpenseFileName.equals(expenseFileName)) {
            // Rename the old expense file to the new one
            String oldFilePath = "src/data/userExpenses/" + oldExpenseFileName + ".txt";
            String newFilePath = "src/data/userExpenses/" + expenseFileName + ".txt";
            
            File oldFile = new File(oldFilePath);
            File newFile = new File(newFilePath);
            
            if(oldFile.exists()) {
                if(oldFile.renameTo(newFile)) {
                    System.out.println("Expense file renamed from " + oldExpenseFileName + " to " + expenseFileName);
                } else {
                    System.out.println("Failed to rename expense file.");
                    return;
                }
            } else {
                System.out.println("Old expense file not found. Creating new one.");
                createExpenseFile(expenseFileName);
            }
        }
        
        // Update the account file with new details
        userRepo.updateAccountFile(email, password, userName, expenseFileName);
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public User getUser() {
        return user;
    }
}
