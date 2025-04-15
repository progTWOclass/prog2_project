// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import java.time.LocalDate;
public class Income extends Transaction {

    //CONSTRUCTOR
    public Income(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }


    //METHODS
    @Override
    public String getSummary() {
        return "INCOME\n" +
                "Description: " + getDescription() + "\n" +
                "Amount: " + getAmount() + "\n" +
                "Date: " + getDate() + "\n";
    }
}
