package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import model.User;

public class UserRepo {
    private HashMap<String, User> users = new HashMap<>();
    File listOfAccounts = new File("src/data/accounts/listOfAccounts.txt");

    public User addUser(User user){
        users.put(user.getEmail(), user);
        return user;
    }

    public void saveUsers(User user){
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
            System.out.println("Error creating file.");
        }

        try(BufferedWriter write = new BufferedWriter(new FileWriter(listOfAccounts))){
             String contentFormat = String.format("""
                All registered Accounts: 
                Email: %s  ||  Username: %s
                    """, user.getEmail(), user.getUsername());
            
            write.write(contentFormat);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadUsers() {
       try( BufferedReader reader = new BufferedReader(new FileReader(listOfAccounts));) {
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
       } catch (IOException e) {
        e.printStackTrace();
       }
    }

    public void updateAccountFile(String email){   
        User user = getUserByEmail(email);
        
        try(BufferedWriter update = new BufferedWriter(new FileWriter(listOfAccounts, true))){
            
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public User getUserByEmail(String email){
        return users.get(email);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

}
