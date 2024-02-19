package org.skylinerobotics.skyscout.settings

import android.widget.EditText

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