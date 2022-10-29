public class Category {
    private final String name;
    private long moviesCount=1;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getMoviesCount() {
        return moviesCount;
    }

    public void increaseMovieCount() {
        this.moviesCount++;
    }

    public void printCategory() {
        System.out.println("Category " + getName() + ": has " + getMoviesCount() + " movies");
    }
}
