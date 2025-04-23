// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import Exceptions.InvalidAmountException;
import Exceptions.SpendingLimitExceededException;
import Exceptions.SpendingLimitNotFoundException;
import Exceptions.TransactionNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
public class FinanceManager {

    //CLASS VARIABLES
    private ArrayList<Transaction> transactions;
    private ArrayList<SpendingLimit> spendingLimit;


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
    public void addTransaction(Transaction tranAdd){

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
    public boolean removeTransaction(String description) throws TransactionNotFoundException{
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
        if(findDescription.getDescription().toLowerCase().contains(description.toLowerCase())){
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
        return Double.parseDouble(String.format("%.2f", income));
    }

    //method for finding the total expense of the user
    public double getTotalExpense(){
        double expense = 0.0;
        for (Transaction findExpense : transactions) {
            if (findExpense instanceof Expense) {
                expense += findExpense.getAmount();
            }
        }
        return Double.parseDouble(String.format("%.2f", expense));
    }

    //in charge of finding the user's current balance
    public double getBalance() {
        double balance = getTotalIncome() - getTotalExpense();
        return Double.parseDouble(String.format("%.2f", balance));
    }

    //allow the user to add a spending limit to a specific category
    public void addSpendingLimit(Expense.ExpenseCategory categoryLimit, double limit) throws InvalidAmountException {

        if(limit < 0){
            throw new InvalidAmountException("Limit must be positive");
        }

        //check if the user wants to update their spending limit
        for(SpendingLimit exist : spendingLimit){
            if(exist.getCategory() == categoryLimit){
                exist.setLimit(limit);
                return;
            }
        }
        spendingLimit.add(new SpendingLimit(categoryLimit, limit));
        System.out.println("New limit updated successfully");
        System.out.println("New limit for " + categoryLimit + " is $" + limit);
    }

    //allow the user to remove their spending limit of a specific category
    public void removeSpendingLimit(Expense.ExpenseCategory categoryLimit) throws SpendingLimitNotFoundException {

        if (categoryLimit == null) {
            throw new SpendingLimitNotFoundException("No spending limit set for category: " + categoryLimit);
        }
        for(int i = 0; i< spendingLimit.size(); i++){
            if (spendingLimit.get(i).getCategory() == categoryLimit) {
                spendingLimit.remove(i);
                break;
            }
        }
    }

    //checkSpendingLimit() will use this method to look for the current spending
    // total of a specific category
    public double getCurrentSpendingLimit (Expense.ExpenseCategory category){
        double categoryTotal = 0.0;
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

    //a method only for this class, addTransaction() will use this method to
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
}
