package ffhs.ch.airhockey;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;


import ffhs.ch.airhockey.activities.ScoreRankingScreen;

/**
 * Created by Sandro on 01.06.2017.
 */

public class ScoreRankingTest extends ActivityInstrumentationTestCase2<ScoreRankingScreen> {

    private Solo mSimulator;

    public ScoreRankingTest() {
        super(ScoreRankingScreen.class);
    }

    public void setUp() throws Exception{
        mSimulator = new Solo(getInstrumentation(), getActivity());
    }

    public void testDatenbankFuellen() {
        mSimulator.clickOnButton("DB füllen");
        mSimulator.searchText("Sandro");
        mSimulator.clickInList(0);
        mSimulator.clickInList(1);
        mSimulator.clickInList(2);
        mSimulator.scrollToBottom();
        mSimulator.scrollToTop();
    }

    public void testDatenbankLeeren() {
        mSimulator.clickInList(0);
        mSimulator.clickOnButton("DB leeren");
    }

    public void zurueck() {
        mSimulator.goBack();
    }


}
