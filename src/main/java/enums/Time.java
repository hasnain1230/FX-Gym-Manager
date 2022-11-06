package enums;

/**
 * Time enum which defines morning (9:30), afternoon(14:00), and evening(18:30). Used for fitness class.
 * @author Hasnain Ali, Carolette Saguil
 */
public enum Time {
    /**
     * "9:30"
     */
    MORNING("9:30"),
    /**
     * "14:00"
     */
    AFTERNOON("14:00"),
    /**
     * "18:30"
     */
    EVENING("18:30");


    private final String TIME;

    /**
     * @param time Fitness Class Time
     */
    private Time(String time) {
        this.TIME = time;
    }

    /**
     * @param time Time in format hr:min as a string.
     * @return Returns the corresponding Time enum of the {@code time}.
     */
    public static Time returnTimeFromString(String time) {
        if (time.equalsIgnoreCase("9:30")) {
            return Time.MORNING;
        } else if (time.equalsIgnoreCase("14:00")) {
            return Time.AFTERNOON;
        } else if (time.equalsIgnoreCase("18:30")) {
            return Time.EVENING;
        } else {
            throw new IllegalArgumentException("Time: " + time + "not valid.");
        }
    }

    /**
     * @param time Time of day as a string.
     * @return Returns the corresponding Time enum of the {@code time}.
     */
    public static Time returnTimeEnumFromTimeOfDay(String time) {
        if (time.equalsIgnoreCase("morning")) {
            return Time.MORNING;
        } else if (time.equalsIgnoreCase("afternoon")) {
            return Time.AFTERNOON;
        } else if (time.equalsIgnoreCase("evening")) {
            return Time.EVENING;
        } else {
            throw new IllegalArgumentException("Time: " + time + "not valid.");
        }
    }

    /**
     * @return Returns Fitness Class time.
     */
    public final String getTime() {
        return this.TIME;
    }

    /**
     * @return Returns string representation of time in the format Time: time.
     */
    @Override
    public final String toString() {
        return String.format("Time: %s", this.TIME);
    }
}
