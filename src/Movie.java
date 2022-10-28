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

    public Movie(String[] movie) {

        try {
            this.id = Integer.parseInt(movie[0]);

            String categories;

//      For movies that the title has "," | Size: 4
            if (movie.length == 4) {
                this.title = movie[1] + "," + movie[2];
                categories = movie[3];
            } else {
                //  Size: 3
                this.title = movie[1];
                categories = movie[2];
            }

//        Get year
            String[] splitYear = this.title.split(splitYearBy);
            for (String token : splitYear) {
                if (isNumeric(token)) {
                    this.year = Integer.parseInt(token);
                    this.title = this.title.replace(" ("+ token + ")","");
                }
            }

//        Split Categories
            String[] splitCategories = categories.split(splitCategoryBy);

            this.categories.addAll(Arrays.asList(splitCategories));
            correctRecord = true;
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Record is not valid!!! " + movie[0]);
        }
    }

    public boolean isCorrectRecord() {
        return correctRecord;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            double d = Double.parseDouble(strNum);
            if (Double.isInfinite(d)) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void printMovie() {
        System.out.print("ID: " + this.id + ", Title: " + this.title + ", Year: " + this.year + ", Categories:  ");
        for (String cat : this.categories) {
            System.out.print(cat + " | ");
        }
        System.out.println("");
    }
}
