// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import java.time.LocalDate;
public class Expense extends Transaction{

    //CLASS VARIABLES
    private String category;//options = rent, food, travel


    //CONSTRUCTOR
    public Expense(String description, double amount, LocalDate date, String category) {
        super(description, amount, date);
        this.category = category;
    }

    //GETTERS AND SETTERS
    public String getCategory(){
        return category;
    }
    public void setCategory(){
        this.category = category;
    }

    //METHODS
    @Override
    public String getSummary() {
        return "expense";
    }
}
