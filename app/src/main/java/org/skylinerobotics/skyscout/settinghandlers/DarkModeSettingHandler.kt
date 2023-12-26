package org.skylinerobotics.skyscout.settinghandlers

import com.google.android.material.switchmaterial.SwitchMaterial
import org.skylinerobotics.skyscout.SettingsDatabase

class DarkModeSettingHandler(
    private val settingsDatabase: SettingsDatabase,
    private val darkModeSwitch: SwitchMaterial
) : SettingHandler {
    override fun applySetting() {
        settingsDatabase.changeSetting("dark-mode", darkModeSwitch.isChecked.toString())
    }

    override fun updateUI() {
        if (settingsDatabase.loadSetting("dark-mode") == "true") {
            darkModeSwitch.isChecked = true
        }
    }
}