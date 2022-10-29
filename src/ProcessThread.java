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
        for (int i = this.start; i < (start + this.batchSize); i++) {
            this.seperateGernes(i);
            this.seperateYears(i);
            this.seperateWordsTitle(i);
        }
        System.out.println(getName() + " finished.");
    }

    private void seperateGernes(int i) {
        ArrayList<String> tempCategories = movieList.get(i).getCategories();
        for (String tempCat : tempCategories) {
            int wordCount = 1;
            if (categoryMap.containsKey(tempCat)) {
                wordCount = categoryMap.get(tempCat) + 1;
            }
            categoryMap.put(tempCat, wordCount);
        }
    }

    private void seperateYears(int i) {
        int tempYear = movieList.get(i).getYear();
        int yearCount = 1;
        if (yearMap.containsKey(tempYear)) {
            yearCount = yearMap.get(tempYear) + 1;
        }
        yearMap.put(tempYear, yearCount);
    }

    private void seperateWordsTitle(int i) {
        String[] wordsArray = movieList.get(i).getWordsTitle();
        for (String tempWord : wordsArray) {
            int wordCount = 1;
            if (wordMap.containsKey(tempWord)) {
                wordCount = wordMap.get(tempWord) + 1;
            }
            wordMap.put(tempWord, wordCount);
        }
    }
}
