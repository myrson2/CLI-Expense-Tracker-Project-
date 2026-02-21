package repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import model.User;

public class UserRepo {
    private HashMap<String, User> users = new HashMap<>();
    File listOfAccounts = new File("src/data/accounts/listOfAccounts.txt");

    public void saveUsers(String email, User user){
        users.put(email, user);
        updateAccountFile(email, user.getUsername());
        // file handling
    }

    public User getUserByEmail(String email){
        return users.get(email);
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void display() {
        if(users.isEmpty()){
            System.out.println("No users found.");
            return;
        }
        System.out.println("All registered users:");
        for (String email : users.keySet()) {
            User user = users.get(email);
            System.out.println("Email: " + user.getEmail() + ", Username: " + user.getUsername());
        }
    }

    public void updateAccountFile(String email, String userName){
        boolean existsOrCreated = listOfAccounts.exists();
        try {
            // Check if it exists OR create it if it doesn't
            if(!existsOrCreated){
                listOfAccounts.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file.");
        }

        if(!existsOrCreated){
            String contentFormat = String.format("""
                All registered Accounts: 
                    Email: %s  ||  Username: %s
                    """, email, userName);
            
              try (FileWriter fw = new FileWriter(listOfAccounts)) { // No need to close manually with try-with-resources
                        fw.write(contentFormat);
                    } catch (IOException e) {
                        System.out.println("Write Error.");
                    }
        } else {
            try(FileWriter fw = new FileWriter(listOfAccounts, true)){
                String addedAccount = String.format("Email: %s  ||  Username: %s\n", email, userName);
                fw.append(addedAccount);
            } catch (IOException e){
                System.out.println("Error.");
            }
        }
    }

}
