package org.skylinerobotics.skyscout.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.database.ScoutDatabase
import org.skylinerobotics.skyscout.data.datacontainer.PitDataContainer

class PitScoutActivity : AppCompatActivity() {
    private lateinit var teamNumberInput: EditText

    private lateinit var ampScoreCheckbox: CheckBox
    private lateinit var speakerScoreCheckbox: CheckBox
    private lateinit var trapScoreCheckbox: CheckBox

    private lateinit var intakeGroundCheckbox: CheckBox
    private lateinit var intakeSourceCheckbox: CheckBox

    private lateinit var shooterRadioGroup: RadioGroup
    private lateinit var drivetrainRadioGroup: RadioGroup

    private lateinit var climbCheckbox: CheckBox

    private lateinit var submitButton: Button

    private lateinit var underStageCheckbox: CheckBox
    private lateinit var harmonyCheckbox: CheckBox
    private lateinit var notesInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pit_scout)

        initLayoutElements()
    }

    private fun initLayoutElements() {
        teamNumberInput = findViewById(R.id.team_number_input)

        underStageCheckbox = findViewById(R.id.drive_under_stage_checkbox)

        ampScoreCheckbox = findViewById(R.id.amp_checkbox)
        speakerScoreCheckbox = findViewById(R.id.speaker_checkbox)
        trapScoreCheckbox = findViewById(R.id.trap_checkbox)

        intakeGroundCheckbox = findViewById(R.id.ground_intake_checkbox)
        intakeSourceCheckbox = findViewById(R.id.source_intake_checkbox)

        shooterRadioGroup = findViewById(R.id.shooter_type_radio_group)
        drivetrainRadioGroup = findViewById(R.id.drivetrain_type_radio_group)

        climbCheckbox = findViewById(R.id.climb_checkbox)
        harmonyCheckbox = findViewById(R.id.harmonize_checkbox)

        notesInput = findViewById(R.id.notes_input)

        submitButton = findViewById(R.id.submit_button)
        submitButton.setOnClickListener { submitButtonAction() }
    }

    private fun constructDataContainer(): PitDataContainer {
        val teamNumber = teamNumberInput.text.toString().toIntOrNull() ?: 0
        val canDriveUnderStage = underStageCheckbox.isChecked
        val canDoAmp = ampScoreCheckbox.isChecked
        val canDoSpeaker = speakerScoreCheckbox.isChecked
        val canDoTrap = trapScoreCheckbox.isChecked
        val canGroundIntake = intakeGroundCheckbox.isChecked
        val canSourceIntake = intakeSourceCheckbox.isChecked

        val shooterType = when (shooterRadioGroup.checkedRadioButtonId) {
            R.id.shooter_none_button -> "NONE"
            R.id.shooter_pivot_button -> "PIVOTING"
            else -> "FIXED"
        }

        val drivetrainType = when (drivetrainRadioGroup.checkedRadioButtonId) {
            R.id.drivetrain_mecanum_button -> "MECANUM"
            R.id.drivetrain_swerve_button -> "SWERVE"
            else -> "TANK"
        }

        val canClimb = climbCheckbox.isChecked
        val canHarmonize = harmonyCheckbox.isChecked

        return PitDataContainer(
            teamNumber,
            canDriveUnderStage,
            canDoAmp,
            canDoSpeaker,
            canDoTrap,
            canGroundIntake,
            canSourceIntake,
            shooterType,
            drivetrainType,
            canClimb,
            canHarmonize,
            getFormattedTextFromEntry(notesInput)
        )
    }

    private fun getFormattedTextFromEntry(entry: EditText): String {
        return entry
            .text
            .toString()
            .replace(',', ';')
    }

    private fun submitButtonAction() {
        val database = ScoutDatabase(this)
        database.addPitEntry(constructDataContainer())
        database.close()

        Toast.makeText(this, "Scout Stored", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }
}