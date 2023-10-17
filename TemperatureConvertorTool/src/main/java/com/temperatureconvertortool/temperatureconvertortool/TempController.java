package com.temperatureconvertortool.temperatureconvertortool;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TempController implements Initializable {


	@FXML
	public Pane myPane;
	@FXML
	public Button btn;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Circle circle = new Circle();
		circle.setRadius(20);
		circle.setFill(Color.RED);
		circle.setCenterX(50);
		circle.setCenterY(50);

		myPane.getChildren().add(circle);
		
		btn.setOnAction(actionEvent -> startAnimation(circle));
	}

	private void startAnimation(Circle circle) {
		TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5),circle);
		transition.setByY(200);
		transition.play();
	}
}