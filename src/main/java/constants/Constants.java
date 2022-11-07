package constants;

/**
 * This class stores all the constants used in {@code GymManager} and its dependencies.
 * @author Hasnain Ali, Carolette Saguil
 */
public class Constants {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int MIN_YEAR = 1900;
    public static final int MIN_MONTH = 1;
    public static final int MAX_MONTH = 12;
    public static final int MIN_DAYS = 1;
    public static final int MAX_DAYS_ONE = 30;
    public static final int MAX_DAYS_TWO = 31;
    public static final int MAX_NORM_FEB_DAYS = 28;
    public static final int MAX_LEAP_FEB_DAYS = 29;
    public static final int FEBRUARY = 2;
    public static final int APRIL = 4;
    public static final int JUNE = 6;
    public static final int SEPTEMBER = 9;
    public static final int NOVEMBER = 11;

    public static final int MINIMUM_AGE = 18;

    public static final int ARRAY_DEFAULT_SIZE = 4;
    public static final int ARRAY_INCREMENT_SIZE = 4;

    public static final int NOT_FOUND = -1;

    public static final double STANDARD_ONE_TIME_MEMBER_FEE = 29.99;
    public static final double STANDARD_MONTHLY_FEE = 39.99;
    public static final double STANDARD_MEMBERSHIP_PAYMENT_FREQUENCY = 3.0;

    public static final double FAMILY_ONE_TIME_MEMBER_FEE = 29.99;
    public static final double FAMILY_MONTHLY_FEE = 59.99;
    public static final double FAMILY_MEMBERSHIP_PAYMENT_FREQUENCY = 3.0;
    public static final short FAMILY_GUEST_PASSES = 1;

    public static final double PREMIUM_MEMBER_ONE_TIME_FEE = 0.0;
    public static final double PREMIUM_MONTHLY_FEE = 59.99;
    public static final double PREMIUM_MEMBERSHIP_PAYMENT_FREQUENCY = 11;
    public static final short PREMIUM_GUEST_PASS = 3;

    public static final String[] LOCATIONS = {"Somerville", "Piscataway", "Bridgewater", "Edison", "Franklin"};

    public static final String CLASS_SCHEDULE_FROM_CONTENT_ROOT = "src/main/java/data/classSchedule.txt";
    public static final String MEMBER_LIST_FROM_CONTENT_ROOT = "src/main/java/data/memberList.txt";

    public static final int MONTHS_SET_TO_EXPIRE = 3;
    public static final int YEAR_SET_TO_EXPIRE = 1;
}
