public class Word {
    private String word;
    private long wordCount=1;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public long getWordCount() {
        return wordCount;
    }

    public void increaseWordCount() {
        this.wordCount++;
    }

    public void addWordsCount(long words) {
        this.wordCount += words;
    }

    public void printWord() {
        System.out.println("Word " + getWord() + ": has " + getWordCount() + " appearances");
    }
}
