import java.util.ArrayList;
import java.util.HashMap;

public class ProcessThread extends Thread {
    ArrayList<Movie> movieList;
    private final int start;
    private final int batchSize;
    HashMap<Integer, Integer> yearMap = new HashMap<>();
    HashMap<String, Integer> wordMap = new HashMap<>();
    HashMap<String, Integer> categoryMap = new HashMap<>();

    public HashMap<Integer, Integer> getYearMap() {
        return yearMap;
    }

    public HashMap<String, Integer> getWordMap() {
        return wordMap;
    }

    public HashMap<String, Integer> getCategoryMap() {
        return categoryMap;
    }

    public ProcessThread(ArrayList<Movie> movieList, int start, int batchSize) {
        this.movieList = movieList;
        this.start = start;
        this.batchSize = batchSize;
    }

    @Override
    public void run() {
//        Run the thread for some rows
        for (int i = this.start; i < (start + this.batchSize); i++) {
            this.seperateGernes(i);
            this.seperateYears(i);
            this.seperateWordsTitle(i);
        }
        System.out.println(getName() + " finished.");
    }

    private void seperateGernes(int i) {
//        Get categories
        ArrayList<String> tempCategories = movieList.get(i).getCategories();
        for (String tempCat : tempCategories) {
            int wordCount = 1;
            //            Check if the key exists
            if (categoryMap.containsKey(tempCat)) {
//            If Key value exists add one to the value
                wordCount = categoryMap.get(tempCat) + 1;
            }
//            Create or update row
            categoryMap.put(tempCat, wordCount);
        }
    }

    private void seperateYears(int i) {
//        Get the year of the row
        int tempYear = movieList.get(i).getYear();
        int yearCount = 1;
        //            Check if the key exists
        if (yearMap.containsKey(tempYear)) {
            //            If Key value exists add one to the value
            yearCount = yearMap.get(tempYear) + 1;
        }
        //            Create or update row
        yearMap.put(tempYear, yearCount);
    }

    private void seperateWordsTitle(int i) {
//        Get the words of row
        String[] wordsArray = movieList.get(i).getWordsTitle();
        for (String tempWord : wordsArray) {
            int wordCount = 1;
//            Check if the key exists
            if (wordMap.containsKey(tempWord)) {
                //            If Key value exists add one to the value
                wordCount = wordMap.get(tempWord) + 1;
            }
            //            Create or update row
            wordMap.put(tempWord, wordCount);
        }
    }
}
