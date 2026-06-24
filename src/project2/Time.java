package project2;

/**
 * Enum class for class periods / time, includes two additional properties - Hour and Minute
 * @author Shivang Patel
 */
public enum Time {
    periodOne(8, 30),
    periodTwo(10, 20),
    periodThree(12, 10),
    periodFour(14, 0),
    periodFive(15, 50),
    periodSix(17, 40);

    private final int hour; // Hour a class begins, uses military time designations
    private final int minute; // Minute in the hour a class begins

    /**
     * Constructor used by JVM to create project2.Time objects defined above
     * @param hour Hour section takes place
     * @param minute minute in hour section takes place
     */
    Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * toString() override method for class
     * @return Returns the period name, hour, and minute in the format periodName, hour:minute
     */
    @Override
    public String toString(){
        return switch (hour) {
            case (8) -> "period 1";
            case (10) -> "period 2";
            case (12) -> "period 3";
            case (14) -> "period 4";
            case (15) -> "period 5";
            default -> "period 6";
        };
    }

    /**
     * Accessor Method for hour
     * @return Returns hour parameter for a given time object as a String
     */
    public int getHour(){
        return hour;
    }

    /**
     * Accessor Method for minute
     * @return Returns minute parameter for a given time object as a String
     */
    public int getMinute(){
        return minute;
    }
}
