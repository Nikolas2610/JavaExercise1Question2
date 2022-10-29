import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Question2 {
    String line = "";
    String splitColumnBy = ",";

    ArrayList<Movie> movieList = new ArrayList<>();
    ArrayList<Category> categoryList = new ArrayList<>();
    ArrayList<Year> yearList = new ArrayList<>();
    ArrayList<Word> wordList = new ArrayList<>();

//    int counter = 0;

    {
        try {

            BufferedReader br = new BufferedReader(new FileReader("./data/movies.csv"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
//                if (counter > 2000) {
//                    System.out.println("Finish Reading");
//                    break;
//                } else {
//                    counter++;
//                }
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
//        for (Movie movie : movieList) {
//            movie.printMovie();
//        }


        for (Movie movie : movieList) {
            //        Add categories List
            ArrayList<String> tempCategories = movie.getCategories();
            for (String tempCat : tempCategories) {
                if (categoryList.isEmpty()) {
                    categoryList.add(new Category(tempCat));
                } else {
                    boolean categoryExists = false;
                    for (Category category : categoryList) {
                        if (category.getName().equals(tempCat)) {
                            category.increaseMovieCount();
                            categoryExists = true;
                            break;
                        }
                    }
                    if (!categoryExists) {
                        categoryList.add(new Category(tempCat));
                    }
                }
            }
        }

//        Add by year count
        for (Movie movie : movieList) {
            int tempYear = movie.getYear();
            if (yearList.isEmpty()) {
                yearList.add(new Year(tempYear));
            } else {
                boolean yearExists = false;
                for (Year yearFromList : yearList) {
                    if (yearFromList.getYear() == tempYear) {
                        yearFromList.increaseYearCount();
                        yearExists = true;
                        break;
                    }
                }
                if (!yearExists) {
                    yearList.add(new Year(tempYear));
                }
            }
        }


        for (Movie movie : movieList) {
            String[] wordsArray = movie.getWordsTitle();
            for (String tempWord : wordsArray) {
                if (wordList.isEmpty()) {
                    wordList.add(new Word(tempWord));
                } else {
                    boolean wordExists = false;
                    for (Word tempWordList : wordList) {
                        if (tempWord.equals(tempWordList.getWord())) {
                            wordExists = true;
                            tempWordList.increaseWordCount();
                            break;
                        }
                    }
                    if (!wordExists) {
                        wordList.add(new Word(tempWord));
                    }
                }
            }
        }

        for (Category cat: categoryList) {
            cat.printCategory();
        }

        for (Year year: yearList) {
            year.printYear();
        }


        sortWordList();
        int counterWords = 0;
        for (Word word : wordList) {
            word.printWord();
            if (counterWords > 10) {
                break;
            } else {
                counterWords++;
            }
        }


    }

    public void sortWordList() {
        wordList.sort((d1, d2) -> {
            if (d1.getWordCount() == d2.getWordCount()) {
                return 0;
            }
            return d1.getWordCount() > d2.getWordCount() ? -1 : 1;
        });
    }

}
