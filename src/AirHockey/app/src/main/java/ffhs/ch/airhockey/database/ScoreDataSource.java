package ffhs.ch.airhockey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandro on 29.05.2017.
 */

public class ScoreDataSource {

    private SQLiteDatabase database;
    private AirHockeyDatenbank dbHelper;
    public String[] allColumns = {dbHelper.COLUMN_ID, dbHelper.COLUMN_NAME, dbHelper.COLUMN_SCORE};
    public Cursor cursor;


    public ScoreDataSource(Context context) {
        dbHelper = new AirHockeyDatenbank(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Score createScore(String name, String score) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_NAME, name);
        cv.put(dbHelper.COLUMN_SCORE, score);
        long insertId = database.insert(AirHockeyDatenbank.TABLE_SCORE, null, cv);

        cursor = database.query(AirHockeyDatenbank.TABLE_SCORE, allColumns, dbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Score newScore = cursorToScore(cursor);
        cursor.close();
        return newScore;

    }

    public void deleteScore(Score score) {
        long id = score.getId();
        System.out.println("Score deleted with id: " + id);
        database.delete(dbHelper.TABLE_SCORE, dbHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<Score>();

        cursor = database.query(dbHelper.TABLE_SCORE, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Score score = cursorToScore(cursor);
            scores.add(score);
            cursor.moveToNext();
        }
        // Cursor immer schliessen
        cursor.close();
        return scores;
    }

    private Score cursorToScore(Cursor cursor) {
        Score score = new Score();
        score.setId(cursor.getLong(0));
        score.setName(cursor.getString(1));
        score.setScore(cursor.getString(2));
        return score;
    }



}
