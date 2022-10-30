import java.util.ArrayList;
import java.util.Arrays;

public class Movie {
    private int id;
    private String title;
    private int year;
    private ArrayList<String> categories = new ArrayList<>();
    private final String splitYearBy = "\\),\\(|\\)|\\("; // Regex for parenthesis
    private final String splitCategoryBy = "\\|";
    private boolean correctRecord = false;

    public int getYear() {
        return year;
    }

    //  Get the title variable
    public String getTitle() {
        return title;
    }

    //  Split the words of the title to lowercase
    public String[] getWordsTitle() {
        return getTitle().toLowerCase().split("[\s(.,;)]+");
    }

    public Movie(String[] movie) {
        try {
//          Get the id of movie
            this.id = (int) Integer.parseInt(movie[0]);
            String categories;
//      For movies that the title has "," | Size > 3
            if (movie.length > 3) {
                //                Merge the title
                this.title = "";
                for (int i = 1; i < movie.length - 1; i++) {
                    this.title += movie[i] + ",";
                }
//                Remove last ","
                this.title = this.title.substring(0, this.title.length() - 1);
//                Save the categories to string
                categories = movie[movie.length - 1];

            } else {
                //  For movies has Size: 3 that the title not contains ","
                this.title = movie[1];
                categories = movie[2];
            }

//        Get year and remove it from title
            String[] splitYear = this.title.split(splitYearBy);
            for (String token : splitYear) {
                if (isNumeric(token)) {
                    this.year = Integer.parseInt(token);
                    this.title = this.title.replace(" (" + token + ")", "");
                    this.title = this.title.replace("\"", "");
                }
            }

//        Split Categories String and add them to arraylist
            String[] splitCategories = categories.split(splitCategoryBy);
            this.categories.addAll(Arrays.asList(splitCategories));
//            If the record is correct to add it to the movie list at Question2 class
            correctRecord = true;
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Record is not valid!!! " + movie[0]);
        }
    }

    //    Get correctRecord value
    public boolean isCorrectRecord() {
        return correctRecord;
    }

    //    Check if the string is a number
    private boolean isNumeric(String strNum) {
//        Check if null
        if (strNum == null) {
            return false;
        }
//        Check if have space | Correct value from movies.csv is: "(1999)"
        if (strNum.contains(" ")) {
            return false;
        }

        try {
//            Check if can be a number
            double d = Double.parseDouble(strNum);
//            Check if the string contains the word infinity
            if (Double.isInfinite(d)) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    //    For DEBUG to check the movies list
    public void printMovie() {
        System.out.print("ID: " + this.id + ", Title: " + this.title + ", Year: " + this.year + ", Categories:  ");
        for (String cat : this.categories) {
            System.out.print(cat + " | ");
        }
        System.out.println("");
    }

    //    Get the categories arraylist
    public ArrayList<String> getCategories() {
        return categories;
    }
}
