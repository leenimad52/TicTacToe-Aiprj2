package com.example.tictactoeaiprj2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;


public class TicTacToeApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label computerLabel = new Label();
        Label userLabel = new Label();
        Label numberOfGamesLabel = new Label("Number of Game: 0/5");
        Label result=new Label();
        TicTacToe ticTacToe = new TicTacToe(computerLabel, userLabel,numberOfGamesLabel,result);
        ticTacToe.startGame();

       // AtomicInteger numOfGame= new AtomicInteger();
        Image image = new Image("D:\\TicTacToe-Aiprj2\\src\\main\\resources\\tictactoeCover.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(710);
        imageView.setFitHeight(300);

        LinearGradient linearGradient = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#076585")),
                new Stop(1, Color.web("#fff"))
        );

        Button retryButton = new Button();
        Image retryImage = new Image("D:\\TicTacToe-Aiprj2\\src\\main\\resources\\retry.png");
        ImageView retryImageView = new ImageView(retryImage);
        retryImageView.setFitWidth(40);
        retryImageView.setFitHeight(40);
        retryImageView.setPreserveRatio(false);

        retryButton.setGraphic(retryImageView);
        retryButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18; -fx-text-fill: #283e51; -fx-background-color: transparent;");

     retryButton.setOnAction(e->{
         ticTacToe.resetGame();
     });
        numberOfGamesLabel.setStyle("-fx-font-family: 'Effect'; -fx-font-size: 20; -fx-text-fill: #283e51; -fx-font-weight: bold;");


        Image computerImage = new Image("D:\\TicTacToe-Aiprj2\\src\\main\\resources\\robot.png");
        Image userImage = new Image("D:\\TicTacToe-Aiprj2\\src\\main\\resources\\man.png");

        ImageView computerImageView = new ImageView(computerImage);
        ImageView userImageView = new ImageView(userImage);

        computerImageView.setFitWidth(45);
        computerImageView.setFitHeight(45);

        userImageView.setFitWidth(45);
        userImageView.setFitHeight(45);

        computerLabel.setGraphic(computerImageView);
        userLabel.setGraphic(userImageView);
       computerLabel.setText("Score: ");
       userLabel.setText("Score: ");
       ticTacToe.setScoreLabels(computerLabel, userLabel,numberOfGamesLabel,result);

//        computerLabel.setText("Score: "+ticTacToe.getoScore());
//        userLabel.setText("Score: "+ticTacToe.getxScore());

        computerLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 25; -fx-text-fill: #283e51;");
        userLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 25; -fx-text-fill: #283e51;");


        VBox resultsBox = new VBox(20);
        resultsBox.setAlignment(Pos.BASELINE_LEFT);
        resultsBox.getChildren().addAll(computerLabel, userLabel,result);

        Label messageLabel = new Label("\u00A0\u00A0\u00A0You're playing with the computer,Start by clicking!");
        messageLabel.setStyle("-fx-font-family: 'Sans Serif';-fx-font-size: 30; -fx-text-fill: #283e51;");


        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox topBox = new VBox(4);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.getChildren().addAll(imageView,messageLabel);
        root.setTop(topBox);


        VBox leftBox = new VBox(20);
        leftBox.setAlignment(Pos.BOTTOM_CENTER);
        leftBox.getChildren().add(ticTacToe.getGameBoard());
      //  root.setLeft(leftBox);


        HBox topCenterBox = new HBox(5);
        topCenterBox.setAlignment(Pos.BOTTOM_CENTER);
        topCenterBox.getChildren().addAll(numberOfGamesLabel,retryButton);

        VBox rightCenterBox = new VBox(25);
        rightCenterBox.setAlignment(Pos.CENTER);
        rightCenterBox.getChildren().addAll(resultsBox);
        VBox rightBox = new VBox(20);
        rightBox.setAlignment(Pos.TOP_LEFT);
        rightBox.getChildren().addAll(topCenterBox, rightCenterBox);

        HBox centerHBox = new HBox(10);
        centerHBox.setAlignment(Pos.CENTER_LEFT);
        centerHBox.getChildren().addAll(leftBox, rightBox);
        root.setCenter(centerHBox);

        Scene scene = new Scene(root, 710, 650);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
