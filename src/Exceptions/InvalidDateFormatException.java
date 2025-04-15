package Exceptions;
// -------------------------------------------------------
// Final Project
// Written by: (include your name and student id)
// For “Programming 2” Section (include number)– Winter 2025
// --------------------------------------------------------
public class InvalidDateFormatException extends Exception{

    //CONSTRUCTOR
    //default
    public InvalidDateFormatException(){
        super();
    }
    //parameterized
    public InvalidDateFormatException(String message){
        super(message);
    }
}
