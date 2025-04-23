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
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.amount = amount;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }


    //METHODS
    public abstract String getSummary();

    //methods mainly for debugging
    @Override
    public boolean equals(Object obj) {

        //checks if both transaction and the object we want to compare
        // referenced the same memory location
        if(this == obj){
            return true;
        }

        //checks if the object is null. If it is, then nothing to compare
        if(obj == null){
            return false;
        }

        //checks if both are the exact same class
        if(this.getClass() != obj.getClass()){
            return false;
        }

        //comparing transaction fields to obj field
        Transaction otherTransaction = (Transaction) obj;
        return this.description.equals(otherTransaction.description) &&
        Double.compare(this.amount, otherTransaction.amount) == 0 &&
                this.date.isEqual(otherTransaction.date);
    }

    public String toString(){
        return getClass().getSimpleName().toUpperCase() + "\n" +
                "Description: " + getDescription() + "\n" +
                "Amount: " + getAmount() + "\n" +
                "Date: " + getDate();
    }
}
