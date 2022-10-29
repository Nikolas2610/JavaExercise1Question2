public class Year {
    private final int year;
    private int yearCount=1;

    public Year(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public int getYearCount() {
        return yearCount;
    }

    public void increaseYearCount() {
        this.yearCount++;
    }

    public void printYear() {
        System.out.println("Year " + getYear() + ": has " + getYearCount() + " movies");
    }
}
