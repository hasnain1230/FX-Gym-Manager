package member;

import constants.Constants;
import date.Date;
import enums.Location;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class defines the Member Database using a single one dimensional array.
 * That array will store all the added members to the database.
 * @author Hasnain Ali, Carolette Saguil
 */
public class MemberDatabase {
    /**
     * Members array / database
     */
    private Member[] mlist;
    /**
     * Current size of member database.
     */
    private int size;

    /**
     * Instantiates an empty Member Database where it's size is 0. Instantiates a member array of default array size of 4
     * that will store members added to the database and later grow as needed.
     */
    public MemberDatabase() {
        this.size = 0;
        mlist = new Member[Constants.ARRAY_DEFAULT_SIZE];
    }

    /**
     * Looks for a Member in the member database.
     * @param member Member to find the database index for.
     * @return index of the member in the member database, -1 otherwise.
     */
    public int find(Member member) {
        for (int x = 0; x < this.size; x++) { // Not binary search, it's fine for this project.
            if (this.mlist[x].equals(member)) {
                return x;
            }
        }

        return Constants.NOT_FOUND;
    }

    /**
     * @param index Index of member in the member database.
     * @return Returns member at that index in the member database, null if index is invalid / is not in the database.
     */
    public Member getMember(int index) {
        if (index < 0 || index > this.size) {
            return null;
        }
        return this.mlist[index];
    }

    /**
     * Grows the member database when the current database is full.
     * Size limited by JVM memory allocation. (How big Java will let you make an array before you run out of space).
     */
    public void grow() {
        Member[] newMemberList = new Member[this.size + Constants.ARRAY_INCREMENT_SIZE];

        for (int x = 0; x < this.size; x++) {
            newMemberList[x] = this.mlist[x];
        }

        this.mlist = newMemberList;
    }

    /**
     * Adds member given to the member database.
     * Returns false if member is already in the member database.
     * @param member Member to be added.
     * @return true if member was added, false otherwise.
     */
    public boolean add(Member member) {
        if (this.find(member) >= 0) {
            return false;
        }
        if (this.size == this.mlist.length) {
            this.grow();
        }

        this.mlist[size] = member;
        this.size++;

        return true;
    }

    /**
     * Removes member given from the member database.
     * Returns false if member is not in the member database.
     * @param member Member to be removed.
     * @return true if member was removed, false otherwise.
     */
    public boolean remove(Member member) {
        int index = this.find(member);

        if (index == Constants.NOT_FOUND) {
            return false;
        }

        for (int x = index; x < this.size - 1; x++) {
            this.mlist[x] = this.mlist[x + 1];
        }

        this.mlist[this.size - 1] = null;
        this.size--;

        return true;
    }

    /**
     * @return Returns string of all the members in the database with no sorting.
     */
    public String print() {

        if (this.size == 0) {
            return ("Member Database is empty!\n");
        }

        String memberDatabase = "-list of members-\n";
        for (int x = 0; x < this.size; x++) {
            memberDatabase = memberDatabase + this.mlist[x] + "\n";
        }
        memberDatabase = memberDatabase + "-end of list-\n";
        return memberDatabase;
    }


    /**
     * @return Returns string of all the members in the database with their membership fee for the next billing term, with no sorting.
     */
    public String printWithMembershipFee() {

        if (this.size == 0) {
            return ("Member Database is empty!\n");
        }

        String memberDatabase = "-list of members with membership fees-\n";
        for (int x = 0; x < this.size; x++) {
            memberDatabase = memberDatabase + (this.mlist[x].toString(mlist[x].getMembershipFee())) + "\n";
        }
        memberDatabase = memberDatabase + "-end of list-\n";
        return memberDatabase;
    }

    /**
     * @return Returns string of all the members in the member database, sorted by county then zipcode.
     */
    public String printByCounty() {
        if (this.size == 0) {
            return ("Member Database is empty!\n");
        }

        Member tempMember;

        String memberDatabase = "-list of members sorted by county and zipcode-\n";
        for (int x = 0; x < this.size; x++) {
            for (int y = x + 1; y < this.size; y++) {
                if (this.mlist[x].getLocation().getCounty().compareTo(this.mlist[y].getLocation().getCounty()) > 0) {
                    tempMember = this.mlist[x];
                    this.mlist[x] = this.mlist[y];
                    this.mlist[y] = tempMember;
                } else if (this.mlist[x].getLocation().getCounty().compareTo(this.mlist[y].getLocation().getCounty()) == 0) {
                    if (this.mlist[x].getLocation().getPostalCode().compareTo(this.mlist[y].getLocation().getPostalCode()) > 0) {
                        tempMember = this.mlist[x];
                        this.mlist[x] = this.mlist[y];
                        this.mlist[y] = tempMember;
                    }
                }
            }

            memberDatabase = memberDatabase + this.mlist[x] + "\n";
        }
        memberDatabase = memberDatabase + "-end of list-\n";
        return memberDatabase;
    }


    /**
     * @return Returns string of all the members in the member database, sorted by expiration date.
     */
    public String printByExpirationDate() {
        if (this.size == 0) {
            return ("Member Database is empty!\n");
        }

        Member tempMember;

        String memberDatabase = "-list of members sorted by member expiration date-\n";
        for (int x = 0; x < this.size; x++) {
            for (int y = x + 1; y < this.size; y++) { // x = 09/29/2022 y
                if (this.mlist[x].getExpire().compareTo(this.mlist[y].getExpire()) > 0) {
                    tempMember = this.mlist[x];
                    this.mlist[x] = this.mlist[y];
                    this.mlist[y] = tempMember;
                }
            }

            memberDatabase = memberDatabase + this.mlist[x] + "\n";
        }
        memberDatabase = memberDatabase + "-end of list-\n";
        return memberDatabase;
    }

    /**
     * @return Returns string of all the members in the member database, sorted by last name then first name.
     */
    public String printByName() {
        if (this.size == 0) {
            return ("Member Database is empty!\n");
        }

        Member tempMember;

        String memberDatabase = "-list of members sorted by last name, and first name-\n";
        for (int x = 0; x < this.size; x++) {
            for (int y = x + 1; y < this.size; y++) {
                if (this.mlist[x].getLname().compareTo(this.mlist[y].getLname()) > 0) {
                    tempMember = this.mlist[x];
                    this.mlist[x] = this.mlist[y];
                    this.mlist[y] = tempMember;
                } else if (this.mlist[x].getLname().compareTo(this.mlist[y].getLname()) == 0) {
                    if (this.mlist[x].getFname().compareTo(this.mlist[y].getFname()) > 0) {
                        tempMember = this.mlist[x];
                        this.mlist[x] = this.mlist[y];
                        this.mlist[y] = tempMember;
                    }
                }
            }

            memberDatabase = memberDatabase + this.mlist[x] + "\n";
        }
        memberDatabase = memberDatabase + "-end of list-\n";
        return memberDatabase;
    }

    public String loadHistoricalMembers() {
        String loadedMembers = null;
        try {
            File file = new File(Constants.MEMBER_LIST_FROM_CONTENT_ROOT);
            Scanner sc = new Scanner(file);
            loadedMembers = ("-List of Members Loaded-\n");

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split("\\s+");
                Member member = new Member(line[0], line[1], new Date(line[2]), new Date(line[3]), Location.returnEnumFromString(line[4]));
                add(member);
                loadedMembers = loadedMembers + (member.toString()) + "\n";
            }
        } catch (FileNotFoundException fileNotFoundException) {
            return "File is not found";
        }

        loadedMembers = loadedMembers + ("-end of list-\n");
        return loadedMembers;
    }
}
