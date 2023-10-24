package com.vaibhavgolam.connectfour;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class connect4dController implements Initializable {
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int CIRCLE_DIAMETER = 70;
    private static final String discColor1 = "#24303E";
    private static final String discColor2 = "#4CAA88";
    private static String PLAYER_ONE = "Player One";
    private static String PLAYER_TWO = "Player Two";
    private boolean isPlayerOneTurn = true;
    private boolean isAllowedToInsert = true;
    private Disk[][] insertedDiskArray = new Disk[ROWS][COLUMNS];  //for structural changes

    @FXML
    public GridPane rootGridPane;
    @FXML
    public Pane insertedDiskPane;
    @FXML
    public Label playerNameLabel;
    @FXML
    public Label whosTurnLabel;
    @FXML
    public TextField playerOneTextField,playerTwoTextField;
    @FXML
    public Button setNamesButton;


    public void createPlayground(){
        Shape rectangleWithHoles = createGameStructuralGrid();
        rootGridPane.add(rectangleWithHoles,0,1);

        List<Rectangle> rectangleList = createClickableColumns();
        for (Rectangle rectangle: rectangleList) {
            rootGridPane.add(rectangle,0,1);
        }

        setNamesButton.setOnAction(event -> {
            //exception if fields are kept empty
            if (playerOneTextField.getText().isEmpty() || playerTwoTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Please enter names for both players.");
                alert.showAndWait();
            } else {
                // If both inputs are not empty, proceed with setting the names
                PLAYER_ONE = playerOneTextField.getText();
                PLAYER_TWO = playerTwoTextField.getText();
            }
        });
    }

    private Shape createGameStructuralGrid(){
        Shape rectangleWithHoles = new Rectangle((COLUMNS + 1) * CIRCLE_DIAMETER,(ROWS+1) * CIRCLE_DIAMETER);
        for (int row = 0; row < ROWS ; row++){
            for (int col = 0; col < COLUMNS; col++){
                Circle circle = new Circle();
                circle.setRadius(CIRCLE_DIAMETER/2);
                circle.setCenterY(CIRCLE_DIAMETER/2);
                circle.setCenterX(CIRCLE_DIAMETER/2);
                circle.setSmooth(true);

                circle.setTranslateX(col * (CIRCLE_DIAMETER +5) + CIRCLE_DIAMETER /4 );
                circle.setTranslateY(row * (CIRCLE_DIAMETER +5) + CIRCLE_DIAMETER /4 );

                rectangleWithHoles = Shape.subtract(rectangleWithHoles,circle);
            }
        }
        rectangleWithHoles.setFill(Color.WHITE);
        return rectangleWithHoles ;
    }

    private List<Rectangle> createClickableColumns(){
        List<Rectangle> rectangleList = new ArrayList<>();
        for(int col = 0; col < COLUMNS; col++ ){
            Rectangle rectangle = new Rectangle(CIRCLE_DIAMETER,(ROWS+1)*CIRCLE_DIAMETER);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setTranslateX(col * (CIRCLE_DIAMETER + 5) + CIRCLE_DIAMETER/4);

            rectangle.setOnMouseEntered(event -> rectangle.setFill(Color.valueOf("#eeeeee10")) );
            rectangle.setOnMouseExited(event -> rectangle.setFill(Color.TRANSPARENT) );

            final int column = col;
            rectangle.setOnMouseClicked(event ->{
                if (isAllowedToInsert){
                    isAllowedToInsert = false;
                    insertDisk(new Disk(isPlayerOneTurn), column);
                }
            } );
            rectangleList.add(rectangle);
        }
        return rectangleList;
    }

    private void insertDisk(Disk disk, int column){
            int row = ROWS - 1;
            while (row >= 0) {
                if (getDiskIfPresent(row, column) == null) {
                    break;
                }
                row--;
            }

            if (row < 0) { //if it is full, we cannot insert anymore disk
                return;
            }

            insertedDiskArray[row][column] = disk;  //for structural changes
            insertedDiskPane.getChildren().add(disk);

            disk.setTranslateX(column * (CIRCLE_DIAMETER + 5) + CIRCLE_DIAMETER / 4);

            int currentRow = row;
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), disk);
            translateTransition.setToY(row * (CIRCLE_DIAMETER + 5) + CIRCLE_DIAMETER / 4);
            translateTransition.setOnFinished(event -> {
                isAllowedToInsert = true; //when disk is  dropped allows next player to insert disk
                if (gameEnded(currentRow, column)) {
                    gameOver();
                }
                isPlayerOneTurn = !isPlayerOneTurn;
                playerNameLabel.setText(isPlayerOneTurn ? PLAYER_ONE : PLAYER_TWO);
            });
            translateTransition.play();
    }

    private boolean gameEnded(int row,int column){
        //Vertical winning
        List<Point2D> verticalPoint = IntStream.rangeClosed(row -3,row +3)
                .mapToObj(r -> new Point2D(r, column))
                .collect(Collectors.toList());

        //horizontal winning
        List<Point2D> horizontalPoint = IntStream.rangeClosed(column -3,column +3)
                .mapToObj(r -> new Point2D(row, r))
                .collect(Collectors.toList());

        //diagonal 1 winning
        Point2D startPoint1 = new Point2D(row -3,column+3);
        List<Point2D> diagonalPoint1 = IntStream.rangeClosed(0,6)
                .mapToObj( i-> startPoint1.add(i,-i))
                .collect(Collectors.toList());

        //diagonal 2 winning
        Point2D startPoint2 = new Point2D(row -3,column -3);
        List<Point2D> diagonalPoint2 = IntStream.rangeClosed(0,6)
                .mapToObj( i-> startPoint2.add(i,i))
                .collect(Collectors.toList());

       boolean isEnded = checkCombinations(verticalPoint) || checkCombinations(horizontalPoint)
                        || checkCombinations(diagonalPoint1) || checkCombinations(diagonalPoint2);
       return  isEnded;
    }

    private boolean checkCombinations(List<Point2D> points) {

        int chain = 0;
        for (Point2D point : points ) {
            int rowIndexForArray = (int) point.getX();
            int columnIndexForArray = (int) point.getY();

            Disk disk = getDiskIfPresent(rowIndexForArray,columnIndexForArray);

            if(disk != null && disk.isPlayerOneMove == isPlayerOneTurn){ //if the last inserted disk belongs to the current player
                chain++;
                if(chain == 4){
                    return true;
                }
            }else{
                chain = 0;
            }
        }
        return false;
    }

    //to prevent array index out of bound
    private Disk getDiskIfPresent(int row, int column){
        if(row >= ROWS|| row < 0 || column >= COLUMNS || column < 0  ) {
            return null;
        }
        return  insertedDiskArray[row][column];
    }

    private  void gameOver(){
        String winner = isPlayerOneTurn? PLAYER_ONE : PLAYER_TWO;
        System.out.println("Winner is "+winner);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect Four");
        alert.setHeaderText("The winner is "+ winner);
        alert.setContentText("Want to play again?");

        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No, Exit");
        alert.getButtonTypes().setAll(yesBtn,noBtn);

        Platform.runLater(() -> {
            Optional<ButtonType> btnClicked = alert.showAndWait();
            if (btnClicked.isPresent() && btnClicked.get() == yesBtn ){
                resetGame();
            }else {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public void resetGame() {
        //will remove all the inserted disk from pane
        insertedDiskPane.getChildren().clear();

        //will structurally make all the elements of insertedDiskArray[][] to null
        for (int row = 0; row< insertedDiskArray.length  ; row++ ){
            for (int col = 0; col< insertedDiskArray[row].length  ; col++ ){
                insertedDiskArray[row][col] = null;
            }
        }
        //player one plays first always
        isPlayerOneTurn = true;
        playerNameLabel.setText(PLAYER_ONE);

        createPlayground();
    }

    private static class Disk extends Circle{
        private final boolean isPlayerOneMove;
        public Disk(boolean isPlayerOneMove){
            this.isPlayerOneMove =isPlayerOneMove;
            setRadius(CIRCLE_DIAMETER/2);
            setFill(isPlayerOneMove? Color.valueOf(discColor1):Color.valueOf(discColor2));
            setCenterX(CIRCLE_DIAMETER/2);
            setCenterY(CIRCLE_DIAMETER/2);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
