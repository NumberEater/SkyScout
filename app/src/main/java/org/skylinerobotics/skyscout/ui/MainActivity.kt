package org.skylinerobotics.skyscout.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.skylinerobotics.skyscout.Constants
import org.skylinerobotics.skyscout.R

class MainActivity : AppCompatActivity() {
    private lateinit var settingsButton: Button
    private lateinit var startButton: Button

    private val bluetoothManager = BluetoothAvailabilityManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayoutElements()
        setClickListeners()
        prepareBluetooth()
    }

    private fun initLayoutElements() {
        settingsButton = findViewById(R.id.settings_button)
        startButton = findViewById(R.id.start_button)
    }

    private fun setClickListeners() {
        settingsButton.setOnClickListener { settingsButtonAction() }
        startButton.setOnClickListener { startButtonAction() }
    }

    private fun prepareBluetooth() {
        bluetoothManager.enableBluetoothIfDisabled()
        bluetoothManager.requestDeniedPermissions()
    }

    private fun settingsButtonAction() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun startButtonAction() {
        val scoutTypeSelectionDialog = ScoutTypeSelectionDialog(this, ::scoutTypeSelectionCallback)
        scoutTypeSelectionDialog.show()
    }

    private fun scoutTypeSelectionCallback(scoutType: Constants.ScoutType) {
        when (scoutType) {
            Constants.ScoutType.MATCH -> {
                matchScoutButtonAction()
            }
            Constants.ScoutType.PIT -> {
                startActivity(Intent(this, PitScoutActivity::class.java))
            }
        }
    }

    private fun matchScoutButtonAction() {
        if (bluetoothManager.isBluetoothReady()) {
            startActivity(Intent(this, GameScoutActivity::class.java))
        } else {
            Toast.makeText(this, R.string.bluetooth_restart_app, Toast.LENGTH_LONG).show()
        }
    }
}