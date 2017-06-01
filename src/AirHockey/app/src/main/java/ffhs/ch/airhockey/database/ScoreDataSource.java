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
 *
 *
 */

public class ScoreDataSource {

    private SQLiteDatabase database;
    private AirHockeyDatenbank dbHelper;
    public String[] allColumns = {dbHelper.COLUMN_ID, dbHelper.COLUMN_NAME, dbHelper.COLUMN_SCORE};
    public Cursor cursor;

    // new instance of database
    public ScoreDataSource(Context context) {
        dbHelper = new AirHockeyDatenbank(context);
    }
    // opens the database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    // closes the database
    public void close() {
        dbHelper.close();
    }

    // puts the Score into the database
    public Score createScore(String name, String score) {
        ContentValues cv = new ContentValues();
        // values and corresponding columns are put into ContentValues
        cv.put(dbHelper.COLUMN_NAME, name);
        cv.put(dbHelper.COLUMN_SCORE, score);
        // values are connected to id
        long insertId = database.insert(AirHockeyDatenbank.TABLE_SCORE, null, cv);

        // values are inserted into database via SQLite query
        cursor = database.query(AirHockeyDatenbank.TABLE_SCORE, allColumns, dbHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Score newScore = cursorToScore(cursor);
        // always close the cursor
        cursor.close();
        // score is returned
        return newScore;

    }
    // delete the Score
    public void deleteScore(Score score) {
        // scoreId is searched and saved into variable
        long id = score.getId();
        System.out.println("Score deleted with id: " + id);
        // score is deleted
        database.delete(dbHelper.TABLE_SCORE, dbHelper.COLUMN_ID + " = " + id, null);
    }

    // returnes all Scores currently saved in the database
    public List<Score> getAllScores() {
        // new ArrayList is created
        List<Score> scores = new ArrayList<Score>();

        // Cursor points to the Columns
        cursor = database.query(dbHelper.TABLE_SCORE, allColumns, null, null, null, null, null);

        // Loop to sequentially get all the scores
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Score score = cursorToScore(cursor);
            scores.add(score);
            cursor.moveToNext();
        }
        // Cursor immer schliessen
        cursor.close();
        // return the scores
        return scores;
    }

    // Method to link Cursor to specific Score
    private Score cursorToScore(Cursor cursor) {
        Score score = new Score();
        score.setId(cursor.getLong(0));
        score.setName(cursor.getString(1));
        score.setScore(cursor.getString(2));
        return score;
    }



}
