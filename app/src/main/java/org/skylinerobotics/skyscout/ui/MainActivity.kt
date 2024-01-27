package org.skylinerobotics.skyscout.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.skylinerobotics.skyscout.R

class MainActivity : AppCompatActivity() {
    private lateinit var settingsButton: Button
    private lateinit var startButton: Button
    private lateinit var suggestionsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayoutElements()
        setClickListeners()
    }

    private fun initLayoutElements() {
        settingsButton = findViewById(R.id.settings_button)
        startButton = findViewById(R.id.start_button)
        suggestionsButton = findViewById(R.id.suggestion_button)
    }

    private fun setClickListeners() {
        settingsButton.setOnClickListener { settingsButtonAction() }
        startButton.setOnClickListener { startButtonAction() }
        suggestionsButton.setOnClickListener { suggestionsButtonAction() }
    }

    private fun settingsButtonAction() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun startButtonAction() {
        startActivity(Intent(this, GameScoutActivity::class.java))
    }

    private fun suggestionsButtonAction() {
        startActivity(Intent(this, SuggestionsActivity::class.java))
    }
}