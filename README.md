Ah! I see what happened — the main issue is that Markdown doesn’t render indented code blocks and nested folder structures properly if we just use plain code fences. We need to **use triple backticks for code blocks and preserve spacing**, and for the file structure, **we can also use a “tree” style inside a code block**.

Here’s a **fully cleaned-up, properly structured Markdown** ready to copy-paste:

# CLI Expense Tracker Project Specification

## Project Overview
Create a **command-line Expense Tracker** using **Java** that demonstrates:

- **Object-Oriented Programming (OOP)**
- **Exception Handling**
- **File Handling / Persistence**
- **Clean Architecture & Modular Design**

The project should be **portfolio-ready**, **modular**, and **easy to extend**.

---

## Functional Requirements

The CLI application must support:

1. **Add Expense**  
   - User inputs **description** and **amount**  
   - Expense is saved **persistently**

2. **Update Expense**  
   - Modify **description** and/or **amount**

3. **Delete Expense**  
   - Remove an existing expense

4. **View All Expenses**  
   - Display all saved expenses

5. **View Expense Summary**  
   - Total expenses  
   - Count of expenses  
   - Highest and lowest expense

6. **View Monthly Summary**  
   - Summary for a specific month of the current year

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

Use **custom exceptions**:
- `InvalidAmountException`
- `ExpenseNotFoundException`
- `DataAccessException`

### 3. File Handling
- Persist data in **JSON, CSV, TXT, or Binary**  
- **Load data** at program start  
- **Save data** after add/update/delete  

### 4. CLI Requirements
- Menu-driven interface  
- Clear prompts and input validation  
- User-friendly messages  
- Loops until user exits  

**Example Menu:**
```text
1. Add Expense
2. Update Expense
3. Delete Expense
4. View Expenses
5. View Summary
6. View Monthly Summary
0. Exit
````

---

## Architecture & Package Design

**Suggested Packages / Modules:**

```text
/model
/service
/repository
/exception
/ui
/util
```

### MODEL Package

* **Purpose:** Core entities
* **Class:** `Expense`
* **Fields:** `id`, `description`, `amount`, `date`
* **Methods:** getters/setters, `toString()`

### SERVICE Package

* **Purpose:** Business logic
* **Class:** `ExpenseService`
* **Methods:**

  * `addExpense()`
  * `updateExpense()`
  * `deleteExpense()`
  * `getAllExpenses()`
  * `getSummary()`
  * `getMonthlySummary()`

### REPOSITORY Package

* **Purpose:** File persistence
* **Class:** `ExpenseRepository`
* **Methods:**

  * `loadFromFile()`
  * `saveToFile()`
  * `add()`
  * `update()`
  * `delete()`

### EXCEPTION Package

* **Purpose:** Custom exceptions
* **Classes:**

  * `InvalidAmountException`
  * `ExpenseNotFoundException`
  * `DataAccessException`

### UI Package

* **Purpose:** CLI interaction
* **Class:** `ConsoleUI`
* **Methods:**

  * `displayMenu()`
  * `handleUserInput()`
  * `showExpenses()`
  * `showSummary()`

### UTIL Package

* **Purpose:** Helper classes
* **Examples:**

  * `InputValidator`
  * `DateUtils`
  * `FileUtils`

---

## File / Folder Structure

```text
CLI-Expense-Tracker/
├─ src/
│  ├─ model/
│  │  └─ Expense.java
│  │
│  ├─ service/
│  │  └─ ExpenseService.java
│  │
│  ├─ repository/
│  │  └─ ExpenseRepository.java
│  │
│  ├─ exception/
│  │  ├─ InvalidAmountException.java
│  │  ├─ ExpenseNotFoundException.java
│  │  └─ DataAccessException.java
│  │
│  ├─ ui/
│  │  └─ ConsoleUI.java
│  │
│  └─ util/
│     ├─ InputValidator.java
│     ├─ DateUtils.java
│     └─ FileUtils.java
│
├─ data/
│  └─ expenses.json  (or .csv / .txt depending on format)
│
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
* Add **user accounts/login**

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

Deliver a **clean, maintainable, and extendable CLI Expense Tracker** suitable for:

* Academic submission
* Portfolio showcase
* OOP demonstration
* Software design example

---
