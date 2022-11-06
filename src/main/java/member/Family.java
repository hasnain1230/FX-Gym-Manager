package member;

import constants.Constants;
import date.Date;
import enums.Location;

/**
 * This class inherits all the characteristics of a member and defines new characteristics for a family. These include:
 * <ul> <b>
 *     <li>String First Name</li>
 *     <li>String Last Name</li>
 *     <li>Date Date of Birth</li>
 *     <li>Date Expiration Date</li>
 *     <li>Location Gym Location</li>
 *     <li>Short Guest Passes</li>
 * </ul> </b>
 * @author Hasnain Ali, Carolette Saguil
 */
public class Family extends Member {
    /**
     * Guest passes.
     */
    private short guestPasses;

    /**
     * Constructor that instantiates the state of a Member.
     *
     * @param fname    Member's first name.
     * @param lname    Member's last name.
     * @param dob      Member's date of birth.
     * @param expire   Member's expiration date.
     * @param location Member's location.
     * @param guestPasses Member's guest passes.
     */
    public Family(String fname, String lname, Date dob, Date expire, Location location, short guestPasses) {
        super(fname, lname, dob, expire, location);
        this.guestPasses = guestPasses;
    }

    /**
     * @param guestPasses Number we want to set member's guest passes to.
     * @return false if the number of guest passes is less than 1, true otherwise.
     */
    public boolean setGuestPasses(short guestPasses) {
        if (guestPasses < 0) { // This prevents us from accidentally setting the
            return false;
        }

        this.guestPasses = guestPasses;
        return true;
    }

    /**
     * @return Returns member's guest passes.
     */
    public short getGuestPasses() {
        return this.guestPasses;
    }

    /**
     * @return Returns family membership fee.
     */
    @Override
    public double getMembershipFee() {
        return Constants.FAMILY_ONE_TIME_MEMBER_FEE + (Constants.FAMILY_MONTHLY_FEE * Constants.FAMILY_MEMBERSHIP_PAYMENT_FREQUENCY);
    }

    /**
     * @param fee Family Membership Fee.
     * @return Returns string representation of a family member including the fee in the format firstName lastName, DOB: mm/dd/yy,
     * Membership expires: mm/dd/yy, Location: location, (Family) guest-pass remaining = guestPasses, Membership fee: $fee.
     */
    @Override
    public String toString(double fee){
        if (super.getExpire().compareTo(new Date()) <= 0) {
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s, (Family) guest-pass remaining = %d, Membership fee: $%.2f",
                    super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), this.guestPasses, fee);
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s, (Family) guest-pass remaining = %d, Membership fee: $%.2f",
                super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), this.guestPasses, fee);
    }

    /**
     * @return Returns string representation of a family member including the fee in the format firstName lastName, DOB: mm/dd/yy,
     * Membership expires: mm/dd/yy, Location: location, (Family) guest-pass remaining = guestPasses.
     */
    @Override
    public String toString() {
        if (super.getExpire().compareTo(new Date()) <= 0) {
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s, (Family) guest-pass remaining = %d",
                    super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), this.guestPasses);
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s, (Family) guest-pass remaining = %d",
                super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), this.guestPasses);
    }
}
