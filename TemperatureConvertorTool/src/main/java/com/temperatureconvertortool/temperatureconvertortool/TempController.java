package com.temperatureconvertortool.temperatureconvertortool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TempController implements Initializable {

	@FXML
	public Label welcomeLabel;
	@FXML
	public ChoiceBox<String> choiceBox;
	@FXML
	public TextField userInputValue;
	@FXML
	public Button convertBtn;
	public static final String c_to_f_text = "Celsius to Fahrenheit";
	public static final String f_to_c_text = "Fahrenheit to Celsius";
	public static final String c_to_k_text = "Celsius to kelvin";
	public static final String k_to_c_text = "kelvin to Celsius";
	public static final String f_to_k_text = "Fahrenheit to kelvin";
	public static final String k_to_f_text = "kelvin to Fahrenheit";

	private int isConversion = 1;

	private String unit = "F";

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		//choiceBox
		choiceBox.getItems().add(c_to_f_text);
		choiceBox.getItems().add(f_to_c_text);
		choiceBox.getItems().add(c_to_k_text);
		choiceBox.getItems().add(k_to_c_text);
		choiceBox.getItems().add(f_to_k_text);
		choiceBox.getItems().add(k_to_c_text);

		//default value of ChoiceBox
		choiceBox.setValue(c_to_f_text);

		choiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) ->{
			switch(t1){
				case c_to_f_text:
					isConversion = 1;
					unit = "F";
					break;
				case f_to_c_text:
					isConversion = 2;
					unit = "C";
					break;
				case c_to_k_text:
					isConversion = 3;
					unit = "K";
					break;
				case k_to_c_text:
					isConversion = 4;
					unit = "C";
					break;
				case f_to_k_text:
					isConversion = 5;
					unit = "K";
					break;
				case k_to_f_text:
					isConversion = 6;
					unit = "F";
					break;
				default:
					System.out.println("Invalid choice");
					break;
			}
		});

		//Convert button logic
		convertBtn.setOnAction(event ->{
			convert();
		});
	}

	private void convert() {
		//taking value from user
		String input = userInputValue.getText();

		//exception handling
		float enteredTemperature = 0.0f;
		try{
			enteredTemperature = Float.parseFloat(input);
		}catch(Exception exception){
			warnUser();
			return;
		}

		//logic
		float newTemperature = 0.0f;
		if (isConversion ==1){
			newTemperature = (enteredTemperature * 9/5) +32; //celsius to fahrenheit
		}else if (isConversion == 2){
			newTemperature = (enteredTemperature - 32) * 5/9; //fahrenheit to celsius
		}else if (isConversion == 3){
			newTemperature = (float) (enteredTemperature + 273.15); //celsius to kelvin
		}else if (isConversion == 4){
			newTemperature = (float) (enteredTemperature - 273.15); //kelvin to celsius
		}else if (isConversion == 5){
			newTemperature = (float) ((enteredTemperature - 32) * 5/9 + 273.15); //fahrenheit to kelvin
		}else if (isConversion == 6){
			newTemperature = (float) ((enteredTemperature - 273.15) * 9/5 + 32); //kelvin to fahrenheit
		}
		display(newTemperature);
	}

	private void warnUser() {
		Alert displayRes = new Alert(Alert.AlertType.ERROR);
		displayRes.setTitle("Warning!");
		displayRes.setContentText("Please enter a valid temperature!");
		displayRes.showAndWait();
	}

	private void display(Float newTemperature) {

		System.out.println("The new temperature is : "+ newTemperature + unit);

		Alert displayRes = new Alert(Alert.AlertType.INFORMATION);
		displayRes.setTitle("Result");
		displayRes.setContentText("The new temperature is : "+ newTemperature + unit);

		displayRes.showAndWait();
	}
}