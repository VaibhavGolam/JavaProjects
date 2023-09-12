package com.temperatureconvertortool.temperatureconvertortool;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Temp extends Application {
		@Override
		public void start(Stage stage) throws IOException {
				FXMLLoader fxmlLoader = new FXMLLoader(Temp.class.getResource("hello-view.fxml"));

				VBox rootNode = fxmlLoader.load();
				MenuBar menuBar = createMenu();
				rootNode.getChildren().add(0,menuBar);

				Scene scene = new Scene(rootNode);
				stage.setScene(scene);
				stage.setTitle("Temperature Convertor");
				stage.show();
		}

		public MenuBar createMenu(){

				//file
				Menu fileMenu = new Menu("File");
				MenuItem quitMenuItem = new MenuItem("Quit");
				fileMenu.getItems().addAll(quitMenuItem);

				//function of quit
				quitMenuItem.setOnAction(event -> {
						Platform.exit();
						System.exit(0);
				} );

				//help
				Menu helpMenu = new Menu("Help");
				MenuItem aboutMenuItem = new MenuItem("About");
				helpMenu.getItems().addAll(aboutMenuItem);

				//function of about
				aboutMenuItem.setOnAction(event -> {
						aboutApp();
				});

				//menuBar
				MenuBar menuBar = new MenuBar();
				menuBar.getMenus().addAll( fileMenu, helpMenu);
				return menuBar;
		}

		public void aboutApp(){
				Alert alertAbout = new Alert(Alert.AlertType.INFORMATION);
				alertAbout.setTitle("About App & Me");
				alertAbout.setHeaderText("Made by Vaibhav Golam");
				alertAbout.setContentText("This software is designed for use as a Temperature Converter");
				alertAbout.showAndWait();
		}



		public static void main(String[] args) {
				launch();
		}
}