import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Question2 {

    //C:\Java\Java22-23\Exercise1-Question2\data\movies.csv

    String line = "";
    String splitColumnBy = ",";

    ArrayList<Movie> movieList = new ArrayList<>();
    int counter = 0;

    {
        try {

            BufferedReader br = new BufferedReader(new FileReader("./data/movies.csv"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                if (counter > 1000) {
                    System.out.println("Finish Reading");
                    break;
                } else {
                    counter++;
                }
                String[] movie = line.split(splitColumnBy);    // use comma as separator
                Movie m = new Movie(movie);
                if (m.isCorrectRecord()) {
                    movieList.add(m);
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

//        Print movies
        for (Movie movie : movieList) {
            movie.printMovie();
        }
    }

}
