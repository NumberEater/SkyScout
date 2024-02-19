package org.skylinerobotics.skyscout.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.skylinerobotics.skyscout.R

class MainActivity : AppCompatActivity() {
    private lateinit var settingsButton: Button
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayoutElements()
        setClickListeners()
    }

    private fun initLayoutElements() {
        settingsButton = findViewById(R.id.settings_button)
        startButton = findViewById(R.id.start_button)
    }

    private fun setClickListeners() {
        settingsButton.setOnClickListener { settingsButtonAction() }
        startButton.setOnClickListener { startButtonAction() }
    }

    private fun settingsButtonAction() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun startButtonAction() {
        startActivity(Intent(this, GameScoutActivity::class.java))
    }
}