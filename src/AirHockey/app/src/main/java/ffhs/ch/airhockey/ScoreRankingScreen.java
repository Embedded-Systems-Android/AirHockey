package ffhs.ch.airhockey;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ffhs.ch.airhockey.database.Score;
import ffhs.ch.airhockey.database.ScoreDataSource;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by Sandro on 04.03.2017.
 */

public class ScoreRankingScreen extends ListActivity {

    private ScoreDataSource datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scoreranking);

        datasource = new ScoreDataSource(this);
        datasource.open();

        List<Score> values = datasource.getAllScores();

        // SimpleCursorAdapter um Elemente der Datenbank in der Liste anzuzeigen
        ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_2, android.R.id.text1, values);
        setListAdapter(adapter);
    }



     // 2 Test Methoden um Datenbank zu füllen / leeren
    public void datenbankTesten(View view) {
        ArrayAdapter<Score> adapter = (ArrayAdapter<Score>) getListAdapter();
        Score score = null;

        String[] names = new String[] {"Sandro"};
        String[] scores = new String[] {"1234"};
        score = datasource.createScore(names[0],scores[0]);
        adapter.add(score);

        adapter.notifyDataSetChanged();
    }

    public void datenbankLeeren(View view) {
        ArrayAdapter<Score> adapter = (ArrayAdapter<Score>) getListAdapter();
        Score score = null;

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