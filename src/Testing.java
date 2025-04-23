import Exceptions.InvalidAmountException;
import Exceptions.TransactionNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Testing {
    private static FinanceManager manager = new FinanceManager();
    private static HandleFileImplement file = new HandleFileImplement();
    private static Scanner input = new Scanner(System.in);

    //used to verify if the user enters acceptable characters for the description
    private static String verifyDescription(Scanner input) {
        while (true) {
            String description = input.nextLine().trim();//remove beginning and ending whitespaces
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty.");
                continue;
            } else if (description.length() > 50) {
                System.out.println("Description is too long (Max: 50 characters)");
                continue;
            } else if (!description.matches("^[a-zA-Z0-9$#\\s]+$")) {
                System.out.println("Description can only contain letters, numbers, spaces, $, and #");
                continue;
            } else if (!description.matches(".*[a-zA-Z].*")) {
                System.out.println("Description must include at least one letter.");
                continue;
            }
            return description;
        }
    }

    //used to verify if the user enters a legal number for their salary/expense
    private static double verifyAmount(Scanner input){
        while(true) {
            try {
                double salary = input.nextDouble();
                input.nextLine();//clear the buffer
                if (salary <= 0) {
                    System.out.println("Salary cannot be negative");
                    continue;
                }
                return salary;
            }catch (InputMismatchException iME){
                System.out.println("Cannot contain characters. It must only be numbers.");
                input.nextLine();//clear the input
            }
        }
    }

    //used to check if the date is valid
    private static LocalDate verifyDate(Scanner input){
        while (true) {
            System.out.println("Enter date (YYYY-MM-DD):");
            try {
                return LocalDate.parse(input.nextLine().trim());
            } catch (DateTimeException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }

    //used to allow the user to choose his/her category
    private static Expense.ExpenseCategory chooseCategory(Scanner input){
        Expense.ExpenseCategory[] categories = Expense.ExpenseCategory.values();//store the enums values into an array

        //display the category options
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        int categoryChoice;
        while (true) {
            try {
                System.out.println("Please choose your category");
                categoryChoice = input.nextInt();
                input.nextLine();//clear the buffer

                // Validate category choice
                if (categoryChoice < 1 || categoryChoice > categories.length) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException iME) {
                System.out.println("Invalid choice. Please try again");
                input.nextLine();
            }
        }
        //pick the correct category
        return categories[categoryChoice - 1];
    }

    //make sure the user choose the available options 1,2, or 3
    private static int verifyChoice(Scanner input, int min, int max){
        while(true){
            try{
                int choice = input.nextInt();
                input.nextLine();//clear the buffer
                if (choice < min || choice > max) {
                    throw new InputMismatchException();
                }
                return choice;
            }catch(InputMismatchException iME){
                System.out.println("Invalid option. Please enter 1, 2, or 3");
                input.nextLine();//clear the previous input
            }
        }
    }


    public static void addNewTransaction() {
        System.out.print("""
                Please select a new transaction
                [1] Income
                [2] Expense
                [3] Return to menu
                """);
        int choice = verifyChoice(input, 1,3);

        if(choice == 1){
            System.out.println("Please enter a description");
            String description = verifyDescription(input);

            System.out.println("Please enter a salary");
            double salary = verifyAmount(input);

            System.out.println("Please enter a date");
            LocalDate date = verifyDate(input);

            Income income = new Income(description, salary, date);
            manager.addTransaction(income);
            System.out.println("Transaction registered successfully");

        }
        else if (choice == 2) {
            System.out.println("Please enter a description");
            String description = verifyDescription(input);

            System.out.println("Please enter expense amount");
            double expense = verifyAmount(input);

            System.out.println("Please enter a year");
            LocalDate date = verifyDate(input);

            System.out.println("Please select expense category:");
            Expense.ExpenseCategory category = chooseCategory(input);

            Expense expenses = new Expense(description, expense, date, category);
            manager.addTransaction(expenses);
            System.out.println("Transaction registered successfully");
        }
        else {
            System.out.println("Exiting...");
            return;
        }
    }

    public static void findTransactionDescription() throws TransactionNotFoundException {
        System.out.println("Please enter a keyword in your description");
        String keyword = verifyDescription(input);
        Transaction object = manager.searchTransactionByDescription(keyword);

        System.out.println("Here is your transaction summary\n" + object.getSummary());
        System.out.print("""
                Please enter an action you wish to perform
                [1] modify the transaction
                [2] delete the transaction
                [3] return to main menu
                """);
        int choiceAction = verifyChoice(input, 1,3);

        if(choiceAction == 1){
            if(object instanceof Income){
                System.out.print("""
                Which information do you wish to update
                [1] description
                [2] salary
                [3] date
                """);
                int choiceModify;
                try{
                    choiceModify = verifyChoice(input, 1,3);
                    if(choiceModify == 1){
                        System.out.println("Please enter your new description");
                        String newDescription = verifyDescription(input);
                        object.setDescription(newDescription);
                        System.out.println("Description updated successfully");

                    } else if (choiceModify == 2) {
                        System.out.println("Please enter your new salary");
                        double updateSalary = verifyAmount(input);
                        object.setAmount(updateSalary);
                        System.out.println("Salary updated successfully");

                    }else{
                        System.out.println("Please enter a new year");
                        LocalDate updateDate = verifyDate(input);
                        object.setDate(updateDate);
                        System.out.println("Date updated successfully");

                    }
                }catch (InputMismatchException iME){
                    System.out.println("Invalid option. Please try again");
                }
            } else if (object instanceof Expense) {
                System.out.print("""
                Which information do you wish to update
                [1] description
                [2] salary
                [3] date
                [4] category
                """);
                int choiceModify;
                try{
                    choiceModify = verifyChoice(input, 1,4);
                    if(choiceModify == 1){
                        System.out.println("Please enter your new description");

                        String newDescription = verifyDescription(input);
                        object.setDescription(newDescription);
                        System.out.println("Description updated successfully");

                    } else if (choiceModify == 2) {
                        System.out.println("Please enter your new expense");
                        double updateSalary = verifyAmount(input);
                        object.setAmount(updateSalary);
                        System.out.println("Expense updated successfully");

                    }else if (choiceModify == 3){
                        System.out.println("Please enter a new year");
                        LocalDate updateDate = verifyDate(input);
                        object.setDate(updateDate);
                        System.out.println("Date updated successfully");

                    }else{
                        System.out.println("Please select a new category");
                        Expense.ExpenseCategory updateCategory = chooseCategory(input);
                        ((Expense) object).setCategory(updateCategory);
                        System.out.println("Category updated successfully");

                    }
                }catch (InputMismatchException iME){
                    System.out.println("Invalid option. Please try again");
                }
            }
        }
        else if (choiceAction == 2) {
            System.out.println("Are you sure you want to delete this transaction? (yes/no)");
            while(true) {
                String choice = input.nextLine();
                if (choice.equalsIgnoreCase("yes")) {
                    try {
                        boolean checkRemove = manager.removeTransaction(keyword);
                        if (checkRemove) {
                            System.out.println("Transaction deleted successfully");
                        } else {
                            System.out.println("Operation canceled. Transaction not found");
                        }
                        break;
                    } catch (TransactionNotFoundException tNFE) {
                        System.out.println("Transaction does not exist");
                        break;
                    }
                } else if (choice.equalsIgnoreCase("no")){
                    System.out.println("Operation canceled");
                    break;
                }else{
                    System.out.println("Invalid operation. please enter yes or no");
                }
            }
        }
    }

    public static void displayTransaction(){
        for(Transaction t : manager.getTransactions()){
            System.out.println(t.getSummary());
        }
    }

    public static void calculateMoney(){
        System.out.println("Your total income");
        System.out.println(manager.getTotalIncome());
        System.out.println("Your total expense");
        System.out.println(manager.getTotalExpense());
        System.out.println("Your current balance");
        System.out.println(manager.getBalance());
    }

    public static void saveTransactionFile() throws IOException {
        while(true) {
            System.out.println("Please enter a name for your file");
            String fileName = input.nextLine();

            if (fileName.isEmpty()) {
                System.out.println("Cannot have an empty file name");
                continue;
            }
            if (!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }
            if(manager.getTransactions().isEmpty()){
                System.out.println("No transactions to save. Please add transactions first.");
                return;
            }
            System.out.println("Do you wish to save your transaction to " + fileName + "(yes/no)");
            String decision = input.nextLine().trim();
            if (decision.equalsIgnoreCase("yes")) {
                file.saveToFile(fileName, manager.getTransactions());
                System.out.println("Your transaction has been successfully saved ");
                break;
            } else if (decision.equalsIgnoreCase("no")) {
                System.out.println("you have cancelled your transaction");
                break;
            } else {
                System.out.println("invalid option. Please type yes or no");
            }
        }
    }

//    public static void loadTransactionFile() throws IOException {
//        System.out.println("Please enter the name of the file you want to load");
//        String loadFile = input.nextLine().trim();
//        File fileExist = new File(loadFile);
//        if (!fileExist.exists()) {
//            System.out.println("No existing data file. Starting fresh.");
//            return;
//        }
//        if (!loadFile.endsWith(".txt")) {
//            loadFile += ".txt";
//        }
//        file.loadFromFile(loadFile);
//    }
public static void loadTransactionFile() {
    while (true) {
        try {
            System.out.println("\nEnter file name to load (or 'cancel' to abort):");
            String loadFile = input.nextLine().trim();

            // Allow user to cancel operation
            if (loadFile.equalsIgnoreCase("cancel")) {
                System.out.println("Load operation cancelled.");
                return;
            }

            // Validate file name
            if (loadFile.isEmpty()) {
                System.out.println("Error: File name cannot be empty");
                continue;
            }

            // Add .txt extension if missing
            if (!loadFile.endsWith(".txt")) {
                loadFile += ".txt";
            }

            // Check file existence
            File fileToLoad = new File(loadFile);
            if (!fileToLoad.exists()) {
                System.out.println("Error: File '" + loadFile + "' does not exist");
                System.out.println("Please check the file name and try again");
                continue;
            }

            // Check empty file
            if (fileToLoad.length() == 0) {
                System.out.println("Warning: The file is empty. No transactions loaded.");
                return;
            }

            // Confirm before loading
            System.out.printf("Load transactions from '%s'? (yes/no): ", loadFile);
            String confirmation = input.nextLine().trim().toLowerCase();

            if (!confirmation.equals("yes")) {
                System.out.println("Load operation cancelled.");
                return;
            }

            // Perform the load operation
            ArrayList<Transaction> loadedTransactions = file.loadFromFile(loadFile);
            manager.setTransactions(loadedTransactions);
            System.out.printf("Successfully loaded %d transactions\n", loadedTransactions.size());
            break;

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Please try again.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return;
        }
    }
}

        public static void setSpendingLimit(){

    }

    public static void main(String[] args) {
        boolean continueLoop = true;
        while(continueLoop) {
            System.out.println("""
                    \n===== Online Banking System =====
                    1. Add New Transaction
                    2. Search Transaction by Description
                    3. Display All Transactions
                    4. Calculate Total Income / Expense / Balance
                    5. Save Transactions to File
                    6. Load Transactions from File
                    7. Set Spending Limit
                    8. Exit
                    """);
            System.out.println("please enter your choice:");
            try {
                int commandChoice = input.nextInt();
                input.nextLine();//clears the buffer
                switch (commandChoice) {
                    case 1:
                        addNewTransaction();
                        break;//exit the switch statement
                    case 2:
                        findTransactionDescription();
                        break;
                    case 3:
                        displayTransaction();
                        break;
                    case 4:
                        calculateMoney();
                        break;
                    case 5:
                        saveTransactionFile();
                        break;
                    case 6:
                        loadTransactionFile();
                        break;
//                    case 7:
//                        s();
//                        break;
                    case 8:
                        System.out.println("Thank you for using our banking system \nGoodbye");
                        continueLoop = false;//stops the while loop
                        break;
                    default:
                        System.out.println("Invalid command");
                }
            }catch (InputMismatchException iME){
                System.out.println("Invalid command. PLease try again");
                input.next();
            } catch (IOException iOE){
                System.out.println("Your transaction has not been saved because something is wrong with the file");
            }catch (TransactionNotFoundException e) {
                System.out.println("transaction not found");;
            }
        }
    }
}
