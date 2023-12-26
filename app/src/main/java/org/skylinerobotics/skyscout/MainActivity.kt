package org.skylinerobotics.skyscout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var settingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayoutElements()

        settingsButton.setOnClickListener { settingsButtonAction() }
    }

    private fun initLayoutElements() {
        settingsButton = findViewById(R.id.settings_button)
    }

    private fun settingsButtonAction() {
        startActivity(Intent(applicationContext, SettingsActivity::class.java))
    }
}