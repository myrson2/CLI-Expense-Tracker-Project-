package service;

import repository.UserRepo;

import java.io.File;
import java.io.IOException;

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

    public boolean loginUser(String email, String password){
        User findUser = userRepo.getUserByEmail(email);
        if(findUser == null) {return false;}

        boolean isValidated = validateUser(findUser, password);
        return isValidated;
    }   

    public boolean validateUser(User findUser, String password){
        if(findUser.getPassword().equalsIgnoreCase(password)){
            return true;
        }

        return false;
    }
    public boolean createExpenseFile(String expenseFileName){
        File expenseFile = new File("src/data/" + expenseFileName + ".txt");
        boolean existsOrCreated = false;

        try {
            // Check if it exists OR create it if it doesn't
            existsOrCreated = expenseFile.exists() || expenseFile.createNewFile();
            existsOrCreated = true;
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
