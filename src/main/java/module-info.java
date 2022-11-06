module gymmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens gymmanager to javafx.fxml;
    exports gymmanager;
}