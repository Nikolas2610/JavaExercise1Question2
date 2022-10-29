import java.util.ArrayList;

public class ProcessThread extends Thread {
    ArrayList<Movie> movieList;
    private final int start;
    private final int batchSize;

    public ArrayList<Year> getYearList() {
        return yearList;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    ArrayList<Year> yearList = new ArrayList<>();
    ArrayList<Category> categoryList = new ArrayList<>();
   ArrayList<Word> wordList = new ArrayList<>();

    public ProcessThread(ArrayList<Movie> movieList, int start, int batchSize) {
        this.movieList = movieList;
        this.start = start;
        this.batchSize = batchSize;
    }

    @Override
    public void run() {
// TODO:
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

    private void seperateYears(int i) {
        int tempYear = movieList.get(i).getYear();
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

    private void seperateWordsTitle(int i) {
        String[] wordsArray = movieList.get(i).getWordsTitle();
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
}
