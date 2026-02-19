package service;

import repository.UserRepo;

import java.io.File;
import java.io.IOException;

import exception.AccountNotFoundException;
import exception.AuthenticationException;
import model.User;

public class UserService {
    private User user;
    private UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public void registerUser(String email, String username, String password, String expenseFileName){
        // file exceptions here
        user = new User(email, username, password, expenseFileName);
        userRepo.saveUsers(email, user);
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
    public boolean createExpenseFile(String expenseFileName){
        File expenseFile = new File("src/data/userExpenses/" + expenseFileName + ".txt");
        boolean existsOrCreated = expenseFile.exists();

        try {
            if(!existsOrCreated){
                expenseFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file.");
        }

        return existsOrCreated;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public User getUser() {
        return user;
    }
}
