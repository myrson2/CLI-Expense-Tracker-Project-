package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import model.User;
import exception.DataAccessException;

public class UserRepo {
    private HashMap<String, User> users = new HashMap<>();
    File listOfAccounts = new File("src/data/accounts/listOfAccounts.txt");

    public User addUser(User user){
        users.put(user.getEmail(), user);
        return user;
    }

    public void saveUsers(User user) throws DataAccessException{
        if(user == null){
            System.out.println("User is null.");
        }
       
        boolean existsOrCreated = listOfAccounts.exists();
        try {
            // Check if it exists OR create it if it doesn't
            if(!existsOrCreated){
                listOfAccounts.createNewFile();
            }
        } catch (IOException e) {
            throw new DataAccessException("Unable to create accounts list file.", e);
        }

        try(BufferedWriter write = new BufferedWriter(new FileWriter(listOfAccounts))){
             String contentFormat = String.format("""
                All registered Accounts: 
                Email: %s  ||  Username: %s  ||  Password: %s  ||  Expense File: %s
                    """, user.getEmail(), user.getUsername(), user.getPassword(), user.getExpenseFileName());
            
            write.write(contentFormat);
        } catch(IOException e){
            throw new DataAccessException("Failed to save user information.", e);
        }
    }

    public void loadUsers() throws DataAccessException {
       try( BufferedReader reader = new BufferedReader(new FileReader(listOfAccounts));) {
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
       } catch (IOException e) {
        throw new DataAccessException("Unable to load users from file.", e);
       }
    }

    public void updateAccountFile(String email, String password, String userName, String expenseFileName) throws DataAccessException{   
        User user = getUserByEmail(email);
        
        if(user == null) {
            System.out.println("User not found.");
            return;
        }
        
        // Step 1: Update the User object in memory
        user.setUsername(userName);
        user.setPassword(password);
        user.setExpenseFileName(expenseFileName);
        
        // Step 2: Read all lines, update the matching user, and write to temp file
        try(BufferedReader update = new BufferedReader(new FileReader(listOfAccounts));
            PrintWriter writer = new PrintWriter(new FileWriter("src/data/accounts/temp.txt"))){

            String line;
            boolean found = false;

            while((line = update.readLine()) != null){
                // Check if this line contains the email we're looking for
                if(line.contains(email)){
                    // Write the updated user information
                    String updatedLine = String.format("""
                        All registered Accounts: 
                        Email: %s  ||  Username: %s  ||  Password: %s  ||  Expense File: %s
                            """, user.getEmail(), user.getUsername(), user.getPassword(), user.getExpenseFileName());
                    writer.print(updatedLine);
                    writer.close();
                    found = true;
                } else {
                    // Write all other lines as they are
                    writer.println(line);
                }
            }
            
            if(!found) {
                System.out.println("User not found in file.");
            }

        } catch(IOException e){
            throw new DataAccessException("Unable to update account file.", e);
        }
        
        // Step 3: Replace original file with temp file
        File tempFile = new File("src/data/accounts/temp.txt");
        if(tempFile.exists()){
            listOfAccounts.delete();
            tempFile.renameTo(listOfAccounts);
        }
    }

    public User getUserByEmail(String email){
        return users.get(email);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

}
