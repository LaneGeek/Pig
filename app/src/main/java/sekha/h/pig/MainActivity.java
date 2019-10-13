package sekha.h.pig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

    private SharedPreferences savedValues;
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

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        updateScreen();
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("player1Name", pigGame.getPlayer1Name());
        editor.putInt("player1Score", pigGame.getPlayer1Score());
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pigGame.setPlayer1Name(savedValues.getString("player1Name", ""));
        pigGame.setPlayer1Score(savedValues.getInt("player1Score", 0));
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
