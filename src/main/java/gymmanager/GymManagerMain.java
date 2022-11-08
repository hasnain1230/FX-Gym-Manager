package gymmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class launches the Gym Manager GUI.
 * @author Hasnain Ali, Carolette Saguil
 */
public class GymManagerMain extends Application {
    /**
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages.
     * @throws IOException
     * This is called by Application.launch() on start up. This method is responsible for
     * loading the fxml file and setting the scene. Upon successful loading of the fxml file, the controller will
     * also have been set up and the controller {@code initialize()} method will have been called to set up the basic
     * state of the application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GymManagerView.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 600);

        stage.setResizable(true);
        stage.alwaysOnTopProperty();
        stage.setAlwaysOnTop(true);
        stage.setTitle("Gym Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args Command line arguments that will not be used here.
     * This is the main method that calls Application.launch() to launch the application.
     */
    public static void main(String[] args) {
        Application.launch();
    }
}