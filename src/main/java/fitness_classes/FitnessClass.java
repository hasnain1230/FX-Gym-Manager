package fitness_classes;

import enums.Location;
import enums.Time;
import member.Family;
import member.Member;
import date.Date;
import constants.Constants;
import java.util.ArrayList;

/**
 * This class defines all the characteristics of a fitness class. This includes:
 * <ul> <b>
 *     <li>ArrayList Members In Class</li>
 *     <li>ArrayList Guest Members In Class</li>
 *     <li>Time Time</li>
 *     <li>String Class Name</li>
 *     <li>String Instructor Name</li>
 *     <li>Location Gym Location</li>
 * </ul> </b>
 * @author Hasnain Ali, Carolette Saguil
 */
public class FitnessClass {
    /**
     * Array list of members in a fitness class.
     */
    private ArrayList<Member> membersInClass;
    /**
     * Array list of guest members in a fitness class.
     */
    private ArrayList<Member> guestMembersInClass;
    /**
     * Time of fitness class.
     */
    private final Time TIME;
    /**
     * Name of fitness class.
     */
    private final String CLASS_NAME;
    /**
     * Name of the instructor for fitness class.
     */
    private final String INSTRUCTOR_NAME;
    /**
     * Location of fitness class.
     */
    private final Location LOCATION;


    /**
     * Constructor that instantiates the state of a new fitness class.
     * The array list for members in class and guest members in class are new and empty.
     * @param time Time of fitness class.
     * @param className Name of fitness class.
     * @param instructorName Name of instructor for fitness class.
     * @param location Location of fitness class.
     */
    public FitnessClass(Time time, String className, String instructorName, Location location) {
        this.membersInClass = new ArrayList<>();
        this.guestMembersInClass = new ArrayList<>();
        this.TIME = time;
        this.CLASS_NAME = className;
        this.INSTRUCTOR_NAME = instructorName;
        this.LOCATION = location;
    }

    /**
     * @return Returns time of fitness class.
     */
    public String getTime() {
        return this.TIME.getTime();
    }

    /**
     * @return Returns name of fitness class.
     */
    public String getClassName() {
        return this.CLASS_NAME;
    }

    /**
     * @return Returns name of the instructor for fitness class.
     */
    public String getInstructorName() {
        return this.INSTRUCTOR_NAME;
    }

    /**
     * @return Returns location of fitness class.
     */
    public Location getLocation() {
        return this.LOCATION;
    }

    /**
     * Looks for a guest member in the guest members in class array list.
     * @param member Guest member to find array list index for.
     * @return index of guest member in the guest members in class array list, -1 otherwise.
     */
    public int findGuestMemberInClass(Member member) {
        for (int x = 0; x < this.guestMembersInClass.size(); x++) {
            if (this.guestMembersInClass.get(x).equals(member)) {
                return x;
            }
        }

        return Constants.NOT_FOUND;
    }


    /**
     * @return The size of the combined class. Only used for testing purposes.
     */
    public int getClassSize() {
        return this.guestMembersInClass.size() + this.membersInClass.size();
    }

    /**
     * Looks for member in the members in class array list.
     * @param member Member to find the array list index for.
     * @return index of the member in the members in class array list, -1 otherwise.
     */
    public int findMemberInClass(Member member) {
        for (int i = 0; i < this.membersInClass.size(); i++) {
            if (this.membersInClass.get(i).equals(member)) {
                return i;
            }
        }

        return Constants.NOT_FOUND;
    }

    /**
     * Checks if membership has expired. <br> **Please note, if membership expires today, the membership is considered expired**
     * @param member The member to check for an expired membership.
     * @return true if membership has expired, false otherwise.
     */
    public boolean checkIfMemberExpired(Member member) {
        return member.getExpire().compareTo(new Date()) <= 0;
    }

    /**
     * Drops guest member from fitness class.
     * Removes guest member from guest members in class array list.
     * @param member Guest member to drop from fitness class.
     */
    public void dropGuestMember(Member member) {
        this.guestMembersInClass.remove(member);
    }

    /**
     * Checks in guest member to fitness class.
     * Adds guest member to guest members in class array list.
     * @param member Guest member to add to fitness class.
     */
    public void checkInGuestMember(Member member) {
        this.guestMembersInClass.add(member);
    }

    /**
     * Checks in member to fitness class.
     * Adds member to members in class array list.
     * Fails if: member's membership is expired or if they are already in class.
     * @param member Member to check in.
     * @return true if member is added to members in class array list, false otherwise.
     */
    public boolean checkIn(Member member) { // Assuming that they allowed to do this if they are calling this method.
        if (!checkIfMemberExpired(member) && !this.membersInClass.contains(member)) {
            this.membersInClass.add(member);
            return true;
        }

        return false;
    }

    /**
     * Drops member from the members in class array list. Fails if member is not found with no return type.
     * @param member Member to drop from class
     */
    public void dropClass(Member member) {
       this.membersInClass.remove(member);
    }

    /**
     * @return Returns string representation of fitness class in the format Class name - Instructor name Time Location
     * - Participants -
     * members in class
     * - Guest -
     * guest members in class
     */
    @Override
    public String toString() {
        String returnString = String.format("%s - %s %s, %s\n", this.CLASS_NAME.toUpperCase(),
                this.INSTRUCTOR_NAME.toUpperCase(), this.TIME.getTime(), this.LOCATION.getTown());
        StringBuilder sb = new StringBuilder(returnString);

        if (!this.membersInClass.isEmpty()) {
            sb.append("- Participants -\n");

            for (Member inClass : this.membersInClass) {
                sb.append(String.format("\t %s\n", inClass));
            }
        }

        if (!this.guestMembersInClass.isEmpty()) {
            sb.append("- Guests -\n");

            for (Member inClass : this.guestMembersInClass) {
                sb.append(String.format("\t %s\n", inClass));
            }
        }

        return sb.toString();
    }


    /**
     * Checks if fitness class to compare is equal to current instance of fitness class using class name and instructor name.
     * @param fitnessClass Fitness class object to compare.
     * @return true if equal, false otherwise.
     */
    public boolean equalsNonVerbose(FitnessClass fitnessClass) {
        return this.CLASS_NAME.equalsIgnoreCase(fitnessClass.getClassName())
                && this.INSTRUCTOR_NAME.equalsIgnoreCase(fitnessClass.getInstructorName()) && this.LOCATION.equals(fitnessClass.getLocation());
    }

    /**
     * Checks if fitness class to compare is equal to current instance of fitness class using class name, instructor name, time and location.
     * @param obj Fitness class object to compare.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        FitnessClass fitnessClass = (FitnessClass) obj;
        return this.CLASS_NAME.equalsIgnoreCase(fitnessClass.getClassName())
                && this.INSTRUCTOR_NAME.equalsIgnoreCase(fitnessClass.getInstructorName())
                && this.TIME.getTime().equalsIgnoreCase(fitnessClass.getTime()) && this.LOCATION.equals(fitnessClass.getLocation());
    }
}


