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
//          Read the movies.csv file from data folder
            BufferedReader br = new BufferedReader(new FileReader("./data/movies.csv"));
//            Read all the lines of the file
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] movie = line.split(splitColumnBy);
//                Save the values to movie class
                Movie m = new Movie(movie);
//                Save the movie class to movie list
                if (m.isCorrectRecord()) {
                    movieList.add(m);
                }
            }
        } catch (IOException e) {
//            Print the error when is unavailable to read the file
            System.err.println("There is a problem to read the file!");
            e.printStackTrace();
        }
//      Calc the batchsize
        int batchSize = movieList.size() / threadsCount;
        //      Create threads array
        ProcessThread[] threads = new ProcessThread[threadsCount];
//      Get time in msec before the threads start to process
        long start = System.currentTimeMillis();
//      Pass the data to threads
        for (int i = 0; i < threads.length; i++) {
            if (i == threads.length - 1) {
//                Add the result of module divide to calculate all the rows
                threads[i] = new ProcessThread(movieList, i * batchSize, (batchSize + movieList.size() % threadsCount));
            } else {
//                All threads calculate the same batch size expect the last one
                threads[i] = new ProcessThread(movieList, i * batchSize, batchSize);
            }
//            Run the thread
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
//      Merge the hashmaps of each thread
        for (ProcessThread thread : threads) {
            compineCategoryMap(thread.getCategoryMap());
            compineYearMap(thread.getYearMap());
            compineWordsMap(thread.getWordMap());
        }
//      Get the time of finish the thread process in msec
        long end = System.currentTimeMillis();
        System.out.println("Duration for " + threadsCount + " threads: " + (end - start) + "msec");
        System.out.println("============END " + threadsCount + " THREAD============");

//      Print data if is true from main
        if (printResult) {
            this.printData();
        }
    }

    //    Sort the word map to get the top 10 used words
    public HashMap<String, Integer> sortWordMap(HashMap<String, Integer> hashmap) {
//        Add the data od the hashmap to arraylist
        ArrayList<Word> wordArrayList = new ArrayList<>();
        for (String item : hashmap.keySet()) {
            wordArrayList.add(new Word(item, hashmap.get(item)));
        }

//        Sort the arraylist by value
        wordArrayList.sort(new Comparator<Word>() {
            public int compare(Word d1, Word d2) {
                if (d1.getWordCount() == d2.getWordCount()) {
                    return 0;
                }
                return d1.getWordCount() > d2.getWordCount() ? -1 : 1;
            }
        });

//        Add the data of the arraylist to hashmap
        HashMap<String, Integer> sortWordMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            sortWordMap.put(wordArrayList.get(i).getWord(), wordArrayList.get(i).getWordCount());
        }
//      Return the new sorted hashmap
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
//        Loop the new hashmap from thread
        for (String tempWord : tempWordMap.keySet()) {
//            Get the value from hashmap
            int wordCount = tempWordMap.get(tempWord);
//            Check if the key exists to the final hashmap
            if (wordMap.containsKey(tempWord)) {
//                If exists add the value from the final map and the thread map
                wordCount = tempWordMap.get(tempWord) + wordMap.get(tempWord);
            }
//            Update or add the record
            wordMap.put(tempWord, wordCount);
        }
    }

    public void compineCategoryMap(HashMap<String, Integer> tempCategoryMap) {
        //        Loop the new hashmap from thread
        for (String tempCategory : tempCategoryMap.keySet()) {
            //            Get the value from hashmap
            int categoryCount = tempCategoryMap.get(tempCategory);
            //            Check if the key exists to the final hashmap
            if (categoryMap.containsKey(tempCategory)) {
                //                If exists add the value from the final map and the thread map
                categoryCount = tempCategoryMap.get(tempCategory) + categoryMap.get(tempCategory);
            }
            //            Update or add the record
            categoryMap.put(tempCategory, categoryCount);
        }
    }

    public void compineYearMap(HashMap<Integer, Integer> tempYearMap) {
        //        Loop the new hashmap from thread
        for (int tempYear : tempYearMap.keySet()) {
            //            Get the value from hashmap
            int yearCount = tempYearMap.get(tempYear);
            //            Check if the key exists to the final hashmap
            if (yearMap.containsKey(tempYear)) {
                //                If exists add the value from the final map and the thread map
                yearCount = tempYearMap.get(tempYear) + yearMap.get(tempYear);
            }
            //            Update or add the record
            yearMap.put(tempYear, yearCount);
        }
    }
}
