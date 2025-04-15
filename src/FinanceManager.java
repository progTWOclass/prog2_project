// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import Exceptions.SpendingLimitExceededException;
import Exceptions.TransactionNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class FinanceManager {

    //CLASS VARIABLES
    private ArrayList<Transaction> transactions;
    private ArrayList<SpendingLimit> spendingLimit;
    private static Scanner input = new Scanner(System.in);


    //CONSTRUCTORS
    public FinanceManager(){
        this.transactions = new ArrayList<>();
        this.spendingLimit = new ArrayList<>();
    }


    //GETTERS AND SETTERS
    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }
    public ArrayList<SpendingLimit> getSpendingLimit() {
        return spendingLimit;
    }

    public void setTransactions(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }
    public void setSpendingLimit(ArrayList<SpendingLimit> spendingLimit) {
        this.spendingLimit = spendingLimit;
    }


    //METHODS
    //add a transaction either income or expense
    public void addTransaction(Transaction tranAdd) {
        try {
            //check if adding another expense will exceed our spending limit or not
            if (tranAdd instanceof Expense) {
                Expense addExpense = (Expense) tranAdd;
                checkSpendingLimit(addExpense);
            }
            transactions.add(tranAdd);
        }catch (SpendingLimitExceededException sLEE){
            System.out.println("Warning " + sLEE.getMessage());
        }
    }

    //remove a transaction based on the keyword given by the user
    public boolean removeTransaction(String description){
        try {
            Transaction transactionFound = searchTransactionByDescription(description);
            return transactions.remove(transactionFound);//remove transaction successful
        }catch(TransactionNotFoundException tNFE){
            System.out.println(tNFE.getMessage());
            return false;
        }
    }

    //call the recursive method below
    public Transaction searchTransactionByDescription(String description) throws TransactionNotFoundException {
        Transaction findTransaction = recursiveSearchDescription(description, 0);
        if(findTransaction == null){
            throw new TransactionNotFoundException("error transaction not found");
        }
        return findTransaction;
    }

    //method that is only for this class and uses recursion to find the word given by the user
    private Transaction recursiveSearchDescription(String description, int index){

        //once the index is bigger than the size of transaction, we know that
        //we have reached the end of the arraylist, and we didn't find our
        //description keyword
        if(index >= transactions.size()){
            return null;
        }
        //get the current index, and find the description we are looking for
        //this works with partial description as well (ex: find "shopping" in "grocery shopping")
        Transaction findDescription = transactions.get(index);
        if(findDescription.getDescription().toLowerCase().contains(description)){
            return findDescription;
        }
        //we increment index in order the satisfy the base case
        return recursiveSearchDescription(description, index + 1);
    }

    //method for finding the total income of the user
    public double getTotalIncome(){
        double income = 0.0;
        for(Transaction findIncome : transactions){
            if(findIncome instanceof Income){
                income += findIncome.getAmount();
            }
        }
        return income;
    }

    //method for finding the total expense of the user
    public double getTotalExpense(){
        double expense = 0.0;
        for (Transaction findExpense : transactions) {
            if (findExpense instanceof Expense) {
                expense += findExpense.getAmount();
            }
        }
        return expense;
    }

    //allow the user to add a spending limit to a specific category
    public void addSpendingLimit(Expense.ExpenseCategory categoryLimit, double limit){
        //check if the user wants to update their spending limit
        for(SpendingLimit exist : spendingLimit){
            if(exist.getCategory() == categoryLimit){
                exist.setLimit(limit);
                return;
            }
        }
        spendingLimit.add(new SpendingLimit(categoryLimit, limit));
    }

    //allow the user to remove their spending limit of a specific category
    public void removeSpendingLimit(Expense.ExpenseCategory categoryLimit){
        for(int i = 0; i< spendingLimit.size(); i++){
            if (spendingLimit.get(i).getCategory() == categoryLimit) {
                spendingLimit.remove(i);
                break;
            }
        }
    }

    //look for the current spending total of a specific category
    public double getCurrentSpendingLimit (Expense.ExpenseCategory category){
        double categoryTotal = 0;
        for(Transaction spending : transactions){
            if(spending instanceof Expense){
                Expense expense = (Expense) spending;//type cast to get category and amount from expense class
                if(expense.getCategory() == category){
                    categoryTotal += expense.getAmount();
                }
            }
        }
        return categoryTotal;
    }

    //check if adding another expense will exceed the spending limit for the specified category
    private void checkSpendingLimit (Expense expense) throws SpendingLimitExceededException{
        for(SpendingLimit limits : spendingLimit){
            if(limits.getCategory() == expense.getCategory()){
                double currentSpendingLimit = getCurrentSpendingLimit(expense.getCategory());
                if(currentSpendingLimit + expense.getAmount() > limits.getLimit()){
                    throw new SpendingLimitExceededException(
                            String.format("You will exceed %s limit of $%.2f (current: $%.2f)",
                            expense.getCategory(), limits.getLimit(), currentSpendingLimit));
                }
            }
        }
    }

    public static void main(String[] args) {

        FinanceManager money = new FinanceManager();
        System.out.println("Add New Transaction. 1 for income, 2 for expense");
        try{
            int choice = input.nextInt();
            input.nextLine();
            if(choice == 1){
                try{
                    System.out.println("add a description");
                    String description = input.nextLine();
//                    input.nextLine();
                    System.out.println("add a salary");
                    double salary = input.nextDouble();
                    System.out.println("enter a year");
                    int year = input.nextInt();
                    System.out.println("enter a month");
                    int month = input.nextInt();
                    System.out.println("enter a day");
                    int day = input.nextInt();
                    LocalDate date = LocalDate.of(year, month, day);
                    Income income = new Income(description, salary, date);
                    //System.out.println(income.getSummary());
                    money.addTransaction(income);
                    for(Transaction t : money.transactions){
                        System.out.println(t.getSummary());
                    }
                }catch(InputMismatchException e){
                    System.out.println("input invalid");
                }
            }else{
                try{
                    System.out.println("add a description");
                    String description = input.nextLine();
//                    input.nextLine();
                    System.out.println("add the amount");
                    double expense = input.nextDouble();
                    System.out.println("enter a year");
                    int year = input.nextInt();
                    System.out.println("enter a month");
                    int month = input.nextInt();
                    System.out.println("enter a day");
                    int day = input.nextInt();
                    LocalDate date = LocalDate.of(year, month, day);
                    // Display available categories
                    System.out.println("Available categories:");
                    Expense.ExpenseCategory[] categories = Expense.ExpenseCategory.values();
                    for (int i = 0; i < categories.length; i++) {
                        System.out.println((i+1) + ". " + categories[i]);
                    }

                    System.out.println("Select a category (1-" + categories.length + "):");
                    int categoryChoice = input.nextInt();
                    input.nextLine(); // Consume newline

                    // Validate category choice
                    if (categoryChoice < 1 || categoryChoice > categories.length) {
                        System.out.println("Invalid category selection");
                        return;
                    }

                    Expense.ExpenseCategory category = categories[categoryChoice-1];
                    Expense expenses = new Expense(description, expense, date, category);
                    //System.out.println(expenses.getSummary());
                    money.addTransaction(expenses);

                    for(Transaction t : money.transactions){
                        System.out.println(t.getSummary());
                    }
                }catch(InputMismatchException e){
                    System.out.println("input invalid");
                }
            }
        }catch(InputMismatchException e){
            System.out.println("invalid input. please enter 1 or 2");
        }
    }
}
