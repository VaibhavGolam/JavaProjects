import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

				StackPane root = new StackPane();
				VBox vbox = new VBox(5);
				vbox.setAlignment(Pos.CENTER);

				ComboBox<Integer> semesterComboBox = new ComboBox<>();
				semesterComboBox.getItems().addAll(1, 2, 3, 4, 5, 6);
				semesterComboBox.setValue(2);
				Label semesterLabel = new Label("Select number of semesters: ");

				Label lab = new Label("Add sum of all SGPA: ");
				TextField input = new TextField();
				Button res = new Button("Convert");
				Label appreciate = new Label("Success in life is not about grades and percentage");

				HBox labResContainer = new HBox();
				labResContainer.setAlignment(Pos.CENTER);

				res.setOnAction(e -> showAnswer(input, semesterComboBox.getValue(), labResContainer, appreciate));
				vbox.getChildren().addAll(semesterLabel, semesterComboBox, lab, input, res, labResContainer, appreciate);

				root.getChildren().add(vbox);
				Scene scene = new Scene(root, 300, 190);

				scene.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
		}

		private void showAnswer(TextField input, int numSemesters, HBox labResContainer, Label appreciate) {
				try {
						double inputText = Double.parseDouble(input.getText());
						double result = inputText / numSemesters;
						labResContainer.getChildren().clear();

						Text boldText = new Text("Your CGPA is: ");
						boldText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
						boldText.setFill(Color.WHITE);
						boldText.setTextAlignment(TextAlignment.CENTER);

						Text resultText = new Text("" + result);
						resultText.setFont(Font.font("Arial", FontWeight.BOLD, 13));
						resultText.setFill(Color.WHITE);
						//resultText.setUnderline(true);

						labResContainer.getChildren().addAll(boldText, resultText);

						Platform.runLater(() -> {
								if (result >= 9) {
										appreciate.setText("You are outstanding. Keep up the incredible work!");
								} else if (result >= 8) {
										appreciate.setText("You are phenomenal. Your dedication shines through!");
								} else if (result >= 7) {
										appreciate.setText("You are good. Your efforts are truly commendable.");
								} else if (result >= 6) {
										appreciate.setText("You are great. Your progress is inspiring!");
								} else {
										appreciate.setText("You are doing well. Keep pushing forward!");
								}
						});
				} catch (NumberFormatException ex) {
						labResContainer.getChildren().clear();
						labResContainer.getChildren().add(new Text("Invalid input"));
				}
		}
}

