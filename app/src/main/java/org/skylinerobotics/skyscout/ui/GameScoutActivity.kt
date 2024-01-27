package org.skylinerobotics.skyscout.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import org.skylinerobotics.skyscout.R

class GameScoutActivity : AppCompatActivity() {

    private lateinit var backPressedCallback: OnBackPressedCallback

    private var teamNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_scout)

        initBackPressedCallback()

        promptTeamNumber()
    }

    private fun promptTeamNumber() {
        val dialog = TeamNumberDialog(this, ::teamNumberFinishCallback)
        dialog.show()
    }

    private fun teamNumberFinishCallback(number: Int) {
        teamNumber = number
        findViewById<TextView>(R.id.team_number_label).text = teamNumber.toString()
    }

    private fun initBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showWarningDialog()
            }
        }
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    private fun showWarningDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to quit?")
        builder.setNegativeButton("No") { _, _ -> }
        builder.setPositiveButton("Yes") { _, _ ->
            finish()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}