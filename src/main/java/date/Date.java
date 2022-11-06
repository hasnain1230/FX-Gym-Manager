package date;

import java.util.Calendar;
import constants.Constants;

/**
 * This class defines a date using a year, month, and day. In this class, it can check whether the date is a valid date, compare dates to one
 * another, check if a date is in the future, and use dates to check a member's age.
 * @author Hasnain Ali, Carolette Saguil
 */
public class Date implements Comparable<Date> {

    /**
     * The year.
     */
    private int year;
    /**
     * The month.
     */
    private int month;
    /**
     * The day.
     */
    private int day;

    /**
     * Default constructor that instantiates today's date if there is no input.
     */
    public Date() {
        Calendar calendar = Calendar.getInstance();

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Constructor that instantiates a new date using the input date.
     * @param date Date in the format mm/dd/yyyy. Allows for single digit month and day inputs. Year must be four digits.
     */
    public Date(String date) {
        String[] dateParts = date.split("/");
        this.year = Integer.parseInt(dateParts[2]);
        this.day = Integer.parseInt(dateParts[1]);
        this.month = Integer.parseInt(dateParts[0]);
    }

    /**
     * @return Returns year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return Returns month.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * @return Returns day.
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Compares two dates together and is able to determine whether date to be compared is after, same, or
     * before this date.
     * @param date Date to be compared.
     * @return -1 if the date to be compared is after, 0 if the dates are the same, 1 if the date to be
     * compared is before. This is because if the current instance is after compared to {@code date}, then this - date
     * would return a negative number because this is after, so it's forward in time, thus yielding a negative value. The
     * same logic applies if this is before {@code date}.
     */
    @Override
    public int compareTo(Date date) {
        if (this.year == date.getYear() && this.month == date.getMonth() && this.day == date.getDay()) {
            return 0;
        }

        if (this.year > date.getYear()) {
            return 1;
        } else if (this.year == date.getYear()) {
            if (this.month > date.getMonth()) {
                return 1;
            } else if (this.month == date.getMonth()) {
                if (this.day > date.getDay()) {
                    return 1;
                }
            }
        }

        return -1;
    }

    /**
     * Checks if date is after today, or not. Meant specifically for date of birth given by client.
     * @return true if date is in the past, false if date is in the future or is today
     */
    public boolean checkIfDobIsFuture() {
        return this.compareTo(new Date()) < 0;
    }

    /**
     * Check if member is 18 years or older.
     * @return true if member is 18 years or older, false otherwise.
     */
    public boolean checkMemberAge() {
        Date today = new Date();
        int currentYear = today.getYear();
        int currentMonth = today.getMonth();
        int currentDay = today.getDay();

        int yearDifference = currentYear - this.year;

        if (yearDifference > Constants.MINIMUM_AGE) {
            return true;
        } else if (yearDifference == Constants.MINIMUM_AGE) {
            if ((currentMonth - this.month) > 0) {
                return true;
            } else if ((currentMonth - this.month) == 0) {
                if ((currentDay - this.day) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if year given is a leap year.
     * @param year Year to be checked.
     * @return true if year is a leap year, false otherwise.
     */
    private boolean isLeapYear(int year) {
        if (year % Constants.QUADRENNIAL == 0) {
            if (year % Constants.CENTENNIAL == 0) {
                if (year % Constants.QUATERCENTENNIAL == 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if date given by client is a valid calendar date.
     * Checks if year given and month given is in valid range.
     * Checks if day is in valid range depending on the month.
     * If month is February, checks if it is a leap year or not, then checks if the day is valid depending on the year.
     * @return true if the date is valid calendar date, false otherwise.
     */
    public boolean isValid() {
        if (this.year < Constants.MIN_YEAR) {
            return false;
        }

        if (this.month < Constants.MIN_MONTH || this.month > Constants.MAX_MONTH) {
            return false;
        }

        if (this.day < Constants.MIN_DAYS){
            return false;
        }

        if (isLeapYear(this.year) && this.month == Constants.FEBRUARY && this.day > Constants.MAX_LEAP_FEB_DAYS) {
            return false;
        } else if (!isLeapYear(this.year) && this.month == Constants.FEBRUARY && this.day > Constants.MAX_NORM_FEB_DAYS) {
            return false;
        }

        if (this.month == Constants.APRIL || this.month == Constants.JUNE || this.month == Constants.SEPTEMBER || this.month == Constants.NOVEMBER) {
            return this.day <= Constants.MAX_DAYS_ONE;
        } else return this.month == Constants.FEBRUARY || this.day <= Constants.MAX_DAYS_TWO;
    }

    /**
     * Increments the month by the amount inputted by the client.
     * @param increment Number of months we want to increment month by.
     */
    public void incrementMonth(int increment) {
        int incrementedMonth = this.month + increment;
        if (incrementedMonth > Constants.MAX_MONTH) {
            this.year += incrementedMonth / Constants.MAX_MONTH;
            this.month = incrementedMonth % Constants.MAX_MONTH;
        } else {
            this.month = incrementedMonth;
        }
    }

    /**
     * Increments the year by the amount inputted by the client.
     * @param increment Number of years we want to increment year by.
     */
    public void incrementYear(int increment) {
        this.year += increment;
    }

    /**
     * @return Returns string representation of the date in the format mm/dd/yy.
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

    /**
     * Testbed main used to check all {@code Date} methods.
     * Mainly used to check the isValid() method but checks compareTo(), checkIfDobIsFuture(), and CheckMemberAge() as well.
     * @param args No command line arguments are accepted here; should be empty.
     */
    public static void main(String[] args) {

        Date today = new Date();

        //Test cases for isValid()

        //Test Case #1
        Date notValidYear = new Date("1/1/800");
        System.out.println(notValidYear.isValid());

        //Test Case #2
        Date notValidMonth = new Date("13/1/2022");
        System.out.println(notValidMonth.isValid());

        //Test Case #3
        Date notValidMonth2 = new Date("0/1/2022");
        System.out.println(notValidMonth2.isValid());

        //Test Case #4
        Date nonLeapYear = new Date("2/29/2021");
        System.out.println(nonLeapYear.isValid());

        //Test Case #5
        Date nonLeapYear2 = new Date("2/28/2021");
        System.out.println(nonLeapYear2.isValid());

        //Test Case #6
        Date leapYear = new Date("2/29/2020");
        System.out.println(leapYear.isValid());

        //Test Case #7
        Date leapYear2 = new Date("2/30/2020");
        System.out.println(leapYear2.isValid());

        //Test Case #8
        Date janDay = new Date("1/32/2020");
        System.out.println(janDay.isValid());

        //Test Case #9
        Date marDay = new Date("3/31/2020");
        System.out.println(marDay.isValid());

        //Test Case #10
        Date mayDay = new Date("5/32/2020");
        System.out.println(mayDay.isValid());

        //Test Case #11
        Date julyDay = new Date("7/31/2020");
        System.out.println(julyDay.isValid());

        //Test Case #12
        Date augDay = new Date("8/32/2020");
        System.out.println(augDay.isValid());

        //Test Case #13
        Date octDay = new Date("10/31/2020");
        System.out.println(octDay.isValid());

        //Test Case #14
        Date decDay = new Date("12/32/2020");
        System.out.println(decDay.isValid());

        //Test Case #15
        Date aprDay = new Date("4/30/2020");
        System.out.println(aprDay.isValid());

        //Test Case #16
        Date juneDay = new Date("6/31/2020");
        System.out.println(juneDay.isValid());

        //Test Case #17
        Date sepDay = new Date("9/30/2020");
        System.out.println(sepDay.isValid());

        //Test Case #18
        Date novDay = new Date("11/31/2020");
        System.out.println(novDay.isValid());

        //Test Case #19
        Date notValidDay = new Date("9/0/2020");
        System.out.println(notValidDay.isValid());

        //Test cases for compareTo()

        //Test Case #1
        Date past = new Date("9/1/2022");
        System.out.println(past.compareTo(today));

        //Test Case #2
        Date today2 = new Date();
        System.out.println(today2.compareTo(today));

        //Test Case #3
        Date future = new Date("11/1/2022");
        System.out.println(future.compareTo(today));

        // Test Cases for checkIfDobIsFuture()

        //Test Case #1
        System.out.println(past.checkIfDobIsFuture());

        //Test Case #2
        System.out.println(future.checkIfDobIsFuture());

        //Test cases for checkMemberAge()

        //Test Case #1
        Date adult = new Date("9/26/1990");
        System.out.println(adult.checkMemberAge());

        //Test Case #2
        Date young = new Date("9/26/2022");
        System.out.println(young.checkMemberAge());
    }
}
