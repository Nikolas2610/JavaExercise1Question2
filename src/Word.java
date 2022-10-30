public class Word {
    private String word;
    private int wordCount = 1;

    public Word(String word, int wordCount) {
//        Create word object
        this.word = word;
        this.wordCount = wordCount;
    }

    //    Get word name
    public String getWord() {
        return this.word;
    }

    //    Get word counts
    public int getWordCount() {
        return wordCount;
    }
}
