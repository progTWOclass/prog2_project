// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import java.util.ArrayList;
import java.io.*;
public class HandleFileImplement implements FileHandler{
    @Override
    public void saveToFile(String file, ArrayList<Transaction> transactionList) throws Exception {
        try{
            PrintWriter printNumber = new PrintWriter(new FileOutputStream(file));

            for(Transaction t : transactionList){
                printNumber.println(t.getSummary());
            }

            System.out.println("File created successfully");

        }catch (IOException iOE){
            System.out.println("file not created. something went wrong");
        }
    }


    @Override
    public ArrayList<Transaction> loadFromFile(String file) throws Exception{
        ArrayList<Transaction> transactions = new ArrayList<>();
        try{
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String fileContent = null;
            while ((fileContent = readFile.readLine()) != null){
                System.out.println(fileContent);
            }

        }catch (IOException iOE){
            System.out.println("file not found.");
        }
        return transactions;
    }
}
