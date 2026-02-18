package model;
//     Fields: id, username, password, expenseFileName
// Methods: getters/setters, authentication helpers

public class User { 
    private String id;
    private String username;
    private String password;
    private String expenseFileName;

    public User(String id, String username, String password, String expenseFileName){
        this.id = id;
        this.username = username;
        this.password = password;
        this.expenseFileName = expenseFileName;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getExpenseFileName() {
        return expenseFileName;
    }
}
