package enums;

/**
 * Location Enum stores information about all valid gym locations. The valid gym locations are: Bridgewater, Edison, Franklin,
 * Piscataway, and Somerville. Enum also stores their appropriate postal codes, and county locations, all of which are accessible.
 * @author Hasnain Ali, Carolette Saguil
 */
public enum Location {
    /**
     * "Bridgewater", "08837", "Somerset County"
     */
    BRIDGEWATER("Bridgewater", "08837", "Somerset County"),
    /**
     * "Edison", "08837", "Middlesex County"
     */
    EDISON("Edison", "08837", "Middlesex County"),
    /**
     * "Franklin", "08873", "Somerset County"
     */
    FRANKLIN("Franklin", "08873", "Somerset County"),
    /**
     * "Piscataway", "08854", "Middlesex County"
     */
    PISCATAWAY("Piscataway", "08854", "Middlesex County"),
    /**
     * "Somerville", "08876", "Somerset County"
     */
    SOMERVILLE("Somerville", "08876", "Somerset County");

    /**
     * The town.
     */
    private final String TOWN;
    /**
     * The postal code.
     */
    private final String POSTALCODE;
    /**
     * The county.
     */
    private final String COUNTY;

    /**
     * Stores information about each gym location in the current instance of the Location enum.
     * @param town Town name as a String
     * @param postalCode Postal Code as a String
     * @param county County Name as a String
     */
    private Location(String town, String postalCode, String county) {
        this.TOWN = town;
        this.POSTALCODE = postalCode;
        this.COUNTY = county;
    }

    /**
     * @param location Gym location as a string
     * @return Returns the corresponding Location enum of the {@code location}.
     */
    public static Location returnEnumFromString(String location) {
        if (location.equalsIgnoreCase("bridgewater")) {
            return Location.BRIDGEWATER;
        } else if (location.equalsIgnoreCase("edison")) {
            return Location.EDISON;
        } else if (location.equalsIgnoreCase("franklin")) {
            return Location.FRANKLIN;
        } else if (location.equalsIgnoreCase("piscataway")) {
            return Location.PISCATAWAY;
        } else if (location.equalsIgnoreCase("somerville")) {
            return Location.SOMERVILLE;
        } else {
            return null;
        }
    }

    /**
     * @return Returns county name of the current Location.
     */
    public final String getCounty() {
        return this.COUNTY;
    }

    /**
     * @return Returns The name of the current Location's town
     */
    public final String getTown() {
        return this.TOWN;
    }

    /**
     * @return Returns the postal code of the current Location.
     */
    public final String getPostalCode() {
        return this.POSTALCODE;
    }

    /**
     * @return Returns the town name, postal code, and county, as one String.
     */
    @Override
    public final String toString() {
        return String.format("%s, %s, %s", this.TOWN, this.POSTALCODE, this.COUNTY);
    }
}
