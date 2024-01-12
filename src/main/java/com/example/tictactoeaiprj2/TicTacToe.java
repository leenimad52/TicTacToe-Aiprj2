package com.example.tictactoeaiprj2;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.Optional;

public class TicTacToe {
    private GridPane gameBoard;
    private char currentPlayer;
    private Button[][] buttons;
     int xScore=0;
     int oScore=0;
    private int numOfGame=0;
    private String winner=null;

    private Label computerLabel;
    private Label userLabel;
    private Label numberOfGamesLabel;
    private Label result;
    public void setScoreLabels(Label computerLabel, Label userLabel,Label numberOfGamesLabel,Label result) {
        this.computerLabel = computerLabel;
        this.userLabel = userLabel;
        this.numberOfGamesLabel=numberOfGamesLabel;
        this.result=result;
    }

    //////////////////////////////////////////////////////////////////////////////////
    public TicTacToe() {
        gameBoard = new GridPane();
        currentPlayer = 'X';
        buttons = new Button[3][3];
    }
    public TicTacToe(Label computerLabel, Label userLabel,Label numberOfGameLabel,Label result) {
        this.computerLabel = computerLabel;
        this.userLabel = userLabel;
        this.numberOfGamesLabel=numberOfGamesLabel;
        this.result=result;
        gameBoard = new GridPane();
        currentPlayer = 'X';
        buttons = new Button[3][3];
    }
    public void startGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = createButton();
                gameBoard.add(buttons[row][col], col, row);
            }
        }
    }
//////////////////////////////////////////////////////////
private Button createButton() {
    Button button = new Button();

    button.setMinSize(95, 95);
    button.setOnAction(e -> handleButtonClick(button));
    button.setStyle("-fx-font-size: 24; -fx-font-family: Arial; -fx-text-fill: #fff; " +
            "-fx-background-color: linear-gradient(to bottom, #4b79a1, #283e51);" +
            "-fx-border-color: #4b79a1; -fx-border-width: 0 2px 2px 0;");

    return button;
}

///////////////////////////////////////////////////////////////
private void handleButtonClick(Button button) {
    if (checkForWinner() == null && button.getText().isEmpty()) {
        button.setText(String.valueOf(currentPlayer));

        int[] winningLine = checkForWinner();

        if (winningLine != null) {
            highlightWinningLine(winningLine);
            scoreXvsO();
        } else if (isBoardFull()) {
            showDrawDialog();
        } else {
            switchPlayer();
            PauseTransition pause = new PauseTransition(Duration.seconds(0.4));
            pause.setOnFinished(event -> makeAIMove());
            pause.play();
        }
    }
}

///////////////////////////////////////////////////////////////////////////
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
 /////////////////////////////////////////////////////////////////////////
 private void highlightWinningLine(int[] coordinates) {
     if (coordinates != null) {
         for (int i = 0; i < coordinates.length; i += 2) {
             int row = coordinates[i];
             int col = coordinates[i + 1];
             Button winningButton = buttons[row][col];
             winningButton.setStyle("-fx-border-color: red; -fx-border-width: 3; -fx-background-color: linear-gradient(to bottom, #4b79a1, #283e51);");
             winningButton.setStyle(winningButton.getStyle() + "-fx-font-weight: bold;");
         }
     }
 }
///////////////////////////////////////////////////////////////////////
private void makeAIMove() {
    int[] winningLine = checkForWinner();
    if (winningLine != null) {
        highlightWinningLine(winningLine);
        scoreXvsO();
    } else {
        int[] bestMove = MinimaxAlgorithm.findBestMove(getCurrentBoardState());
        if (bestMove != null && bestMove.length == 2) {
            int row = bestMove[0];
            int col = bestMove[1];
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && buttons[row][col].getText().isEmpty()) {
                buttons[row][col].setText("O");
                if (checkForWinner() != null) {
                    highlightWinningLine(checkForWinner());
                    scoreXvsO();
                } else if (isBoardFull()) {
                    showDrawDialog();
                } else {
                    switchPlayer();
                }
            }
        }
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////
private void scoreXvsO() {
    numOfGame++;
    int restOfRounds = 5 - numOfGame;

    if (restOfRounds > 0) {
        if (currentPlayer == 'X') {
            result.setText("You won this round, there are still " + restOfRounds + " round/s ");
            result.setStyle("-fx-font-family: 'Effect'; -fx-font-size: 15; -fx-text-fill: #009688; -fx-font-weight: bold;");
            xScore++;
        } else {
            result.setText("You lost this round, there are still " + restOfRounds + " round/s ");
            result.setStyle("-fx-font-family: 'Effect'; -fx-font-size: 15; -fx-text-fill: #F44336; -fx-font-weight: bold;");
            oScore++;
        }
    } else { // restOfRounds is 0
        if (oScore >= 3) {
            result.setText("Computer is the winner ");
            result.setStyle("-fx-font-family: 'Effect'; -fx-font-size: 15; -fx-text-fill: #009688; -fx-font-weight: bold;");
        } else if (xScore >= 3) {
            result.setText("You are the winner");
            result.setStyle("-fx-font-family: 'Effect'; -fx-font-size: 15; -fx-text-fill: #009688; -fx-font-weight: bold;");
        } else {
            result.setText("No one won because no one of you got 3 scores or more.");
            result.setStyle("-fx-font-family: 'Effect'; -fx-font-size: 15; -fx-text-fill: #F44336; -fx-font-weight: bold;");
        }
    }
    updateScoreLabels();
}

    /////////////////////////////////////////////////////////////////
    private void showDrawDialog() {
        numOfGame++;
        int restOfRounds=5-numOfGame;
        result.setText("draw, there are still " +restOfRounds +" round/s ");
        result.setStyle("-fx-font-family: 'Effect'; -fx-font-size: 15; -fx-text-fill: #FF9800; -fx-font-weight: bold;");
        System.out.println("DRAW");
        updateScoreLabels();
    }
/////////////////////////////////////////////////////////////////////////////////////////
    private void updateScoreLabels() {
        computerLabel.setText("Score: " + oScore);
        userLabel.setText("Score: " + xScore);
        numberOfGamesLabel.setText("Number of Game: "+numOfGame+"/5");
    }

//////////////////////////////////////////////////////////////
    private char[][] getCurrentBoardState() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String text = buttons[i][j].getText();
                board[i][j] = text.isEmpty() ? ' ' : text.charAt(0);
            }
        }
        return board;
    }
//////////////////////////////////////////////////////////////
    private int[] checkForWinner() {
        for (int i = 0; i < 3; i++) { // Check rows
            if (buttons[i][0].getText().equals(buttons[i][1].getText())
                    && buttons[i][1].getText().equals(buttons[i][2].getText())
                    && !buttons[i][0].getText().isEmpty()) {
                return new int[]{i, 0, i, 1, i, 2}; // Row coordinates
            }
        }
        for (int j = 0; j < 3; j++) {// Check columns
            if (buttons[0][j].getText().equals(buttons[1][j].getText())
                    && buttons[1][j].getText().equals(buttons[2][j].getText())
                    && !buttons[0][j].getText().isEmpty()) {
                return new int[]{0, j, 1, j, 2, j}; // Column coordinates
            }
        }
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) // Check diagonals
                && buttons[1][1].getText().equals(buttons[2][2].getText())
                && !buttons[0][0].getText().isEmpty()) {
            return new int[]{0, 0, 1, 1, 2, 2}; // Diagonal coordinates (top-left to bottom-right)
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][0].getText())
                && !buttons[0][2].getText().isEmpty()) {
            return new int[]{0, 2, 1, 1, 2, 0}; // Diagonal coordinates (top-right to bottom-left)
        }
        return null; // No winner
    }
/////////////////////////////////////////////////////////////////////////////////////////
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
//////////////////////////////////////////////////////////////////////////////////////////
 void resetGame() {
     result.setText("");
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            buttons[i][j].setText("");
            buttons[i][j].setStyle("-fx-font-size: 24; -fx-font-family: Arial; -fx-text-fill: #fff; " +
                    "-fx-background-color: linear-gradient(to bottom, #4b79a1, #283e51);" +
                    "-fx-border-color: #4b79a1; -fx-border-width: 0 2px 2px 0;");
        }
    }
    currentPlayer = 'X';
    if (numOfGame==5){
        numOfGame=0;
        xScore=0;
        oScore=0;
        updateScoreLabels();
    }
 }
////////////////////////////////////////////////////////////////////////////////////
    public Pane getGameBoard() {
        return gameBoard;
    }
}
