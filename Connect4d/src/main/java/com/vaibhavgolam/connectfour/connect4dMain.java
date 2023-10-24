package com.vaibhavgolam.connectfour;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class connect4dMain extends Application {
    private connect4dController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(connect4dMain.class.getResource("gameDesign.fxml"));
        GridPane rootGridPane = loader.load();

        controller = loader.getController();
        controller.createPlayground();
        Scene scene = new Scene(rootGridPane);

        stage.setTitle("Connect4D");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        //MenuBar
        MenuBar menuBar = createMenu();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        Pane menuPane = (Pane) rootGridPane.getChildren().get(0);
        menuPane.getChildren().add(menuBar);
    }

    private MenuBar createMenu() {
       //Menu creation
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");

        //New game
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> controller.resetGame());

        //Reset game
        MenuItem resetGame = new MenuItem("Reset Game");
        resetGame.setOnAction(event -> controller.resetGame());

        //Exit game
        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setOnAction(event -> exitGame());

        //About dev
        MenuItem aboutDev = new MenuItem("About Me");
        aboutDev.setOnAction(event -> aboutDev());

        //about app
        MenuItem aboutApp = new MenuItem("About Connect4");
        aboutApp.setOnAction(event -> aboutApp());

        //Separator
        SeparatorMenuItem separatorOfFileMenu = new SeparatorMenuItem();
        SeparatorMenuItem separatorOfHelpMenu = new SeparatorMenuItem();

        //adding fileItem to fileMenu
        fileMenu.getItems().addAll(newGame,resetGame,separatorOfFileMenu,exitGame);

        //adding menuItem to helpMenu
        helpMenu.getItems().addAll(aboutDev,separatorOfHelpMenu,aboutApp);

        //Menubar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu,helpMenu);

        return menuBar;
    }

    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }



    private void aboutApp() {
        Alert alertAboutApp = new Alert(Alert.AlertType.INFORMATION);
        alertAboutApp.setTitle("About Connect Four");
        alertAboutApp.setHeaderText("How to play?");

        //Set width and height
        alertAboutApp.getDialogPane().setMinWidth(400);
        alertAboutApp.getDialogPane().setMinHeight(200);

        alertAboutApp.setContentText("Connect Four is a two-player connection game in which the players first choose a color and then take turns dropping colored discs from the top into a seven-column, six-row vertically suspended grid. The pieces fall straight down, occupying the next available space within the column. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs. Connect Four is a solved game. The first player can always win by playing the right moves");
        alertAboutApp.show();
    }

    private void aboutDev() {
        Alert alertAboutDev = new Alert(Alert.AlertType.INFORMATION);
        alertAboutDev.setTitle("About Developer");
        alertAboutDev.setHeaderText("Vaibhav Golam");

        //Wrap text
        Label label = new Label("I love video games, So in my learning phase I created this game using Java.");
        label.setWrapText(true);

        alertAboutDev.getDialogPane().setContent(label);
        alertAboutDev.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public connect4dController getController() {
        return controller;
    }

    public void setController(connect4dController controller) {
        this.controller = controller;
    }
}