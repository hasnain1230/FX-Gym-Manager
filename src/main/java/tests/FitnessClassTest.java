package tests;

import constants.Constants;
import date.Date;
import enums.Location;
import enums.Time;
import fitness_classes.FitnessClass;
import member.Member;
import member.Premium;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a class using JUnit5 to tests the adding and dropping of guest and regular members.
 * @author Hasnain Ali, Carolette Saguil
 */
public class FitnessClassTest {

    /**
     * Checks whether Drop guest works correctly.
     */
    @Test
    @DisplayName("Testing Drop Guest Member")
    void dropGuestMember() {
        Date expire = new Date();
        expire.incrementMonth(Constants.YEAR_SET_TO_EXPIRE);
        Premium premium = new Premium("Harry", "Potter", new Date("10/17/2000"), expire, Location.SOMERVILLE, Constants.PREMIUM_GUEST_PASS);

        FitnessClass fitnessClass = new FitnessClass(Time.MORNING, "Cardio", "Hermonie", Location.SOMERVILLE);

        fitnessClass.checkIn(premium);

        fitnessClass.checkInGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() - 1));
        fitnessClass.checkInGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() - 1));
        fitnessClass.checkInGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() - 1));

        fitnessClass.dropGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() + 1));
        assertEquals(premium.getGuestPasses(), 1);

        fitnessClass.dropGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() + 1));
        assertEquals(premium.getGuestPasses(), 2);

        fitnessClass.dropGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() + 1));
        assertEquals(premium.getGuestPasses(), 3);
    }

    /**
     * Testing check in guest member.
     */
    @Test
    @DisplayName("Testing Check In Guest Member")
    void checkInGuestMember() {
        Date expire = new Date();
        expire.incrementMonth(Constants.YEAR_SET_TO_EXPIRE);
        Premium premium = new Premium("Harry", "Potter", new Date("10/17/2000"), expire, Location.SOMERVILLE, Constants.PREMIUM_GUEST_PASS);

        FitnessClass fitnessClass = new FitnessClass(Time.MORNING, "Cardio", "Hermonie", Location.SOMERVILLE);

        fitnessClass.checkIn(premium);

        fitnessClass.checkInGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() - 1));
        assertEquals(premium.getGuestPasses(), 2);
        fitnessClass.checkInGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() - 1));
        assertEquals(premium.getGuestPasses(), 1);
        fitnessClass.checkInGuestMember(premium);
        premium.setGuestPasses((short) (premium.getGuestPasses() - 1));
        assertEquals(premium.getGuestPasses(), 0);
    }

    /**
     * Testing Check In Member
     */
    @Test
    @DisplayName("Testing Check In Guest Member")
    void checkIn() {
        Member member1 = new Member("Harry", "Potter", new Date("01/01/2000"), new Date("01/01/2030"), Location.PISCATAWAY);
        FitnessClass fitnessClass = new FitnessClass(Time.MORNING, "Cardio", "Hermonie", Location.SOMERVILLE);

        Member member2 = new Member("Harry", "Pooper", new Date("01/01/2000"), new Date("01/01/2030"), Location.PISCATAWAY);

        fitnessClass.checkIn(member1);
        assertEquals(fitnessClass.getClassSize(), 1);
        fitnessClass.checkIn(member2);
        assertEquals(fitnessClass.getClassSize(), 2);
    }

    /**
     * Testing Drop Member
     */
    @Test
    @DisplayName("Testing Drop Guest Member")
    void dropClass() {
        Member member1 = new Member("Harry", "Potter", new Date("01/01/2000"), new Date("01/01/2030"), Location.PISCATAWAY);
        FitnessClass fitnessClass = new FitnessClass(Time.MORNING, "Cardio", "Hermonie", Location.SOMERVILLE);

        Member member2 = new Member("Harry", "Pooper", new Date("01/01/2000"), new Date("01/01/2030"), Location.PISCATAWAY);

        fitnessClass.checkIn(member1);
        fitnessClass.checkIn(member2);

        fitnessClass.dropClass(member1);
        assertEquals(fitnessClass.getClassSize(), 1);
        fitnessClass.dropClass(member2);
        assertEquals(fitnessClass.getClassSize(), 0);
    }
}