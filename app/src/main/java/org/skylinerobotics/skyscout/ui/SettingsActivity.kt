package org.skylinerobotics.skyscout.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.settings.SettingsDatabase
import org.skylinerobotics.skyscout.settings.CompetitionSettingHandler
import org.skylinerobotics.skyscout.settings.ScoutingPositionSettingHandler
import org.skylinerobotics.skyscout.settings.SettingHandler

class SettingsActivity : AppCompatActivity() {
    private lateinit var applyButton: Button
    private lateinit var suggestionsButton: Button
    private lateinit var positionEntry: EditText
    private lateinit var competitionEntry: EditText

    private lateinit var settingsDatabase: SettingsDatabase
    private var settingHandlers: MutableList<SettingHandler> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsDatabase = SettingsDatabase(this)

        initLayoutElements()
        initSettingHandlers()
        updateUI()
        setClickListeners()
    }

    private fun initLayoutElements() {
        applyButton = findViewById(R.id.apply_button)
        positionEntry = findViewById(R.id.position_entry)
        competitionEntry = findViewById(R.id.competition_entry)
        suggestionsButton = findViewById(R.id.suggestion_button)
    }

    private fun initSettingHandlers() {
        settingHandlers.add(ScoutingPositionSettingHandler(settingsDatabase, positionEntry))
        settingHandlers.add(CompetitionSettingHandler(settingsDatabase, competitionEntry))
    }

    private fun updateUI() {
        settingHandlers.forEach {
            it.updateUI()
        }
    }

    private fun setClickListeners() {
        applyButton.setOnClickListener { applyButtonAction() }
        suggestionsButton.setOnClickListener { suggestionsButtonAction() }
    }

    private fun suggestionsButtonAction() {
        startActivity(Intent(this, SuggestionsActivity::class.java))
    }

    private fun applyButtonAction() {
        updateSettingsDatabase()
        settingsDatabase.commit()

        Toast.makeText(this, "Settings Applied", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun updateSettingsDatabase() {
        settingHandlers.forEach {
            it.applySetting()
        }
    }
}