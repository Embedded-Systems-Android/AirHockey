package ffhs.ch.airhockey.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sandro on 28.05.2017.
 *
 * The Database for all the Scores
 */

public class AirHockeyDatenbank extends SQLiteOpenHelper {


    private static final String DATENBANK_NAME = "airhockey.db";
    private static final int DATENBANK_VERSION = 1;

    public static final String TABLE_SCORE = "rangliste";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";

    // String to create the Table "rangliste"
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_SCORE + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_SCORE + " TEXT NOT NULL)";

    public AirHockeyDatenbank(Context context) {
        super(context, DATENBANK_NAME, null, DATENBANK_VERSION);
    }

    // creates the table
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    // defines what happens if the variable "DATENBANK_VERSION" is changed
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteOpenHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + " , which will delete old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    // Needs to be implemented if downgrades are necessary
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO falls Downgrade notwendig
    }
}
