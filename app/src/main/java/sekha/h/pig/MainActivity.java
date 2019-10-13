package sekha.h.pig;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText player1NameEditText;
    private EditText player2NameEditText;
    private TextView player1ScoreTextView;
    private TextView player2ScoreTextView;
    private TextView nextTurnTextView;
    private TextView turnPointsTextView;
    private ImageView dieImageView;
    private Button rollDieButton;
    private Button turnButton;

    private PigGame pigGame = new PigGame();
    private int die = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1NameEditText = findViewById(R.id.player1NameEditText);
        player2NameEditText = findViewById(R.id.player2NameEditText);
        player1ScoreTextView = findViewById(R.id.player1ScoreTextView);
        player2ScoreTextView = findViewById(R.id.player2ScoreTextView);
        nextTurnTextView = findViewById(R.id.nextTurnTextView);
        turnPointsTextView = findViewById(R.id.turnPointsTextView);
        dieImageView = findViewById(R.id.dieImageView);
        rollDieButton = findViewById(R.id.rollDieButton);
        turnButton = findViewById(R.id.turnButton);

        updateScreen();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current game state
        savedInstanceState.putInt("player1Score", pigGame.getPlayer1Score());
        savedInstanceState.putString("player1Name", pigGame.getPlayer1Name());
        savedInstanceState.putInt("player2Score", pigGame.getPlayer2Score());
        savedInstanceState.putString("player2Name", pigGame.getPlayer2Name());
        savedInstanceState.putInt("turn", pigGame.getTurn());
        savedInstanceState.putInt("turnPoints", pigGame.getTurnPoints());
        savedInstanceState.putInt("die", die);
        savedInstanceState.putBoolean("rollDieButtonEnabled", rollDieButton.isEnabled());
        savedInstanceState.putBoolean("turnButtonEnabled", turnButton.isEnabled());
        savedInstanceState.putString("turnButtonText", turnButton.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the game state
        super.onRestoreInstanceState(savedInstanceState);
        pigGame.setPlayer1Name(savedInstanceState.getString("player1Name"));
        pigGame.setPlayer1Score(savedInstanceState.getInt("player1Score"));
        pigGame.setPlayer2Name(savedInstanceState.getString("player2Name"));
        pigGame.setPlayer2Score(savedInstanceState.getInt("player2Score"));
        pigGame.setTurn(savedInstanceState.getInt("turnPoints"));
        pigGame.setTurnPoints(savedInstanceState.getInt("turnPoints"));
        die = savedInstanceState.getInt("die");
        rollDieButton.setEnabled(savedInstanceState.getBoolean("rollDieButtonEnabled"));
        turnButton.setEnabled(savedInstanceState.getBoolean("turnButtonEnabled"));
        turnButton.setText(savedInstanceState.getString("turnButtonText"));
        updateScreen();
    }

    public void rollDieClick(View view) {
        die = pigGame.rollDie();
        if (die == 1) {
            rollDieButton.setEnabled(false);
            turnButton.setText("End Turn");
            pigGame.changeTurn();
        }
        updateScreen();
    }

    public void turnClick(View view) {
        if (turnButton.getText().toString().equals("Start Turn")) {
            turnButton.setText("End Turn");
            rollDieButton.setEnabled(true);
            updateScreen();
        } else {
            turnButton.setText("Start Turn");
            rollDieButton.setEnabled(false);
            pigGame.changeTurn();
            die = 0;
            updateScreen();
        }
    }

    public void newGameClick(View view) {
        pigGame.resetGame();
        die = 0;
        rollDieButton.setEnabled(false);
        turnButton.setText("Start Turn");
        turnButton.setEnabled(true);
        updateScreen();
    }

    private void updateScreen() {
        pigGame.setPlayer1Name(player1NameEditText.getText().toString());
        pigGame.setPlayer2Name(player2NameEditText.getText().toString());
        nextTurnTextView.setText(pigGame.getCurrentPlayer() + "'s Turn");
        player1ScoreTextView.setText(Integer.toString(pigGame.getPlayer1Score()));
        player2ScoreTextView.setText(Integer.toString(pigGame.getPlayer2Score()));
        turnPointsTextView.setText(Integer.toString(pigGame.getTurnPoints()));

        // Check for winner
        if (!pigGame.checkForWinner().equals("")) {
            nextTurnTextView.setText(pigGame.checkForWinner());
            rollDieButton.setEnabled(false);
            turnButton.setEnabled(false);
        }

        // Update the die image
        switch (die) {
            case 0:
                dieImageView.setImageResource(R.drawable.pig);
                break;
            case 1:
                dieImageView.setImageResource(R.drawable.die1);
                break;
            case 2:
                dieImageView.setImageResource(R.drawable.die2);
                break;
            case 3:
                dieImageView.setImageResource(R.drawable.die3);
                break;
            case 4:
                dieImageView.setImageResource(R.drawable.die4);
                break;
            case 5:
                dieImageView.setImageResource(R.drawable.die5);
                break;
            case 6:
                dieImageView.setImageResource(R.drawable.die6);
                break;
        }
    }
}
