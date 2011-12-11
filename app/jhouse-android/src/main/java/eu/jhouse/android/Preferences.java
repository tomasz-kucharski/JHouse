package eu.jhouse.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import roboguice.activity.RoboPreferenceActivity;

/**
 * @author tomekk
 * @since 13.02.11, 23:27
 */
public class Preferences extends RoboPreferenceActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addPreferencesFromResource(R.xml.preferences);
    }
}
