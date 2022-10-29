import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;

public class Question2 {
    String line = "";
    String splitColumnBy = ",";
    ArrayList<Movie> movieList = new ArrayList<>();
    HashMap<Integer, Integer> yearMap = new HashMap<>();
    HashMap<String, Integer> wordMap = new HashMap<>();
    HashMap<String, Integer> categoryMap = new HashMap<>();

    public Question2(int threadsCount, boolean printResult) {
        try {

            BufferedReader br = new BufferedReader(new FileReader("./data/movies.csv"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] movie = line.split(splitColumnBy);    // use comma as separator
                Movie m = new Movie(movie);
                if (m.isCorrectRecord()) {
                    movieList.add(m);
                }
            }
        } catch (IOException e) {
            System.err.println("There is a problem to read the file!");
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

        for (ProcessThread thread : threads) {
            compineCategoryMap(thread.getCategoryMap());
            compineYearMap(thread.getYearMap());
            compineWordsMap(thread.getWordMap());
        }

        long end = System.currentTimeMillis();
        System.out.println("Duration for " + threadsCount + " threads: " + (end - start) + "msec");
        System.out.println("============END " + threadsCount + " THREAD============");

//        Print data if is true from main
        if (printResult) {
            this.printData();
        }
    }

    public HashMap<String, Integer> sortWordMap(HashMap<String, Integer> hashmap) {
        ArrayList<Word> wordArrayList = new ArrayList<>();
        for (String item : hashmap.keySet()) {
            wordArrayList.add(new Word(item, hashmap.get(item)));
        }

        wordArrayList.sort(new Comparator<Word>() {
            public int compare(Word d1, Word d2) {
                if (d1.getWordCount() == d2.getWordCount()) {
                    return 0;
                }
                return d1.getWordCount() > d2.getWordCount() ? -1 : 1;
            }
        });

        HashMap<String, Integer> sortWordMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            sortWordMap.put(wordArrayList.get(i).getWord(), wordArrayList.get(i).getWordCount());
        }
        return sortWordMap;

    }

    public void printData() {
//        Print Gernes List
        System.out.println("=======GERNE=========");
        for (String category : categoryMap.keySet()) {
            System.out.println("Gerne: " + category + ", has " + categoryMap.get(category) + " movies");
        }
        System.out.println("=======YEARS=========");
//        Print years list
        for (int year : yearMap.keySet()) {
            System.out.println("Year: " + year + ", has " + yearMap.get(year) + " movies");
        }
//        Get Sort Word List for 10 words
        wordMap = sortWordMap(wordMap);
//        Print top 10 words
        System.out.println("=====TOP USED 10 WORDS=====");
        for (String word : wordMap.keySet()) {
            System.out.println("Word: " + word + " has " + wordMap.get(word) + " movies");
        }
    }

    public void compineWordsMap(HashMap<String, Integer> tempWordMap) {
        for (String tempWord : tempWordMap.keySet()) {
            int wordCount = tempWordMap.get(tempWord);
            if (wordMap.containsKey(tempWord)) {
                wordCount = tempWordMap.get(tempWord) + wordMap.get(tempWord);
            }
            wordMap.put(tempWord, wordCount);
        }
    }

    public void compineCategoryMap(HashMap<String, Integer> tempCategoryMap) {
        for (String tempCategory : tempCategoryMap.keySet()) {
            int categoryCount = tempCategoryMap.get(tempCategory);
            if (categoryMap.containsKey(tempCategory)) {
                categoryCount = tempCategoryMap.get(tempCategory) + categoryMap.get(tempCategory);
            }
            categoryMap.put(tempCategory, categoryCount);
        }
    }

    public void compineYearMap(HashMap<Integer, Integer> tempYearMap) {
        for (int tempYear : tempYearMap.keySet()) {
            int yearCount = tempYearMap.get(tempYear);
            if (yearMap.containsKey(tempYear)) {
                yearCount = tempYearMap.get(tempYear) + yearMap.get(tempYear);
            }
            yearMap.put(tempYear, yearCount);
        }
    }
}
