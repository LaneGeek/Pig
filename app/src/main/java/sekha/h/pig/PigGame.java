package sekha.h.pig;

import java.util.Random;

class PigGame {

    private final int WINNING_SCORE = 20;

    private Random rand = new Random();
    private int player1Score;
    private int player2Score;
    private String player1Name = "";
    private String player2Name = "";
    private int turn;
    private int turnPoints;

    PigGame() {
        player1Score = 0;
        player2Score = 0;
        turnPoints = 0;
        turn = 1; // player 1 goes first
    }

    String getPlayer1Name() {
        return player1Name;
    }

    void setPlayer1Name(String n) {
        player1Name = n;
    }

    String getPlayer2Name() {
        return player2Name;
    }

    void setPlayer2Name(String n) {
        player2Name = n;
    }

    int getPlayer1Score() {
        return player1Score;
    }

    void setPlayer1Score(int score) {
        player1Score = score;
    }

    int getPlayer2Score() {
        return player2Score;
    }

    void setPlayer2Score(int score) {
        player2Score = score;
    }

    int getTurn() {
        return turn;
    }

    void setTurn(int t) {
        turn = t;
    }

    int getTurnPoints() {
        return turnPoints;
    }

    void setTurnPoints(int p) {
        turnPoints = p;
    }

    void resetGame() {
        player1Score = 0;
        player2Score = 0;
        turnPoints = 0;
        turn = 1;
    }

    int rollDie() {
        int roll = rand.nextInt(6) + 1;
        if (roll != 1) {
            turnPoints += roll;
        } else {
            turnPoints = 0;
            changeTurn();
        }
        return roll;
    }

    String getCurrentPlayer() {
        return turn % 2 == 1 ? player1Name : player2Name;
    }

    void changeTurn() {
        if (turn % 2 == 1)
            player1Score += turnPoints;
        else
            player2Score += turnPoints;
        turnPoints = 0;
        turn++;
    }

    String checkForWinner() {
        String winnerMessage = "";
        if (player1Score >= WINNING_SCORE || player2Score >= WINNING_SCORE) {
            if (player2Score > player1Score) {
                winnerMessage = String.format("%s wins!", player2Name);
            } else if (player1Score > player2Score && turn % 2 == 1) {
                winnerMessage = String.format("%s wins!", player1Name);
            } else if (player1Score == player2Score) {
                winnerMessage = "Tie";
            }
        }
        return winnerMessage;
    }
}
