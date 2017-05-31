package ffhs.ch.airhockey.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ffhs.ch.airhockey.R;

/**
 * Created by Sandro on 04.03.2017.
 */

public class StartScreen extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startscreen);


    }


    public void onClickNewGame(final View sfNormal) {
        Intent i = new Intent(this, AirHockeyActivity.class);
        startActivity(i);
    }

    public void onClickScoreRanking(final View sfNormal) {
        Intent i = new Intent(this, ScoreRankingScreen.class);
        startActivity(i);
    }

    public void onClickSettingsButton(final View sfNormal) {
        Intent i = new Intent(this, PreferencesScreen.class);
        startActivity(i);
    }





}



