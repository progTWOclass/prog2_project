// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import java.time.LocalDate;
public class Expense extends Transaction{

    //An enum class to only allow the values below
    public enum ExpenseCategory{
        RENT, FOOD, TRAVEL, UTILITY, ENTERTAINMENT, OTHER
    }

    //CLASS VARIABLES
    private ExpenseCategory category;//options = rent, food, travel


    //CONSTRUCTOR
    public Expense(String description, double amount, LocalDate date, ExpenseCategory category) {
        super(description, amount, date);
        this.category = category;

    }


    //GETTERS AND SETTERS
    public ExpenseCategory getCategory(){
        return category;
    }
    public void setCategory(ExpenseCategory category){
        this.category = category;
    }


    //METHODS
    @Override
    public String getSummary() {
        return "Expense\n" +
                "Description: " + getDescription() + "\n" +
                "Amount: $" + String.format("%.2f", getAmount()) + "\n" +
                "Date: " + getDate() + "\n" +
                "Category: " + getCategory() + "\n";
    }
}
