// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.*;
public class HandleFileImplement implements FileHandler{

    //creating the text file to store the user's transactions
    @Override
    public void saveToFile(String file, ArrayList<Transaction> transactionList) throws IOException{
        try{
            PrintWriter printNumber = new PrintWriter(new FileOutputStream(file));

            for(Transaction t : transactionList){
                if(t instanceof Expense){
                    Expense expense = (Expense) t;
                    printNumber.println("EXPENSE: " + expense.getDescription() + ", " +
                            expense.getAmount() + ", " + expense.getDate() + ", " +
                            expense.getCategory());
                }else{
                    printNumber.println("INCOME: " + t.getDescription() + ", " +
                            t.getAmount() + ", " + t.getDate());
                }
            }
            System.out.println("File created successfully");
            printNumber.close();
        }catch (IOException iOE){
            System.out.println("file not created. something went wrong");
        }
    }

    //retrieve the data from the text file
    @Override
    public ArrayList<Transaction> loadFromFile(String file) throws IOException{
        ArrayList<Transaction> transactions = new ArrayList<>();
        try{
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String fileContent;
            while ((fileContent = readFile.readLine()) != null){
                String[] fileData = fileContent.split(",");
                if(fileData[0].equals("INCOME")) {
                    String fileDescription = fileData[1];
                    double fileSalary = Double.parseDouble(fileData[2]);
                    LocalDate fileDate = LocalDate.parse(fileData[3]);
                    transactions.add(new Income(fileDescription, fileSalary, fileDate));
                }else {
                    String fileDescription = fileData[1];
                    double fileExpense = Double.parseDouble(fileData[2]);
                    LocalDate fileDate = LocalDate.parse(fileData[3]);
                    Expense.ExpenseCategory fileCategory = Expense.ExpenseCategory.valueOf(fileData[4]);
                    transactions.add(new Expense(fileDescription, fileExpense, fileDate, fileCategory));
                }
            }
            readFile.close();
        }catch (IOException iOE){
            System.out.println("file not found.");
        }
        return transactions;
    }
}
