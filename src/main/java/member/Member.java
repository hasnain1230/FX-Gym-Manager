package member;

import constants.Constants;
import date.Date;
import enums.Location;

/**
 * This class defines all the characteristics of a Member. These include:
 * <ul> <b>
 *     <li>String First Name</li>
 *     <li>String Last Name</li>
 *     <li>Date Date of Birth</li>
 *     <li>Date Expiration Date</li>
 *     <li>Location Gym Location</li>
 * </ul> </b>
 * @author Hasnain Ali, Carolette Saguil
 */
public class Member implements Comparable<Member> {
    /**
     * First name
     */
    private final String fname;
    /**
     * Last Name
     */
    private final String lname;
    /**
     * Date of Birth
     */
    private final Date dob;
    /**
     * Expiration Date
     */
    private final Date expire;
    /**
     * Gym Location
     */
    private final Location location;

    /*
        A John Doe 9/2/2022 BRIDGEWATER
        A John Doe 12/2/2022 BRIDGEWATER
        A John Doe 12/20/2004 BRIDGEWATER
        A John Doe 2/29/2003 BRIDGEWATER
        A John Doe 4/31/2003 BRIDGEWATER
        A John Doe 13/31/2003 BRIDGEWATER
        A John Doe 3/32/2003 BRIDGEWATER
        A John Doe -1/31/2003 BRIDGEWATER
    */

    /**
     * Constructor that instantiates the state of a Member.
     * @param fname Member's first name.
     * @param lname Member's last name.
     * @param dob Member's date of birth.
     * @param expire Member's expiration date.
     * @param location Member's location.
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * @return Returns member's first name.
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * @return Returns member's last name.
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * @return Returns member's date of birth.
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * @return Returns member's expiration date.
     */
    public Date getExpire() {
        return this.expire;
    }

    /**
     * @return Returns member's location.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * @return Returns standard membership fee.
     */
    public double getMembershipFee() {
        return Constants.STANDARD_ONE_TIME_MEMBER_FEE + (Constants.STANDARD_MONTHLY_FEE * Constants.STANDARD_MEMBERSHIP_PAYMENT_FREQUENCY);
    }

    /**
     * @param fee Standard Membership Fee.
     * @return Returns string representation of a standard member including the fee in the format firstName lastName, DOB: mm/dd/yy,
     * Membership expires: mm/dd/yy, Location: location, Membership fee: $fee.
     */
    public String toString(double fee){
        if (this.expire.compareTo(new Date()) <= 0) {
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s, Membership fee: $%.2f",
                    this.fname, this.lname, this.dob, this.expire, this.location, fee);
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s, Membership fee: $%.2f",
                this.fname, this.lname, this.dob, this.expire, this.location, fee);
    }

    /**
     * @return Return string representation of member in the format firstName lastName, DOB: mm/dd/yy,
     * Membership expires: mm/dd/yy, Location: location.
     */
    @Override
    public String toString() {
        if (this.expire.compareTo(new Date()) <= 0) {
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s",
                    this.fname, this.lname, this.dob, this.expire, this.location);
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s",
                this.fname, this.lname, this.dob, this.expire, this.location);
    }


    /**
     * @param obj Member object to compare.
     * @return Whether the {@code obj} is equal to the current instance of member when comparing first names, last names,
     * and dates of births.
     */
    @Override
    public boolean equals(Object obj) {
        Member member = (Member) obj;

        return this.fname.equalsIgnoreCase(member.getFname()) && this.lname.equalsIgnoreCase(member.getLname())
                && this.dob.compareTo(member.getDob()) == 0;
    }


    /**
     * Compares two members together by last name then first name.
     * @param member Member to be compared.
     * @return 0 if the members' names are the same, a value less than 0 if the current instance member's name is
     * lexicographically less than the member argument's name, and a value greater than 0 if the current instance member's name
     * is lexicographically greater than the member argument's name.
     */
    @Override
    public int compareTo(Member member) {
        String name = String.format("%s %s", this.getLname(), this.getFname());
        String memberName = String.format("%s %s", member.getLname(), member.getFname());

        return name.compareTo(memberName);
    }

    /**
     * This testbed main in Member.java is to test all methods related Member, especially {@code Member.compareTo()}.
     * @param args Empty arguments; no arguments from the command line should be passed here.
     */
    public static void main(String[] args) {
        Member memberToTest1 = new Member("Rick", "Astely", new Date("04/20/1969"),
                new Date("10/07/2022"), Location.PISCATAWAY);

        // ************************ Testing Getters ************************
        System.out.println(memberToTest1.getFname());
        System.out.println(memberToTest1.getLname());
        System.out.println(memberToTest1.getDob());
        System.out.println(memberToTest1.getExpire());
        System.out.println(memberToTest1.getLocation());

        System.out.println();

        // ************************ Testing Member.toString() ************************
        System.out.println(memberToTest1); // Calls toString by default

        System.out.println();
        // ************************ Testing Member.equals() ************************
        Member memberToTest2 = new Member("Rick", "Astley", new Date("04/20/1969"),
                new Date("10/07/2022"), Location.PISCATAWAY);
        Member memberToTest3 = new Member("Rick", "Astley", new Date("06/09/1969"),
                new Date("10/07/2022"), Location.SOMERVILLE);

        System.out.println(memberToTest1.equals(memberToTest2)); // Should print true
        System.out.println(memberToTest2.equals(memberToTest3)); // Should print false because different DOB

        System.out.println();

        // ************************ Testing Member.compareTo() ************************

        Member memberToTest4 = new Member("Barack", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);
        Member memberToTest5 = new Member("Kayne", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);
        Member memberToTest6 = new Member("Barac", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);
        Member memberToTest7 = new Member("Barack", "Obame", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);
        Member memberToTest8 = new Member("Barax", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);
        Member memberToTest9 = new Member("Barack", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);
        Member memberToTest10 = new Member("Baracx", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);

        // Test Case 1
        System.out.println(memberToTest1.compareTo(memberToTest2)); // Names are the same, hence 0
        System.out.println();

        // Test Case 2
        System.out.println(memberToTest4.compareTo(memberToTest1)); // Prints positive number since Obama comes after Astley
        // Test Case 3
        System.out.println(memberToTest1.compareTo(memberToTest4)); // Prints negative number since Astley comes before Obama
        System.out.println();

        // Test Case 4
        System.out.println(memberToTest5.compareTo(memberToTest4)); // Same last name, different first name
        // Test Case 5
        System.out.println(memberToTest4.compareTo(memberToTest5));
        System.out.println();

        // Test Case 6
        System.out.println(memberToTest4.compareTo(memberToTest6)); // Similar first names, same last names
        // Test Case 7
        System.out.println(memberToTest6.compareTo(memberToTest4));
        // Test Case 8
        System.out.println(memberToTest8.compareTo(memberToTest4)); // "ax" comes after "ck"
        // Test Case 9
        System.out.println(memberToTest4.compareTo(memberToTest8)); // "ck" comes before "ax"
        System.out.println();

        // Test Case 10
        System.out.println(memberToTest7.compareTo(memberToTest4)); // Similar last names, same first name
        // Test Case 11
        System.out.println(memberToTest4.compareTo(memberToTest7));
        // Test Case 12
        System.out.println(memberToTest10.compareTo(memberToTest9));
        // Test Case 13
        System.out.println(memberToTest9.compareTo(memberToTest10));
    }
}
