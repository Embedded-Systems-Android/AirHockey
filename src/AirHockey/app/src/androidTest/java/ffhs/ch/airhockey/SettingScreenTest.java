package ffhs.ch.airhockey;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import ffhs.ch.airhockey.activities.PreferencesScreen;

/**
 * Created by Sandro on 01.06.2017.
 */

public class SettingScreenTest extends ActivityInstrumentationTestCase2<PreferencesScreen> {

    private Solo mSimulator;

    public SettingScreenTest() {
        super(PreferencesScreen.class);
    }

    public void setUp() throws Exception{
        mSimulator = new Solo(getInstrumentation(), getActivity());
    }

    public void testSpracheWechseln () {
        mSimulator.clickOnText("Sprache");
        mSimulator.isTextChecked("DE");
        mSimulator.clickOnText("EN");
        mSimulator.clickOnText("Sprache");
        mSimulator.isTextChecked("EN");
        mSimulator.clickOnText("FR");
        mSimulator.clickOnText("Sprache");
        mSimulator.isTextChecked("FR");
        mSimulator.clickOnText("DE");
        mSimulator.clickOnText("Sprache");
        mSimulator.isTextChecked("DE");
        mSimulator.clickOnText("Abbrechen");
        mSimulator.goBack();
    }
}
