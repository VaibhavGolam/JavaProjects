module com.vaibhavgolam.connect4d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vaibhavgolam.connectfour to javafx.fxml;
    exports com.vaibhavgolam.connectfour;
}