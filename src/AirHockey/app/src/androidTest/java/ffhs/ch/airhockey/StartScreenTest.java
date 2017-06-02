package ffhs.ch.airhockey;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

import ffhs.ch.airhockey.activities.AirHockeyActivity;
import ffhs.ch.airhockey.activities.PreferencesScreen;
import ffhs.ch.airhockey.activities.ScoreRankingScreen;
import ffhs.ch.airhockey.activities.StartScreen;

/**
 * Created by Sandro on 01.06.2017.
 */

public class StartScreenTest extends ActivityInstrumentationTestCase2<StartScreen> {

    private Solo mSimulator;


    public StartScreenTest() {
        super(StartScreen.class);
    }

    public void setUp() throws Exception{
        mSimulator = new Solo(getInstrumentation(), getActivity());
    }

    public void testEinstellungenKlick() throws Exception {
        mSimulator.clickOnButton("Einstellungen");
        mSimulator.assertCurrentActivity("Einstellungsseite erwartet", PreferencesScreen.class);
        mSimulator.goBack();
    }

    public void testDatenbankKlick() throws Exception {
        mSimulator.clickOnButton("Score Ranking anzeigen");
        mSimulator.assertCurrentActivity("ScoreRanking erwartet", ScoreRankingScreen.class);
        mSimulator.goBack();
    }

    public void testSpielKlick() throws Exception {
        mSimulator.clickOnButton("Neues Spiel");
        mSimulator.assertCurrentActivity("Neues Spiel erwartet", AirHockeyActivity.class);
        mSimulator.goBack();
    }


}
