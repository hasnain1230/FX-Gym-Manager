package gymmanager;

import constants.Constants;
import date.Date;
import enums.Location;
import enums.Time;
import fitness_classes.ClassSchedule;
import fitness_classes.FitnessClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import member.Family;
import member.Member;
import member.MemberDatabase;
import member.Premium;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * This is the controller class for the Gym Manager application. It will handle all ActionEvents, button clicks, and any other
 * behavior that the user can interact with. This class will also handle the logic of the application. This class implements initializable
 * in order to initialize the basic state of the application upon start.
 * @author Hasnain Ali, Carolette Saguil
 */
public class GymManagerController implements Initializable {
    /**
     * The first name text field for the member tab.
     */
    @FXML
    private TextField firstNameTextField;
    /**
     * The last name text field for the member tab.
     */
    @FXML
    private TextField lastNameTextField;
    /**
     * The date of birthdate picker for the member tab.
     */
    @FXML
    private DatePicker dobDatePicker;
    /**
     * The choice box for the member type for the member tab. This choice box will be initialized upon start when the initialize method is called.
     */
    @FXML
    private ChoiceBox<String> locationChoiceBox;

    /**
     * The choice box for the member type for the fitness class tab. This choice box will be initialized upon start when the initialize method is called.
     */
    @FXML
    private ChoiceBox<String> locationFitnessClassChoiceBox;
    /**
     * This is one of the radio buttons for the standard membership when adding a member.
     */
    @FXML
    private RadioButton standardMembershipRadioButton;
    /**
     * This is one of the radio buttons for the family membership when adding a member.
     */
    @FXML
    private RadioButton familyMembershipRadioButton;
    /**
     * This is one of the radio buttons for the premium membership when adding a member.
     */
    @FXML
    private RadioButton premiumMembershipRadioButton;
    /**
     * This is the text area where all the program output will be displayed.
     */
    @FXML
    private TextArea outputTextArea;
    /**
     * This is the choice box for selecting a class when adding a member to a class. It will not be selectable until the user loads a class schedule.
     */
    @FXML
    private ChoiceBox<String> classChoiceBox;
    /**
     * This is the choice box for the instructor names when adding a member into a fitness class. No instructors will be displayed until the user loads in the fitness class file.
     */
    @FXML
    private ChoiceBox<String> instructorChoiceBox;
    /**
     * The first name text field for the fitness class tab.
     */
    @FXML
    private TextField firstNameFitnessClassTextField;
    /**
     * The last name text field for the fitness class tab.
     */
    @FXML
    private TextField lastNameFitnessClassTextField;
    /**
     * The DatePicker for the date of birth for the fitness class DOB
     */
    @FXML
    private DatePicker fitnessClassDobDatePicker;
    /**
     * This is the MemberDatabase that will be initialized once the controller has been loaded.
     */
    private static MemberDatabase memberDatabase;
    /**
     * This is the class schedule that will be initialized once the controller has been loaded.
     */
    private static ClassSchedule classSchedule;

    /**
     * @param arg0 The location used to resolve relative paths for the root object, or
     *             {@code null} if the location is not known.
     * @param arg1 The resources used to localize the root object, or {@code null} if
     *             the root object was not localized.
     * This is method will initialize the class schedule and the member database state for the Gym Manager GUI. It will be called automatically upon the start of the program.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.locationChoiceBox.getItems().addAll(Constants.LOCATIONS);
        this.locationFitnessClassChoiceBox.getItems().addAll(Constants.LOCATIONS);
        memberDatabase = new MemberDatabase();
        classSchedule = new ClassSchedule();
    }

    /**
     * Checks if the inputs for add are filled in for the member tab.
     * @return true if all the inputs are filled, false otherwise. We check if the inputs are valid in the respective functions where it is called.
     */
    private boolean checkAddInputs() {
        if (this.firstNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("First Name Field Empty!\n");
            this.clearMembershipArea();
            return false;
        } else if (this.lastNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("Last Name Field Empty\n");
            this.clearMembershipArea();
            return false;
        } else if (this.dobDatePicker.getValue() == null) {
            this.outputTextArea.appendText("Date Not Selected and / or Date is invalid\n");
            this.clearMembershipArea();
            return false;
        } else if (this.locationChoiceBox.getValue() == null) {
            this.outputTextArea.appendText("Location Is Empty\n");
            this.clearMembershipArea();
            return false;
        } else if (!(this.standardMembershipRadioButton.isSelected() || this.familyMembershipRadioButton.isSelected() || this.premiumMembershipRadioButton.isSelected())) {
            this.outputTextArea.appendText("Membership Not Selected\n");
            this.clearMembershipArea();
            return false;
        }

        return true;
    }

    /**
     * This function maps to the "Clear Fields" button, but it serves as a helper function for the {@code addMember} and {@code removeMember} functions.
     * This function will clear all fields in the membership tab.
     */
    @FXML
    protected void clearMembershipArea() {
        this.firstNameTextField.clear();
        this.lastNameTextField.clear();
        this.dobDatePicker.getEditor().clear();
        this.dobDatePicker.setValue(null);
        this.standardMembershipRadioButton.setSelected(false);
        this.familyMembershipRadioButton.setSelected(false);
        this.premiumMembershipRadioButton.setSelected(false);
        this.locationChoiceBox.setValue(null);
    }

    /**
     * Adds member to the member database.
     * Fails if: date of birth is invalid, date of birth is in the future, member is younger than 18 years old,
     * location is invalid, or if member is already in the database. The corresponding error message will be displayed
     * in the output text field. Otherwise, upon success, the member will be added to the database successfully and a corresponding message
     * will be displayed in the output text area. Additionally, any invalid dates entered will be rejected.
     */
    @FXML
    protected void addMember() {
        if (checkAddInputs()) {
            Member member = null;
            String firstName = this.firstNameTextField.getText().trim();
            String lastName = this.lastNameTextField.getText().trim();
            Date dob = new Date(this.dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            Location location = Location.returnEnumFromString(this.locationChoiceBox.getValue());

            if (location == null) {
                this.outputTextArea.appendText("Invalid Location\n");
                return;
            } else if (!dob.isValid()) {
                this.outputTextArea.appendText(String.format("DOB %s: invalid calendar date!\n", dob));
                return;
            } else if (!dob.checkMemberAge()) {
                this.outputTextArea.appendText(String.format("DOB %s: must be 18 or older to join!\n", dob));
                return;
            }

            Date expire = new Date();
            if (this.standardMembershipRadioButton.isSelected()) {
                expire.incrementMonth(Constants.MONTHS_SET_TO_EXPIRE);
                member = new Member(firstName, lastName, dob, expire, location);
            } else if (this.familyMembershipRadioButton.isSelected()) {
                expire.incrementMonth(Constants.MONTHS_SET_TO_EXPIRE);
                member = new Family(firstName, lastName, dob, expire, location, Constants.FAMILY_GUEST_PASSES);
            } else if (this.premiumMembershipRadioButton.isSelected()) {
                expire.incrementYear(Constants.YEAR_SET_TO_EXPIRE);
                member = new Premium(firstName, lastName, dob, expire, location, Constants.PREMIUM_GUEST_PASS);
            }

            if (memberDatabase.add(member)) {
                String outputString = String.format("%s %s added.\n", member.getFname(), member.getLname());
                this.outputTextArea.appendText(outputString);
            } else {
                String outputString = String.format("%s %s is already in the database.\n", member.getFname(), member.getLname());
                this.outputTextArea.appendText(outputString);
            }

            this.clearMembershipArea();
        }
    }

    /**
     * Removes a member from the database.
     * Fails if: firstName, lastName, and / or DOB is empty and if member is not in database. A corresponding error message will be displayed.
     * Additionally, if the dated entered into DOB picker is invalid, then a corresponding error message will be displayed. If a member is not found
     * in the memberDataBase, then a corresponding error message will be printed in the output. If a member is found in the member database, then
     * they will be removed and a corresponding message will be printed to the user.
     */
    @FXML
    protected void removeMember() {
        if (this.firstNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("First Name Field Empty!\n");
            return;
        } else if (this.lastNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("Last Name Field Empty\n");
            return;
        } else if (this.dobDatePicker.getValue() == null) {
            this.outputTextArea.appendText("Date Not Selected and / or the Date is Invalid\n");
            return;
        }

        String firstName = this.firstNameTextField.getText().trim();
        String lastName = this.lastNameTextField.getText().trim();
        Date dob = new Date(this.dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        Member member = new Member(firstName, lastName, dob, null, null);
        Member memberToRemove = memberDatabase.getMember(memberDatabase.find(member));
        this.clearMembershipArea();
        if (memberToRemove == null) {
            String outputText = String.format("Member %s %s - %s not found in database.\n", firstName, lastName, dob);
            this.outputTextArea.appendText(outputText);
            return;
        } else if (memberDatabase.remove(memberToRemove)) {
            String outputText = String.format("Removed %s %s - %s from the database.\n", firstName, lastName, dob);
            this.outputTextArea.appendText(outputText);
            return;
        }

        outputTextArea.appendText(String.format("Something went seriously wrong when trying to remove %s %s - %s\n", firstName, lastName, dob));
    }

    /**
     * @param classSchedule The ClassSchedule Object that has all the class schedules.
     * @param className The name of the class.
     * @param instructorName The name of the instructor.
     * @param location The name of the location.
     * @return The index of the specified class in the class schedule.
     */
    private int returnClassIndex(ClassSchedule classSchedule, String className, String instructorName, Location location) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        FitnessClass fitnessClass = new FitnessClass(null, className, instructorName, location);

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClass.equalsNonVerbose(fitnessClasses[x])) {
                return x;
            }
        }

        return Constants.NOT_FOUND;
    }

    /**
     * @param classSchedule Schedule of all fitness classes
     * @param memberToAdd The member we want to check for a time conflict
     * @param fitnessClass The fitness class desired to check in the {@code classSchedule} for a time conflict
     * @return The fitness class that has a time conflict in {@code fitnessClass}.
     */
    private FitnessClass checkForTimeConflict(ClassSchedule classSchedule, Member memberToAdd, FitnessClass fitnessClass) {
        Time time = Time.returnTimeFromString(fitnessClass.getTime());
        FitnessClass[] allClasses = classSchedule.getAllClasses();

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClass.equalsNonVerbose(allClasses[x])) {
                continue;
            }
            if (allClasses[x].findMemberInClass(memberToAdd) != Constants.NOT_FOUND && allClasses[x].getTime().equalsIgnoreCase(time.getTime())) {
                return allClasses[x];
            }
        }

        return null;
    }

    /**
     * @param className The name of the class
     * @param classSchedule The ClassSchedule Object that has all the class schedules.
     * @return Whether the class name is valid against the {@code classSchedule}.
     */
    private boolean checkValidClassName(String className, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClasses[x].getClassName().equalsIgnoreCase(className)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param instructorName The name of the instructor
     * @param classSchedule The ClassSchedule Object that has all the class schedules.
     * @return Whether the instructor is valid against the {@code classSchedule}.
     */
    private boolean checkValidInstructor(String instructorName, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClasses[x].getInstructorName().equalsIgnoreCase(instructorName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param location The fitness class location as a {@code String}
     * @return Checks whether the {@code location} is valid.
     */
    private boolean checkValidLocation(String location) {
        return Location.returnEnumFromString(location) != null;
    }


    /**
     * @param inputData The data passed in the fitness class tab when the client is interacting with fitness class database.
     * @param member The member that we want to check for.
     * @param classSchedule The ClassSchedule Object that has all the class schedules.
     * @return Whether the input from the fitness class tab in the GUI is valid and whether this user / gust is able to be added / removed
     * from the fitness class database.
     */
    public boolean checkGeneralInput(String[] inputData, Member member, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        if (!checkValidClassName(inputData[1], classSchedule)) {
            this.outputTextArea.appendText(String.format("%s - class does not exist.\n", inputData[1]));
            return false;
        } else if (!checkValidInstructor(inputData[2], classSchedule)) {
            this.outputTextArea.appendText(String.format("%s — instructor does not exist.\n", inputData[2]));
            return false;
        } else if (!checkValidLocation(inputData[3])) {
            this.outputTextArea.appendText(String.format("%s — location does not exist.\n", inputData[3]));
            return false;
        }

        int classIndex = returnClassIndex(classSchedule, inputData[1], inputData[2], Location.returnEnumFromString(inputData[3]));

        if (classIndex == Constants.NOT_FOUND) {
            this.outputTextArea.appendText(String.format("%s class by %s does not exist.\n", inputData[1], inputData[2]));
            return false;
        } else if (!(new Date(inputData[6]).isValid())) {
            this.outputTextArea.appendText(String.format("DOB %s: invalid calendar date!\n", new Date(inputData[6])));
            return false;
        } else if (member == null) {
            this.outputTextArea.appendText(String.format("%s %s %s does not exist in database.\n", inputData[4], inputData[5], new Date(inputData[6])));
            return false;
        } else if (fitnessClasses[classIndex].checkIfMemberExpired(member)) {
            this.outputTextArea.appendText(String.format("%s %s %s membership expired.\n", inputData[4], inputData[5], member.getDob()));
            return false;
        }

        return true;
    }

    /**
     * @param inputData The data passed in the fitness class tab when the client is interacting with fitness class database.
     * @param memberToCheckIn The member we want to check in.
     * @param classSchedule The ClassSchedule Object that has all the class schedules.
     * @return Checks whether the entered fitness class corresponds to a fitness class in the database and whether the member / guest is able to
     * actually check in. This function checks and calls all related helper functions to check if a member is able to be added into the member database.
     */
    private boolean checkFitnessClassesWithCheckInClass(String[] inputData, Member memberToCheckIn, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        if (!checkGeneralInput(inputData, memberToCheckIn, classSchedule)) {
            return false;
        }

        int classIndex = returnClassIndex(classSchedule, inputData[1], inputData[2], Location.returnEnumFromString(inputData[3]));

        if (!(memberToCheckIn instanceof Family) && !memberToCheckIn.getLocation().equals(fitnessClasses[classIndex].getLocation())) {
            this.outputTextArea.appendText(String.format("%s %s checking in %s - standard membership location restriction", inputData[4], inputData[5], Location.returnEnumFromString(inputData[3])));
            return false;
        } else if ( !(fitnessClasses[classIndex].findMemberInClass(memberToCheckIn) == Constants.NOT_FOUND) ) {
            this.outputTextArea.appendText(String.format("%s %s has already checked into %s.\n", inputData[4], inputData[5], fitnessClasses[classIndex].getClassName()));
            return false;
        }

        FitnessClass timeConflict = this.checkForTimeConflict(classSchedule, memberToCheckIn, fitnessClasses[classIndex]);

        if (timeConflict != null) {
            this.outputTextArea.appendText(String.format("%s time conflict -- %s %s has already checked into %s\n", fitnessClasses[classIndex].getClassName(), inputData[4], inputData[5], Objects.requireNonNull(this.checkForTimeConflict(classSchedule, memberToCheckIn, fitnessClasses[classIndex])).getClassName()));
            return false;
        }

        return true;
    }

    /**
     * @return Checks whether the information added in the fitness class tab is valid. Checks all inputs by the clients and will throw
     * according error / success messages based on their inputs.
     */
    private boolean checkFitnessClassInputs() {
        if (classSchedule.getNumClasses() == 0) {
            this.outputTextArea.appendText("No classes have been added yet. Please go to the information hub to load all fitness classes\n");
            return false;
        }

        if (this.classChoiceBox.getValue() == null) {
            this.outputTextArea.appendText("Class Field Empty! Please go to the information hub to load in the fitness classes.\n");
            return false;
        } else if (this.instructorChoiceBox.getValue() == null) {
            this.outputTextArea.appendText("Instructor Name Field Empty! Please go to the information hub to load in the fitness classes to get a list of instructor names.\n");
            return false;
        } else if (this.firstNameFitnessClassTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("First Name Field Empty!\n");
            return false;
        } else if (this.lastNameFitnessClassTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("Last Name Field Empty\n");
            return false;
        } else if (this.fitnessClassDobDatePicker.getValue() == null) {
            this.outputTextArea.appendText("DOB Not Selected\n");
            return false;
        } else if (this.locationFitnessClassChoiceBox.getValue() == null) {
            this.outputTextArea.appendText("Location Is Empty\n");
            return false;
        }

        return true;
    }

    @FXML
    protected void clearFitnessClassInputs() {
        this.classChoiceBox.setValue(null);
        this.instructorChoiceBox.setValue(null);
        this.locationFitnessClassChoiceBox.setValue(null);
        this.firstNameFitnessClassTextField.clear();
        this.lastNameFitnessClassTextField.clear();
        this.fitnessClassDobDatePicker.getEditor().clear();
        this.fitnessClassDobDatePicker.setValue(null);
    }

    /**
     * Will check in a member into a fitness class if they are allowed into the fitness class. We will check for invalid or missing inputs
     * in the GUI and we will check for all possible reasons a member may not be allowed to check into the fitness class (like location errors or expired memberships). If they
     * are allowed to check into the fitness class, we check them into the corresponding fitness class.
     */
    @FXML
    protected void checkInMember() {
        if (checkFitnessClassInputs()) {
            FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
            String firstName = this.firstNameFitnessClassTextField.getText().trim();
            String lastName = this.lastNameFitnessClassTextField.getText().trim();
            Date dob = new Date(this.fitnessClassDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            Location location = Location.returnEnumFromString(this.locationFitnessClassChoiceBox.getValue());
            String className = this.classChoiceBox.getValue();
            String instructorName = this.instructorChoiceBox.getValue();

            Member memberToCheckIn = memberDatabase.getMember(memberDatabase.find(new Member(firstName, lastName, dob, null, location)));

            assert location != null;
            String[] dataArray = {null, className, instructorName, location.getTown(), firstName, lastName, dob.toString()};
            this.clearFitnessClassInputs();

            if (!checkFitnessClassesWithCheckInClass(dataArray, memberToCheckIn, classSchedule)) {
                return;
            }


            int classIndex = returnClassIndex(classSchedule, dataArray[1], dataArray[2], Location.returnEnumFromString(dataArray[3]));

            fitnessClasses[classIndex].checkIn(memberToCheckIn);
            String outputText = String.format("%s %s checked into %s\n", memberToCheckIn.getFname(), memberToCheckIn.getLname(), fitnessClasses[classIndex]);
            this.outputTextArea.appendText(outputText);
        }
    }

    /**
     * Will check in a guest into a fitness class if they are allowed into the fitness class. We will check for invalid or missing inputs
     * in the GUI and we will check for all possible reasons the guest may not be allowed to check into the fitness class (like location errors or expired memberships or invalid guest passes). If they
     * are allowed to check into the fitness class, we check them into the corresponding fitness class.
     */
    @FXML
    protected void checkInGuest() {
        if (checkFitnessClassInputs()) {
            FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
            String firstName = this.firstNameFitnessClassTextField.getText().trim();
            String lastName = this.lastNameFitnessClassTextField.getText().trim();
            Date dob = new Date(this.fitnessClassDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            Location location = Location.returnEnumFromString(this.locationFitnessClassChoiceBox.getValue());
            String className = this.classChoiceBox.getValue();
            String instructorName = this.instructorChoiceBox.getValue();

            Member member = new Member(firstName, lastName, dob, null, location);
            member = memberDatabase.getMember(memberDatabase.find(member));

            this.clearFitnessClassInputs();

            if (member instanceof Family) { // If member is a family instance, that means it may also be a Premium. Either way, they have guest permissions
                FitnessClass fitnessClassToCheckInto;

                if (((Family) member).getGuestPasses() <= 0) {
                    this.outputTextArea.appendText(String.format("%s %s ran out of guest passes\n", member.getFname(), member.getLname()));
                    return;
                }

                if (!member.getLocation().equals(location)) {
                    this.outputTextArea.appendText(String.format("%s %s Guest checking in %s - guest location restricted\n", firstName, lastName, location));
                    return;
                }

                for (int x = 0; x < classSchedule.getNumClasses(); x++) {
                    if (fitnessClasses[x].getClassName().equalsIgnoreCase(className) &&
                            fitnessClasses[x].getInstructorName().equalsIgnoreCase(instructorName) && fitnessClasses[x].getLocation().equals(location)) {
                        fitnessClassToCheckInto = fitnessClasses[x];

                        ((Family) member).setGuestPasses((short) (((Family) member).getGuestPasses() - 1));
                        fitnessClassToCheckInto.checkInGuestMember(member);
                        this.outputTextArea.appendText(String.format("%s %s (guest) checked into %s\n", member.getFname(), member.getLname(), fitnessClasses[x]));
                        return;
                    }
                }

                this.outputTextArea.appendText("Invalid class!\n");
            } else {
                this.outputTextArea.appendText("Standard membership - guest check-in is not allowed.\n");
            }
        }

    }

    /**
     * Will check out a member from a fitness class if they are already checked into a fitness class. We will check for invalid or missing inputs
     * in the GUI and we will check for all possible reasons a member may not be allowed to check out of the class (like not already being checked into a fitness class). If they
     * are allowed to check out the fitness class, we check them out of the corresponding fitness class.
     */
    @FXML
    protected void checkOutMember() {
        if (checkFitnessClassInputs()) {
            FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
            String firstName = this.firstNameFitnessClassTextField.getText().trim();
            String lastName = this.lastNameFitnessClassTextField.getText().trim();
            Date dob = new Date(this.fitnessClassDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            Location location = Location.returnEnumFromString(this.locationFitnessClassChoiceBox.getValue());
            String className = this.classChoiceBox.getValue();
            String instructorName = this.instructorChoiceBox.getValue();

            assert location != null;
            String[] inputData = {null, className, instructorName, location.getTown(), firstName, lastName, dob.toString()};
            Member memberToRemove = memberDatabase.getMember(memberDatabase.find(new Member(inputData[4], inputData[5], new Date(inputData[6]), null, null)));

            this.clearFitnessClassInputs();

            if (!checkGeneralInput(inputData, memberToRemove, classSchedule)) {
                return;
            }

            int classIndex = returnClassIndex(classSchedule, inputData[1], inputData[2], Location.returnEnumFromString(inputData[3]));


            if (classIndex == Constants.NOT_FOUND) {
                this.outputTextArea.appendText("Class Not Found\n");
                return;
            }

            if (classSchedule.getSpecificClass(classIndex).findMemberInClass(memberToRemove) == Constants.NOT_FOUND) {
                this.outputTextArea.appendText(String.format("%s %s did not check in\n", memberToRemove.getFname(), memberToRemove.getLname()));
                return;
            }


            fitnessClasses[classIndex].dropClass(memberToRemove);
            this.outputTextArea.appendText(String.format("%s %s is done with %s\n", memberToRemove.getFname(), memberToRemove.getLname(), fitnessClasses[classIndex].getClassName()));
        }
    }

    /**
     * Will check out a guest from a fitness class if they are already checked into a fitness class. We will check for invalid or missing inputs
     * in the GUI and we will check for all possible reasons a guest may not be allowed to check out of the class (like not already being checked into a fitness class). If they
     * are allowed to check out the fitness class, we check them out of the corresponding fitness class.
     */
    @FXML
    protected void checkOutGuest() {
        if (checkFitnessClassInputs()) {
            FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
            String firstName = this.firstNameFitnessClassTextField.getText().trim();
            String lastName = this.lastNameFitnessClassTextField.getText().trim();
            Date dob = new Date(this.fitnessClassDobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            Location location = Location.returnEnumFromString(this.locationFitnessClassChoiceBox.getValue());
            String className = this.classChoiceBox.getValue();
            String instructorName = this.instructorChoiceBox.getValue();

            Member member = new Member(firstName, lastName, dob, null, location);
            member = memberDatabase.getMember(memberDatabase.find(member));
            assert location != null;
            String[] inputData = {null, className, instructorName, location.getTown(), firstName, lastName, dob.toString()};

            this.clearFitnessClassInputs();

            if (!checkGeneralInput(inputData, member, classSchedule)) {
                return;
            }

            if (member instanceof Family) {
                for (int x = 0; x < classSchedule.getNumClasses(); x++) {
                    if (fitnessClasses[x].findGuestMemberInClass(member) != Constants.NOT_FOUND &&
                            fitnessClasses[x].getClassName().equalsIgnoreCase(className) &&
                            fitnessClasses[x].getInstructorName().equalsIgnoreCase(instructorName) &&
                            fitnessClasses[x].getLocation().equals(location)) {

                        ((Family) member).setGuestPasses((short) (((Family) member).getGuestPasses() + 1));
                        fitnessClasses[x].dropGuestMember(member);
                        outputTextArea.appendText(String.format("%s %s (guest) is done with %s\n", member.getFname(), member.getLname(), fitnessClasses[x].getClassName()));
                        return;
                    }
                }

                this.outputTextArea.appendText("Invalid class!\n");
            } else {
                this.outputTextArea.appendText("Standard Membership - No guests to check out!\n");
            }
        }
    }


    /**
     * Prints out all the members in the database, no sorting, to the GUI.
     */
    @FXML
    protected void print() {
        this.outputTextArea.appendText(memberDatabase.print());
    }

    /**
     * Prints out all the members in the database, sorted by county then zipcode, to the GUI.
     */
    @FXML
    protected void printByCounty() {
        this.outputTextArea.appendText(memberDatabase.printByCounty());
    }

    /**
     * Prints out all the members in the database, sorted by expiration date, to the GUI.
     */
    @FXML
    protected void printByExpiration() {
        this.outputTextArea.appendText(memberDatabase.printByExpirationDate());
    }

    /**
     * Prints out all the members in the database, sorted by last name then first name, to the GUI.
     */
    @FXML
    protected void printByName() {
        this.outputTextArea.appendText(memberDatabase.printByName());
    }

    /**
     * Prints out all the members in the database with their membership fee, no sorting, to the GUI.
     */
    @FXML
    protected void printWithMembershipFee() {
        this.outputTextArea.appendText(memberDatabase.printWithMembershipFee());
    }

    /**
     * Loads in historical members to member database from memberList.txt.
     * Prints out members loaded in to GUI.
     */
    @FXML
    protected void loadHistoricalMembers() {
        this.outputTextArea.appendText(memberDatabase.loadHistoricalMembers());
    }

    /**
     * Loads in fitness class schedule to class schedule from the file classSchedule.txt.
     * Prints out fitness class loaded in to GUI.
     */
    @FXML
    protected void loadFitnessClasses() {
        this.outputTextArea.appendText(classSchedule.loadFitnessClasses(this.classChoiceBox, this.instructorChoiceBox));
    }

    /**
     * Prints out class schedule to text area in GUI.
     */
    @FXML
    protected void printFitnessClassSchedule() {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        if (classSchedule.getNumClasses() <= 0) {
            this.outputTextArea.appendText("Fitness class schedule is empty.\n");
            return;
        }

        this.outputTextArea.appendText("-Fitness classes-\n");

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            this.outputTextArea.appendText(fitnessClasses[x].toString());
        }
        this.outputTextArea.appendText("-end of class list-\n");
    }
}