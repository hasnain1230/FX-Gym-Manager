package member;

import constants.Constants;
import date.Date;
import enums.Location;

/**
 * This class inherits all the characteristics of a family. These include:
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
public class Premium extends Family {
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
    public Premium(String fname, String lname, Date dob, Date expire, Location location, short guestPasses) {
        super(fname, lname, dob, expire, location, guestPasses);
    }

    /**
     * @return Returns premium membership fee.
     */
    @Override
    public double getMembershipFee() {
        return Constants.PREMIUM_MEMBER_ONE_TIME_FEE + (Constants.PREMIUM_MONTHLY_FEE * Constants.PREMIUM_MEMBERSHIP_PAYMENT_FREQUENCY);
    }

    /**
     * @param fee Premium Membership Fee.
     * @return Returns string representation of a premium member including the fee in the format firstName lastName, DOB: mm/dd/yy,
     * Membership expires: mm/dd/yy, Location: location, (Family) guest-pass remaining = guestPasses, Membership fee: $fee.
     */
    @Override
    public String toString(double fee){
        if (super.getExpire().compareTo(new Date()) <= 0) {
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s, (Premium) guest-pass remaining = %d, Membership fee: $%.2f",
                    super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), super.getGuestPasses(), fee);
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s, (Premium) guest-pass remaining = %d, Membership fee: $%.2f",
                super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), super.getGuestPasses(), fee);
    }

    /**
     * @return Returns string representation of a premium member including the fee in the format firstName lastName, DOB: mm/dd/yy,
     * Membership expires: mm/dd/yy, Location: location, (Family) guest-pass remaining = guestPasses.
     */
    @Override
    public String toString() {
        if (super.getExpire().compareTo(new Date()) <= 0) {
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s, (Premium) guest-pass remaining = %d",
                    super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), super.getGuestPasses());
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s, (Premium) guest-pass remaining = %d",
                super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), super.getGuestPasses());
    }
}
