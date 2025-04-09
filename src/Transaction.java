// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import java.time.LocalDate;
abstract class Transaction {

    //CLASS VARIABLES
    private String description;
    private double amount;
    private LocalDate date;


    //CONSTRUCTOR
    public Transaction (String description, double amount, LocalDate date){
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    //GETTERS AND SETTERS
    public String getDescription() {
        return description;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //METHODS
    public abstract String getSummary();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String toString(){
        return "default string";
    }
}
