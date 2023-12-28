package org.skylinerobotics.skyscout

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import java.awt.font.TextAttribute

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
        TeamNumberDialog()
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