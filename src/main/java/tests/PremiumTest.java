package tests;

import constants.Constants;
import date.Date;
import enums.Location;
import member.Family;
import member.Member;
import member.Premium;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a class using JUnit5 to test the getMembershipFee() method in the Premium class.
 * @author Hasnain Ali, Carolette Saguil
 */
class PremiumTest {

    /**
     * Tests that premium membership fee is 659.89
     */
    @Test
    @DisplayName("Testing premium membership fee")
    void testPremiumGetMembershipFee() {
        Date expire = new Date();
        expire.incrementMonth(Constants.YEAR_SET_TO_EXPIRE);
        Premium member = new Premium("Marilyn", "Monroe", new Date("6/1/1926"),
                expire, Location.SOMERVILLE, Constants.PREMIUM_GUEST_PASS);
        double actualOutput = member.getMembershipFee();
        double expectedOutput = 659.89;
        assertEquals(actualOutput, expectedOutput);
    }

    /**
     * Tests that family membership fee is 209.96.
     */
    @Test
    @DisplayName("Testing family membership fee")
    void testFamilyGetMembershipFee() {
        Date expire = new Date();
        expire.incrementMonth(Constants.MONTHS_SET_TO_EXPIRE);
        Family member = new Family("Marilyn", "Monroe", new Date("6/1/1926"),
                expire, Location.SOMERVILLE, Constants.FAMILY_GUEST_PASSES);
        double actualOutput = member.getMembershipFee();
        double expectedOutput = 209.96;
        assertEquals(actualOutput, expectedOutput);
    }

    /**
     * Tests that standard membership fee is 149.96.
     */
    @Test
    @DisplayName("Testing standard membership fee")
    void testStandardGetMembershipFee() {
        Date expire = new Date();
        expire.incrementMonth(Constants.MONTHS_SET_TO_EXPIRE);
        Member member = new Member("Marilyn", "Monroe", new Date("6/1/1926"),
                expire, Location.SOMERVILLE);
        double actualOutput = member.getMembershipFee();
        double expectedOutput = 149.96;
        assertEquals(actualOutput, expectedOutput);
    }
}