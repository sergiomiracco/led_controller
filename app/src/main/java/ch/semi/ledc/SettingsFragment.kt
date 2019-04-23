package ch.semi.ledc

import android.os.Bundle
import android.view.Menu
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.opt_menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

}