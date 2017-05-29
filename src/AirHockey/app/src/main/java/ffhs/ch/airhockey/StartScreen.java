package ffhs.ch.airhockey;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import ffhs.ch.airhockey.R;
import ffhs.ch.airhockey.database.Score;
import ffhs.ch.airhockey.database.ScoreDataSource;

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
        Intent i = new Intent(this, SettingScreen.class);
        startActivity(i);
    }





}



