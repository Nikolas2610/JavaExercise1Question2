public class Word {
    private String word;
    private int wordCount=1;;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void increaseWordCount() {
        this.wordCount++;
    }

    public void printWord() {
        System.out.println("Word " + getWord() + ": has " + getWordCount() + " appearances");
    }
}
