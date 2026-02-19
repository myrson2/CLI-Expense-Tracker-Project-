package repository;

import java.util.HashMap;
import model.User;

public class UserRepo {
    private HashMap<String, User> users = new HashMap<>();

    public void saveUsers(String email, User user){
        users.put(email, user);
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

}
