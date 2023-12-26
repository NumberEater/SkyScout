package org.skylinerobotics.skyscout.settinghandlers

import android.widget.EditText
import org.skylinerobotics.skyscout.SettingsDatabase

class ScoutingPositionSettingHandler(
    private val settingsDatabase: SettingsDatabase,
    private val positionEntry: EditText
) : SettingHandler {
    override fun applySetting() {
        settingsDatabase.changeSetting("scouting-position", positionEntry.text.toString())
    }

    override fun updateUI() {
        val position = settingsDatabase.loadSetting("scouting-position")
        if (position != null) {
            positionEntry.setText(position)
        }
    }
}