package ffhs.ch.airhockey.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;


import ffhs.ch.airhockey.R;

/**
 * Created by Sandro on 30.05.2017.
 *
 *  Activity for the Settings
 */

public class PreferencesScreen extends PreferenceActivity {

    // Creates a PreferenceActivity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }
    // internal Class because Methods were deprecated
    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            // Gets the Preferences from a predefined XML-File "preferences"
            addPreferencesFromResource(R.xml.preferences);
        }
    }












}
