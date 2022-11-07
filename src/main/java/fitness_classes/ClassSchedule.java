package fitness_classes;

import constants.Constants;
import enums.Location;
import enums.Time;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class defines the class schedule using a single one dimensional array.
 * That array will store all the fitness classes loaded into the schedule.
 * @author Hasnain Ali, Carolette Saguil
 */
public class ClassSchedule {
    /**
     * Classes array / schedule
     */
    private FitnessClass[] classes;

    /**
     * Current size of class schedule.
     */
    private int numClasses;

    /**
     * Instantiates an empty class schedule where it's size is 0. Instantiates a fitness class array of default array size of 4
     * that will store fitness classes loaded into the schedule and later grow as needed.
     */
    public ClassSchedule() {
        this.classes = new FitnessClass[Constants.ARRAY_DEFAULT_SIZE];
        this.numClasses = 0;
    }

    /**
     * @return Returns fitness class array that stores all the fitness classes.
     */
    public FitnessClass[] getAllClasses() {
        return this.classes;
    }

    /**
     * @param index Index of class.
     * @return Returns fitness class at index of fitness class array.
     */
    public FitnessClass getSpecificClass(int index) {
        return this.classes[index];
    }

    /**
     * @return Returns the size of / the number of fitness classes in the class schedule.
     */
    public int getNumClasses() {
        return this.numClasses;
    }

    /**
     * Adds fitness class to the class schedule.
     * Fails if fitness class already exists in the class schedule.
     * @param fitnessClass Fitness class to add.
     * @return true if fitness class was added, false otherwise.
     */
    public boolean addClass(FitnessClass fitnessClass) {
        for (int x = 0; x < numClasses; x++) {
            if (this.classes[x].equals(fitnessClass)) {
                return false;
            }
        }

        if (this.classes.length == numClasses) {
            this.grow();
        }


        this.classes[numClasses] = fitnessClass;
        numClasses++;
        return true;
    }

    /**
     * Grows the class schedule when the current schedule is full.
     * Size limited by JVM memory allocation. (How big Java will let you make an array before you run out of space).
     */
    private void grow() {
        FitnessClass[] fitnessClasses = new FitnessClass[this.numClasses + Constants.ARRAY_INCREMENT_SIZE];
        for (int x = 0; x < this.numClasses; x++) {
            fitnessClasses[x] = this.classes[x];
        }

        this.classes = fitnessClasses;
    }

    /**
     * @param classChoiceBox The class choice box for the GUI
     * @param instructorChoiceBox The instructor choice box for the GUI
     * @return A string with the output designated for output text area in the GUI.
     * This method will also load in all fitness classes into the Class Schedule database.
     */
    public String loadFitnessClasses(ChoiceBox<String> classChoiceBox, ChoiceBox<String> instructorChoiceBox) {
        StringBuilder outputTextArea = new StringBuilder();

        try {
            File file = new File(Constants.CLASS_SCHEDULE_FROM_CONTENT_ROOT);
            Scanner sc = new Scanner(file);
            outputTextArea.append("-Fitness classes loaded-\n");

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split("\\s+");

                String className = line[0];

                if (!classChoiceBox.getItems().contains(className)) {
                    classChoiceBox.getItems().add(className);
                }

                String instructorName = line[1];

                if (!instructorChoiceBox.getItems().contains(instructorName)) {
                    instructorChoiceBox.getItems().add(instructorName);
                }

                Time time = Time.returnTimeEnumFromTimeOfDay(line[2]);
                Location location = Location.returnEnumFromString(line[3]);

                FitnessClass fitnessClass = new FitnessClass(time, className, instructorName, location);
                outputTextArea.append(fitnessClass.toString());

                this.addClass(fitnessClass);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            outputTextArea.append("File is not found!\n");
            return outputTextArea.toString();
        }

        outputTextArea.append("-end of list-\n");

        return outputTextArea.toString();
    }

}
