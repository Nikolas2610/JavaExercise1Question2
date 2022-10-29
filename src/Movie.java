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

    public int getId() {    // TODO: For DEBUG
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getWordsTitle() {
        return getTitle().toLowerCase().split("[\s(.,;)]+");
    }

    public Movie(String[] movie) {

        try {
            this.id = (int)Integer.parseInt(movie[0]);

            String categories;

//      For movies that the title has "," | Size: 4
            if (movie.length > 3) {
                this.title = "";
                for (int i = 1; i < movie.length-1; i++) {
                    this.title += movie[i] + ",";
                }
                this.title = this.title.substring(0, this.title.length()-1);
//                this.title = movie[1] + "," + movie[2];
//                categories = movie[3];
                categories = movie[movie.length-1];

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
                    this.title = this.title.replace("\"","");
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
        if (strNum.contains(" ")) {
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

    public ArrayList<String> getCategories() {
        return categories;
    }
}
