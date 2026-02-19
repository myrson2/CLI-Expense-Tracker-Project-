# CLI Expense Tracker Project Specification

## Project Overview
**CLI Expense Tracker** is a Java-based command-line application to manage personal expenses efficiently.  
It demonstrates **OOP concepts**, **exception handling**, and **file persistence** while maintaining a clean, modular architecture.  
The project is portfolio-ready, easy to extend, and serves as a practical example of software design best practices.

---

## Functional Requirements

The CLI application must support:

1. **User Authentication & Account Management**
   - Register new users with username and password  
   - Login existing users  
   - Track **account details** in separate account files  
   - Each user can optionally create a **personal expense file**  
   - System loads user data from account file when logging in  

2. **Add Expense**  
   - User inputs **description** and **amount**  
   - Expense is saved in the user’s personal expense file  

3. **Update Expense**  
   - Modify **description** and/or **amount**  

4. **Delete Expense**  
   - Remove an existing expense  

5. **View All Expenses**  
   - Display all expenses for the logged-in user  

6. **View Expense Summary**  
   - Total expenses  
   - Count of expenses  
   - Highest and lowest expense  

7. **View Monthly Summary**  
   - Summary for a specific month of the current year  

8. **Expense History**
   - Track all past changes (add, update, delete) with timestamps  
   - Option to view history for auditing purposes  

---

## Technical Requirements

### 1. OOP Concepts
- **Encapsulation:** Private fields + getters/setters  
- **Abstraction:** Interfaces / abstract classes if needed  
- **Inheritance:** Use where applicable  
- **Polymorphism:** Method overriding/overloading  
- Each class has **clear responsibility**

### 2. Exception Handling
Handle errors like:
- Invalid numeric input
- Negative amounts
- Expense not found
- File read/write failures
- Empty expense list  
- User not found / authentication failed  

Use **custom exceptions**:
- `InvalidAmountException`
- `ExpenseNotFoundException`
- `DataAccessException`
- `AuthenticationException`
- `AccountNotFoundException`

### 3. File Handling
- **Account files:** Store user details (username, password, linked expense file)  
- **Expense files:** Each user can have a separate file for their expenses  
- **History files:** Track changes for auditing  
- Persist data in **JSON, CSV, TXT, or Binary**  
- Load data at program start and save after operations  

### 4. CLI Requirements
- Menu-driven interface  
- Clear prompts and input validation  
- User-friendly messages  
- Loops until user exits  

**Example Menu:**
```text
1. Register
2. Login
0. Exit

After Login:
1. Add Expense
2. Update Expense
3. Delete Expense
4. View Expenses
5. View Summary
6. View Monthly Summary
7. View Expense History
8. Manage Account / Expense File
0. Logout
````

---

## Architecture & Package Design

**Suggested Packages / Modules:**

* [model](./src/model)
* [service](./src/service)
* [repository](./src/repository)
* [exception](./src/exception)
* [ui](./src/ui)
* [util](./src/util)
* [auth](./src/auth)  *(AccountManager & authentication)*

### MODEL Package

* **Purpose:** Core entities
* **Classes:**

  * [Expense.java](./src/model/Expense.java)

    * Fields: `id`, `description`, `amount`, `date`, `userId`
    * Methods: getters/setters, `toString()`
  * [User.java](./src/model/User.java)

    * Fields: `id`, `username`, `password`, `expenseFileName`
    * Methods: getters/setters, authentication helpers

### SERVICE Package

* **Purpose:** Business logic
* **Classes:**

  * [ExpenseService.java](./src/service/ExpenseService.java)

    * Methods: 
      - `addExpense()` → Adds a new expense for a user  
      - `updateExpense()` → Updates an existing expense’s description or amount  
      - `deleteExpense()` → Deletes a selected expense  
      - `getAllExpenses()` → Returns a list of all expenses for a user  
      - `getSummary()` → Shows total, count, highest and lowest expense  
      - `getMonthlySummary()` → Shows expense summary for a specific month  
      - `getHistory()` → Returns the log of all expense changes for auditing  
  * [UserService.java](./src/service/UserService.java)

    * Methods: 
      - `registerUser()` → Creates a new user account  
      - `loginUser()` → Logs in a user by checking credentials  
      - `validateUser()` → Checks if username/password are correct  
      - `createExpenseFile()` → Creates a personal expense file for the user 

### REPOSITORY Package

* **Purpose:** File persistence
* **Classes:**

  * [ExpenseRepository.java](./src/repository/ExpenseRepository.java)

    * Methods: 
      - `loadFromFile()` → Reads all expenses from the file  
      - `saveToFile()` → Saves all expenses to the file  
      - `add()` → Adds a new expense to the file  
      - `update()` → Updates an existing expense in the file  
      - `delete()` → Deletes an expense from the file  
      - `loadHistory()` → Loads the expense change history from file  

  * [UserRepository.java](./src/repository/UserRepository.java)

    * Methods:
      - `loadUsers()` → Reads all user accounts from storage  
      - `saveUsers()` → Saves all user accounts to storage  
      - `addUser()` → Adds a new user to storage  
      - `getUserByUsername()` → Finds a user by username  
      - `updateAccountFile()` → Updates user account details in storage  

### EXCEPTION Package

* **Purpose:** Custom exceptions
* **Classes:**

  * [InvalidAmountException.java](./src/exception/InvalidAmountException.java)  → Thrown when an expense amount is invalid or negative 
  * [ExpenseNotFoundException.java](./src/exception/ExpenseNotFoundException.java) → Thrown when an expense cannot be found  
  * [DataAccessException.java](./src/exception/DataAccessException.java) → Thrown when there is a problem reading/writing files  
  * [AuthenticationException.java](./src/exception/AuthenticationException.java) → Thrown when login fails  
  * [AccountNotFoundException.java](./src/exception/AccountNotFoundException.java) → Thrown when a user account is missing  

### UI Package

* **Purpose:** CLI interaction
* **Class:** [ConsoleUI.java](./src/ui/ConsoleUI.java)
* **Methods:**

  - `displayMenu()` → Shows the main menu to the user  
  - `handleUserInput()` → Processes user’s menu choices  
  - `showExpenses()` → Displays all expenses for the current user  
  - `showSummary()` → Displays expense summary (total, count, highest/lowest)  
  - `showHistory()` → Shows all changes to expenses for auditing  
  - `showLoginMenu()` → Shows login and registration options  
  - `manageAccount()` → Allows creating or renaming a personal expense file  

### UTIL Package

* **Purpose:** Helper classes
* **Classes:**

  * [InputValidator.java](./src/util/InputValidator.java)
  * [DateUtils.java](./src/util/DateUtils.java)
  * [FileUtils.java](./src/util/FileUtils.java)

### AUTH Package

* **Purpose:** Handle authentication & account management
* **Classes:**

  * [AuthManager.java](./src/auth/AuthManager.java)

  * Methods:
    - `register()` → Registers a new user through UserService  
    - `login()` → Logs in a user through UserService  
    - `logout()` → Logs out the current user  
    - `isAuthenticated()` → Checks if a user is currently logged in  
    - `loadAccountFile()` → Loads the user’s account details from file  
    - `createExpenseFile()` → Creates a personal expense file for a user  

---

## File / Folder Structure

```text
CLI-Expense-Tracker/
├─ src/
│  ├─ model/
│  │  ├─ Expense.java
│  │  └─ User.java
│  ├─ service/
│  │  ├─ ExpenseService.java
│  │  └─ UserService.java
│  ├─ repository/
│  │  ├─ ExpenseRepository.java
│  │  └─ UserRepository.java
│  ├─ exception/
│  │  ├─ InvalidAmountException.java
│  │  ├─ ExpenseNotFoundException.java
│  │  ├─ DataAccessException.java
│  │  ├─ AuthenticationException.java
│  │  └─ AccountNotFoundException.java
│  ├─ ui/
│  │  └─ ConsoleUI.java
│  ├─ util/
│  │  ├─ InputValidator.java
│  │  ├─ DateUtils.java
│  │  └─ FileUtils.java
│  └─ auth/
│     └─ AuthManager.java
├─ data/
│  ├─ accounts/        (stores account files for each user)
│  │  └─ username.txt
│  ├─ expenses/        (optional expense files per user)
│  │  └─ username_expenses.txt
│  └─ history/         (stores change logs per user)
│     └─ username_history.tx
└─ README.md
```

---

## Future Enhancements

* Add **expense categories** (Food, Transport, Bills)
* Set **budget limits**
* Generate **reports / analytics**
* Export to **CSV / PDF**
* Use **database storage**
* Develop **GUI / Web version**
* Add **user roles** and permissions

---

## Output Expectations

* Full source code
* Package structure
* Class descriptions with **fields and methods**
* Exception handling strategy
* File format design
* Sample run/demo
* Suggestions for improvements

---

## Goal

Deliver a **clean, maintainable, and extendable CLI Expense Tracker** with:

* **User authentication & account management**
* **Personal expense files**
* **Expense history tracking**

Suitable for:

* Academic submission
* Portfolio showcase
* OOP demonstration
* Software design example
---
---
