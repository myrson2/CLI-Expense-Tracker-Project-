package model;
//     Fields: id, username, password, expenseFileName
// Methods: getters/setters, authentication helpers

public class User { 
    private String email;
    private String username;
    private String password;
    private String expenseFileName;

    public User(String email, String username, String password, String expenseFileName){
        this.email = email;
        this.username = username;
        this.password = password;
        this.expenseFileName = expenseFileName;
    }

    public String getEmail() {
        return email;
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

    //setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setExpenseFileName(String expenseFileName) {
        this.expenseFileName = expenseFileName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
