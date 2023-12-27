package org.skylinerobotics.skyscout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.switchmaterial.SwitchMaterial
import org.skylinerobotics.skyscout.settinghandlers.DarkModeSettingHandler
import org.skylinerobotics.skyscout.settinghandlers.ScoutingPositionSettingHandler
import org.skylinerobotics.skyscout.settinghandlers.SettingHandler

class SettingsActivity : AppCompatActivity() {
    private lateinit var applyButton: Button
    private lateinit var positionEntry: EditText

    private lateinit var settingsDatabase: SettingsDatabase
    private var settingHandlers: MutableList<SettingHandler> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsDatabase = SettingsDatabase(applicationContext)

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

        Toast.makeText(applicationContext, "Settings Applied", Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    private fun updateSettingsDatabase() {
        settingHandlers.forEach {
            it.applySetting()
        }
    }
}