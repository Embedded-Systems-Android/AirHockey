package ffhs.ch.airhockey.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ffhs.ch.airhockey.R;

/**
 * Created by Sandro on 04.03.2017.
 *
 * MainActivity of the Application. Will be shown first when the app opens
 */

public class StartScreen extends AppCompatActivity {

    // Creates the Screen and sets the View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startscreen);


    }

    // Method to start a new Game, starts an intent for the AirHockeyActivity Class
    public void onClickNewGame(final View sfNormal) {
        Intent i = new Intent(this, AirHockeyActivity.class);
        startActivity(i);
    }
    // Method to show the Score Screen, starts an intent for the ScoreRankingScreen Class
    public void onClickScoreRanking(final View sfNormal) {
        Intent i = new Intent(this, ScoreRankingScreen.class);
        startActivity(i);
    }
    // Method to show the Settings Screen, starts an intent for the PreferencesScreen Class
    public void onClickSettingsButton(final View sfNormal) {
        Intent i = new Intent(this, PreferencesScreen.class);
        startActivity(i);
    }





}



