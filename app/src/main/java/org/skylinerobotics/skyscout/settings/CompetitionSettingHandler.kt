package org.skylinerobotics.skyscout.settings

import android.widget.EditText
import org.skylinerobotics.skyscout.data.SettingsDatabase

class CompetitionSettingHandler(
    private val settingsDatabase: SettingsDatabase,
    private val competitionEntry: EditText
) : SettingHandler {
    override fun applySetting() {
        settingsDatabase.changeSetting("competition", competitionEntry.text.toString())
    }

    override fun updateUI() {
        val competitionString = settingsDatabase.loadSetting("competition")
        if (competitionString != null) {
            competitionEntry.setText(competitionString)
        }
    }
}