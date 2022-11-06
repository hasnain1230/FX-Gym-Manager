package gymmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GymManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GymManagerView.fxml"));
        Parent root = fxmlLoader.load();
        GymManagerController gymManagerController = fxmlLoader.getController();
        gymManagerController.initData();
        Scene scene = new Scene(root, 500, 600);

        stage.setResizable(false);
        stage.setTitle("Gym Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}