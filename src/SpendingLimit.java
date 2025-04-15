// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
public class SpendingLimit {

    //CLASS VARIABLES
    private Expense.ExpenseCategory category;//calling the enum class in Expense class to reuse the code
    private  double limit;


    //CONSTRUCTOR
    public SpendingLimit(Expense.ExpenseCategory category, double limit){
        this.category = category;
        this.limit = limit;
    }


    //GETTERS AND SETTERS
    public Expense.ExpenseCategory getCategory(){
        return category;
    }
    public double getLimit(){
        return limit;
    }

    public void setCategory(Expense.ExpenseCategory category) {
        this.category = category;
    }
    public void setLimit(double limit){
        this.limit = limit;
    }


    //METHODS
    @Override
    public boolean equals(Object obj) {

        if(this == obj){
            return true;
        }

        if(obj == null){
            return false;
        }

        if(this.getClass() != obj.getClass()){
            return false;
        }

        SpendingLimit other = (SpendingLimit) obj;
        return this.category.equals(other.category) &&
                Double.compare(this.limit, other.limit) == 0;
    }

    public String toString(){
        return "Category: " + getCategory() + "\n" +
                "Limit: " + getLimit();
    }
}
