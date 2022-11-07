package gymmanager;

import constants.Constants;
import date.Date;
import enums.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import member.Family;
import member.Member;
import member.MemberDatabase;
import member.Premium;

import java.time.format.DateTimeFormatter;

public class GymManagerController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private TextField locationTextField;
    @FXML
    private RadioButton standardMembershipRadioButton;
    @FXML
    private RadioButton familyMembershipRadioButton;
    @FXML
    private RadioButton premiumMembershipRadioButton;
    @FXML
    private TextArea outputTextArea;
    private static MemberDatabase memberDatabase;

    protected void initData() {
        memberDatabase = new MemberDatabase();
    }

    private boolean checkAddInputs() {
        if (this.firstNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("First Name Field Empty!\n");
            return false;
        } else if (this.lastNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("Last Name Field Empty\n");
            return false;
        } else if (this.dobDatePicker.getValue() == null) {
            this.outputTextArea.appendText("Date Not Selected\n");
            return false;
        } else if (this.locationTextField.getText().isEmpty()) {
            this.outputTextArea.appendText("Location Is Empty\n");
            return false;
        } else if (!(this.standardMembershipRadioButton.isSelected() || this.familyMembershipRadioButton.isSelected() || this.premiumMembershipRadioButton.isSelected())) {
            this.outputTextArea.appendText("Membership Not Selected\n");
            return false;
        }

        return true;
    }

    @FXML
    protected void addMember() {
        if (checkAddInputs()) {
            Member member;
            String firstName = this.firstNameTextField.getText().trim();
            String lastName = this.lastNameTextField.getText().trim();
            Date dob = new Date(this.dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            Location location = Location.returnEnumFromString(this.locationTextField.getText().trim());

            if (location == null) {
                outputTextArea.appendText("Invalid Location\n");
                return;
            } else if (!dob.isValid()) {
                outputTextArea.appendText(String.format("DOB %s: invalid calendar date!\n", dob));
                return;
            } else if (!dob.checkMemberAge()) {
                outputTextArea.appendText(String.format("DOB %s: must be 18 or older to join!\n", dob));
                return;
            }

            // FIXME: Check member expiration date?


            if (this.standardMembershipRadioButton.isSelected()) {
                member = new Member(firstName, lastName, dob, new Date(), location);
                memberDatabase.add(member);
                String outputString = String.format("Added %s %s - %s as a Standard member to the database.\n", member.getFname(), member.getLname(), dob);
                this.outputTextArea.appendText(outputString);
            } else if (this.familyMembershipRadioButton.isSelected()) {
                member = new Family(firstName, lastName, dob, new Date(), location, Constants.FAMILY_GUEST_PASSES);
                memberDatabase.add(member);
                String outputString = String.format("Added %s %s - %s as a Family member to the database.\n", member.getFname(), member.getLname(), dob);
                this.outputTextArea.appendText(outputString);
            } else if (this.premiumMembershipRadioButton.isSelected()) {
                member = new Premium(firstName, lastName, dob, new Date(), location, Constants.PREMIUM_GUEST_PASS);
                memberDatabase.add(member);
                String outputString = String.format("Added %s %s - %s as a Premium member to the database.\n", member.getFname(), member.getLname(), dob);
                this.outputTextArea.appendText(outputString);
            } else {
                this.outputTextArea.appendText("Something has gone seriously wrong!\n");
            }
        }
    }

    @FXML
    protected void removeMember() {
        if (this.firstNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("First Name Field Empty!\n");
            return;
        } else if (this.lastNameTextField.getText().trim().isEmpty()) {
            this.outputTextArea.appendText("Last Name Field Empty\n");
            return;
        } else if (this.dobDatePicker.getValue() == null) {
            this.outputTextArea.appendText("Date Not Selected\n");
            return;
        }

        String firstName = this.firstNameTextField.getText().trim();
        String lastName = this.lastNameTextField.getText().trim();
        Date dob = new Date(this.dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        Member member = new Member(firstName, lastName, dob, null, null);
        Member memberToRemove = memberDatabase.getMember(memberDatabase.find(member));
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


}