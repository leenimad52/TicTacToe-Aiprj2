package com.example.tictactoeaiprj2;

public class MinimaxAlgorithm {

    public static int[] findBestMove(char[][] board) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';  // Assume AI's move
                    int score = minimax(board, 0, false);
                    board[i][j] = ' ';  // Undo the move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }
//////////////////////////////////////////////////////////////////////////////////////
    private static int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);
        if (score == 10 || score == -10) {
            return score;
        }

        if (!isMovesLeft(board)) {
            return 0;  // It's a draw
        }
        if (isMaximizing) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        best = Math.max(best, minimax(board, depth + 1, !isMaximizing));
                        board[i][j] = ' ';  // Undo the move
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        best = Math.min(best, minimax(board, depth + 1, !isMaximizing));
                        board[i][j] = ' ';  // Undo the move
                    }
                }
            }
            return best;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////
    private static int evaluate(char[][] board) {
        // Check rows, columns, and diagonals for a winner
        // Return +10 if AI wins, -10 if player wins, 0 otherwise
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == 'O') {
                    return 10;
                } else if (board[i][0] == 'X') {
                    return -10;
                }
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == 'O') {
                    return 10;
                } else if (board[0][i] == 'X') {
                    return -10;
                }
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'O') {
                return 10;
            } else if (board[0][0] == 'X') {
                return -10;
            }
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'O') {
                return 10;
            } else if (board[0][2] == 'X') {
                return -10;
            }
        }

        return 0;  // No winner
    }
/////////////////////////////////////////////////////////////////////////
    private static boolean isMovesLeft(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return true;
                }
            }
        }
        return false;
    }
}
