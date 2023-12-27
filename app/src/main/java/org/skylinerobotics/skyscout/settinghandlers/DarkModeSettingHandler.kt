package org.skylinerobotics.skyscout.settinghandlers

import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import org.skylinerobotics.skyscout.SettingsDatabase

class DarkModeSettingHandler(
    private val settingsDatabase: SettingsDatabase,
    private val darkModeSwitch: SwitchMaterial
) : SettingHandler {
    override fun applySetting() {
        settingsDatabase.changeSetting("dark-mode", darkModeSwitch.isChecked.toString())

        if (darkModeSwitch.isChecked) {
            setModeDark()
        } else {
            setModeLight()
        }
    }

    override fun updateUI() {
        if (settingsDatabase.loadSetting("dark-mode") == "true") {
            darkModeSwitch.isChecked = true
        }
    }

    private fun setModeDark() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun setModeLight() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}