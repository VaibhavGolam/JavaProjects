module com.temperatureconvertortool.temperatureconvertortool {
		requires javafx.controls;
		requires javafx.fxml;


		opens com.temperatureconvertortool.temperatureconvertortool to javafx.fxml;
		exports com.temperatureconvertortool.temperatureconvertortool;
}