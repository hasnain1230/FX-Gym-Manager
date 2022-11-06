package tests;

import date.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a class using JUnit5 to test the isValid() method in the Date Class.
 * @author Hasnain Ali, Carolette Saguil
 */
class DateTest {

    /**
     * Tests the requirement that the method shall not accept any date before 1900.
     */
    @Test
    @DisplayName("Testing Year Range")
    void test_year_range() {
        Date date1 = new Date("1/1/800");
        assertFalse(date1.isValid());

        Date date2 = new Date("1/1/1900");
        assertTrue(date2.isValid());
    }

    /**
     * Tests the requirement that the valid range of months is between 1 to 12.
     */
    @Test
    @DisplayName("Testing Month Range")
    void test_month_range() {
        Date date1 = new Date("13/1/2022");
        assertFalse(date1.isValid());

        Date date2 = new Date("0/1/2022");
        assertFalse(date2.isValid());

        Date date3 = new Date("5/1/2022");
        assertTrue(date3.isValid());
    }

    /**
     * Tests the requirement that the day is not less than 1.
     */
    @Test
    @DisplayName("Testing Day Min Range")
    void test_day_min_range() {
        Date date1 = new Date("9/0/2020");
        assertFalse(date1.isValid());
    }

    /**
     * Tests that the number of days in February, during non leap year, is 28.
     */
    @Test
    @DisplayName("Testing Days in Feb Non Leap Year")
    void test_days_in_Feb_nonLeap() {
        Date date1 = new Date("2/29/2021");
        assertFalse(date1.isValid());

        Date date2 = new Date("2/28/2021");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in February, during leap year, is 29.
     */
    @Test
    @DisplayName("Testing Days in Feb Leap Year")
    void test_days_in_Feb_Leap() {
        Date date1 = new Date("2/30/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("2/29/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in January is 31.
     */
    @Test
    @DisplayName("Testing Days in Jan")
    void test_days_in_Jan() {
        Date date1 = new Date("1/32/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("1/31/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in March is 31.
     */
    @Test
    @DisplayName("Testing Days in Mar")
    void test_days_in_Mar() {
        Date date1 = new Date("3/32/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("3/31/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in May is 31.
     */
    @Test
    @DisplayName("Testing Days in May")
    void test_days_in_May() {
        Date date1 = new Date("5/32/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("5/31/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in July is 31.
     */
    @Test
    @DisplayName("Testing Days in July")
    void test_days_in_July() {
        Date date1 = new Date("7/32/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("7/31/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in August is 31.
     */
    @Test
    @DisplayName("Testing Days in Aug")
    void test_days_in_Aug() {
        Date date1 = new Date("8/32/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("8/31/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in October is 31.
     */
    @Test
    @DisplayName("Testing Days in Oct")
    void test_days_in_Oct() {
        Date date1 = new Date("10/32/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("10/31/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in December is 31.
     */
    @Test
    @DisplayName("Testing Days in Dec")
    void test_days_in_Dec() {
        Date date1 = new Date("12/32/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("12/31/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in April is 30.
     */
    @Test
    @DisplayName("Testing Days in Apr")
    void test_days_in_Apr() {
        Date date1 = new Date("4/31/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("4/30/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in June is 30.
     */
    @Test
    @DisplayName("Testing Days in June")
    void test_days_in_June() {
        Date date1 = new Date("6/31/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("6/30/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in September is 30.
     */
    @Test
    @DisplayName("Testing Days in Sep")
    void test_days_in_Sep() {
        Date date1 = new Date("9/31/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("9/30/2020");
        assertTrue(date2.isValid());
    }

    /**
     * Tests that the number of days in November is 30.
     */
    @Test
    @DisplayName("Testing Days in Nov")
    void test_days_in_Nov() {
        Date date1 = new Date("11/31/2020");
        assertFalse(date1.isValid());

        Date date2 = new Date("11/30/2020");
        assertTrue(date2.isValid());
    }
}