import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
		public static void main(String[] args) {
				launch(args);
		}

		@Override
		public void start(Stage primaryStage) {
				primaryStage.setTitle("SGPA to CGPA Converter");

				Image appIcon = new Image(getClass().getResourceAsStream("vg.png"));
				primaryStage.getIcons().add(appIcon);

				VBox vbox = new VBox(5);
				vbox.setAlignment(Pos.CENTER);


				ComboBox<Integer> semesterComboBox = new ComboBox<>();
				semesterComboBox.getItems().addAll(1, 2, 3, 4, 5, 6);
				semesterComboBox.setValue(2);
				Label semesterLabel = new Label("Select number of semesters: ");

				Label lab = new Label("Add sum of SGPA: ");
				TextField input = new TextField();
				Button res = new Button("Convert");
				Label labRes = new Label("Your CGPA is: ");
				res.setOnAction(e -> showAnswer(input, semesterComboBox.getValue(), labRes));
				vbox.getChildren().addAll(semesterLabel, semesterComboBox, lab, input, res, labRes);
				Scene scene = new Scene(vbox, 300, 160);

				scene.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
		}
		
		private void showAnswer(TextField input, int numSemesters, Label labRes) {
				try {
						double inputText = Double.parseDouble(input.getText());
						double result = inputText / numSemesters;
						labRes.setText("Your CGPA is: " + result);
				} catch (NumberFormatException ex) {
						labRes.setText("Invalid input");
				}
		}
}
