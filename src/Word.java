public class Word {
    private String word;
    private int wordCount=1;

    public Word(String word, int wordCount) {
        this.word = word;
        this.wordCount = wordCount;
    }

    public String getWord() {
        return this.word;
    }

    public int getWordCount() {
        return wordCount;
    }
}
