// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
import java.util.ArrayList;
interface FileHandler {

    //METHODS
    public void saveToFile(); // interface method (does not have a body)
    public ArrayList<Transaction> loadFromFile(String fileName);
}
