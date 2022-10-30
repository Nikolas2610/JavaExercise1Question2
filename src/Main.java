import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
//        At Question 2 Class add true of false at the second argument to print the data
//        Call Exercise for 1 Thread
        Question2 q1 = new Question2(1, true);
        //        Call Exercise for 2 Thread
        Question2 q2 = new Question2(2, true);
        //        Call Exercise for 4 Thread
        Question2 q3 = new Question2(4, true);
        //        Call Exercise for 8 Thread
        Question2 q4 = new Question2(8, true);
    }
}