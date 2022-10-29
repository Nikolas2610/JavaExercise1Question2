import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Question2 {
    String line = "";
    String splitColumnBy = ",";

    ArrayList<Movie> movieList = new ArrayList<>();

    ArrayList<Year> yearList = new ArrayList<>();
    ArrayList<Category> categoryList = new ArrayList<>();
    ArrayList<Word> wordList = new ArrayList<>();

//    int counter = 0;

    public Question2(int threadsCount, boolean printResult) {
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

        int batchSize = movieList.size() / threadsCount;
        //      Create threads array
        ProcessThread[] threads = new ProcessThread[threadsCount];
        long start = System.currentTimeMillis();
        System.out.println("Here: " + movieList.size());
        for (int i = 0; i < threads.length; i++) {
            if (i == threads.length - 1) {
                threads[i] = new ProcessThread(movieList, i * batchSize, (batchSize + movieList.size() % threadsCount));
            } else {
                threads[i] = new ProcessThread(movieList, i * batchSize, batchSize);
            }
            threads[i].start();
        }

        //      Wait the threads to finish
        for (ProcessThread thread : threads) {
            try {
                // Wait for the threads to finish.
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < threads.length; i++) {
            if (i == 0) {
                wordList.addAll(threads[i].getWordList());
                categoryList.addAll(threads[i].getCategoryList());
                yearList.addAll(threads[i].getYearList());
            } else {
                compineWordsList(threads[i].getWordList());
                compineCategoryList(threads[i].getCategoryList());
                compineYearList(threads[i].getYearList());
            }
        }


        long end = System.currentTimeMillis();
        System.out.println("Duration for " + threadsCount + " threads: " + (end - start) + "msec");
        System.out.println("============END " + threadsCount + " THREAD============");
        if (printResult) {
            this.printData();
        }

//        Print movies
//        for (Movie movie : movieList) {
//            movie.printMovie();
//        }


    }

    public void sortWordList() {
        wordList.sort((d1, d2) -> {
            if (d1.getWordCount() == d2.getWordCount()) {
                return 0;
            }
            return d1.getWordCount() > d2.getWordCount() ? -1 : 1;
        });
    }

    public void printData() {
        //        Print Gernes
        for (Category cat : categoryList) {
            cat.printCategory();
        }

//        Print Years
        for (Year year : yearList) {
            year.printYear();
        }

//        Print Top 10 words
        sortWordList();
        for (int i = 0; i < 10; i++) {
            wordList.get(i).printWord();
        }
    }

    public void compineWordsList(ArrayList<Word> threadWordList) {
        for (Word word1 : threadWordList) {
            boolean wordExists = false;
            for (Word word2 : wordList) {
                if (word1.getWord().equals(word2.getWord())) {
                    word2.addWordsCount(word1.getWordCount());
                    wordExists = true;
                    break;
                }
            }
            if (!wordExists) {
                wordList.add(word1);
            }
        }
    }

    public void compineCategoryList(ArrayList<Category> threadCategoryList) {
        for (Category category1 : threadCategoryList) {
            boolean categoryExists = false;
            for (Category category2 : categoryList) {
                if (category1.getName().equals(category2.getName())) {
                    category2.addMovies(category1.getMoviesCount());
                    categoryExists = true;
                    break;
                }
            }
            if (!categoryExists) {
                categoryList.add(category1);
            }
        }
    }

    public void compineYearList(ArrayList<Year> threadYearList) {
        for (Year year1 : threadYearList) {
            boolean categoryExists = false;
            for (Year year2 : yearList) {
                if (year1.getYear() == year2.getYear()) {
                    year2.addYears(year1.getYearCount());
                    categoryExists = true;
                    break;
                }
            }
            if (!categoryExists) {
                yearList.add(year1);
            }
        }
    }

}
