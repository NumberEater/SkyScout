package org.skylinerobotics.skyscout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.skylinerobotics.skyscout.settinghandlers.ScoutingPositionSettingHandler
import org.skylinerobotics.skyscout.settinghandlers.SettingHandler

class SettingsActivity : AppCompatActivity() {
    companion object {
        private const val SETTINGS_PIN = 1423
    }

    private lateinit var applyButton: Button
    private lateinit var positionEntry: EditText

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
    }

    private fun initSettingHandlers() {
        settingHandlers.add(ScoutingPositionSettingHandler(settingsDatabase, positionEntry))
    }

    private fun updateUI() {
        settingHandlers.forEach {
            it.updateUI()
        }
    }

    private fun setClickListeners() {
        applyButton.setOnClickListener { applyButtonAction() }
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