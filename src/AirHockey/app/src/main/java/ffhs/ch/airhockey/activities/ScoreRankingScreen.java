package ffhs.ch.airhockey.activities;

import android.app.ListActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;



import ffhs.ch.airhockey.R;
import ffhs.ch.airhockey.database.Score;
import ffhs.ch.airhockey.database.ScoreDataSource;
import ffhs.ch.airhockey.util.DatabaseCustomAdapter;


/**
 * Created by Sandro on 04.03.2017.
 *
 * Activity for the Score Screen
 */

public class ScoreRankingScreen extends ListActivity {

    private ScoreDataSource datasource;


    // Creates the Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the View to the predefined layout
        setContentView(R.layout.database_listview);
        // creates an instance of the ScoreDataSource-Helper and opens the database
        datasource = new ScoreDataSource(this);
        datasource.open();
        // Creates an ArrayList out of the data in the database
        ArrayList<Score> values = (ArrayList<Score>) datasource.getAllScores();
        // Adapter refers the values to the correct ListView
        DatabaseCustomAdapter adapter = new DatabaseCustomAdapter(this, values);
        setListAdapter(adapter);
    }



    // Method to fill the database with testvalues
    public void datenbankTesten(View view) {
        DatabaseCustomAdapter adapter =  (DatabaseCustomAdapter) getListAdapter();
        Score score;

        String[] names = new String[] {"Sandro", "Felix", "Markus"};
        String[] scores = new String[] {"1234", "1333", "4132"};
        score = datasource.createScore(names[0],scores[0]);
        adapter.add(score);
        score = datasource.createScore(names[1],scores[1]);
        adapter.add(score);
        score = datasource.createScore(names[2],scores[2]);
        adapter.add(score);

        adapter.notifyDataSetChanged();
    }
    // Method to empty the database from the testvalues
    public void datenbankLeeren(View view) {
        ArrayAdapter<Score> adapter = (ArrayAdapter<Score>) getListAdapter();
        Score score;

        if (getListAdapter().getCount() > 0) {
            score = (Score) getListAdapter().getItem(0);
            datasource.deleteScore(score);
            adapter.remove(score);
        }

        adapter.notifyDataSetChanged();
    }

    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    protected void onPause() {
        datasource.close();
        super.onPause();
    }




}