package org.skylinerobotics.skyscout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datacontainer.AutonDataContainer
import org.skylinerobotics.skyscout.data.datahandler.AutonDataHandler

class AutonScoutFragment(private val teamNumber: Int) : GameScoutFragment(teamNumber) {
    private val dataHandler = AutonDataHandler()

    private lateinit var layout: View

    private lateinit var speakerScoredButton: Button
    private lateinit var speakerFailedButton: Button
    private lateinit var ampScoredButton: Button
    private lateinit var  ampFailedButton: Button
    private lateinit var leftWingCheckbox: CheckBox
    private lateinit var undoButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_auton_scout, container, false)

        layout.findViewById<TextView>(R.id.team_number_label).text = teamNumber.toString()

        initializeButtons()
        setButtonActions()
        updateButtonText()

        return layout
    }

    private fun initializeButtons() {
        speakerScoredButton = layout.findViewById(R.id.speaker_shot_scored_button)
        speakerFailedButton = layout.findViewById(R.id.speaker_shot_failed_button)
        ampScoredButton = layout.findViewById(R.id.amp_shot_scored_button)
        ampFailedButton = layout.findViewById(R.id.amp_shot_failed_button)
        leftWingCheckbox = layout.findViewById(R.id.left_wing_checkbox)
        undoButton = layout.findViewById(R.id.undo_button)
    }

    private fun updateButtonText() {
        speakerScoredButton.text = getString(
            R.string.auton_fragment_speaker_scored,
            dataHandler.getDataContainer().speakerNotesScored
        )

        speakerFailedButton.text = getString(
            R.string.auton_fragment_speaker_failed,
            dataHandler.getDataContainer().speakerNotesAttempted - dataHandler.getDataContainer().speakerNotesScored
        )

        ampScoredButton.text = getString(
            R.string.auton_fragment_amp_scored,
            dataHandler.getDataContainer().ampNotesScored
        )

        ampFailedButton.text = getString(
            R.string.auton_fragment_amp_failed,
            dataHandler.getDataContainer().ampNotesAttempted - dataHandler.getDataContainer().ampNotesScored
        )
    }

    private fun setButtonActions() {
        speakerScoredButton.setOnClickListener { speakerScoredButtonAction() }
        speakerFailedButton.setOnClickListener { speakerFailedButtonAction() }
        ampScoredButton.setOnClickListener { ampScoredButtonAction() }
        ampFailedButton.setOnClickListener { ampFailedButtonAction() }
        leftWingCheckbox.setOnClickListener { dataHandler.setLeftWing(leftWingCheckbox.isChecked) }
        undoButton.setOnClickListener { undoButtonAction() }
    }

    private fun speakerScoredButtonAction() {
        dataHandler.incrementSpeakerNoteScored()
        speakerScoredButton.text = getString(
            R.string.auton_fragment_speaker_scored,
            dataHandler.getDataContainer().speakerNotesScored
        )
    }

    private fun speakerFailedButtonAction() {
        dataHandler.incrementSpeakerNoteFailed()
        speakerFailedButton.text = getString(
            R.string.auton_fragment_speaker_failed,
            dataHandler.getDataContainer().speakerNotesAttempted - dataHandler.getDataContainer().speakerNotesScored
        )
    }

    private fun ampScoredButtonAction() {
        dataHandler.incrementAmpNoteScored()
        ampScoredButton.text = getString(
            R.string.auton_fragment_amp_scored,
            dataHandler.getDataContainer().ampNotesScored
        )
    }

    private fun ampFailedButtonAction() {
        dataHandler.incrementAmpNoteFailed()
        ampFailedButton.text = getString(
            R.string.auton_fragment_amp_failed,
            dataHandler.getDataContainer().ampNotesAttempted - dataHandler.getDataContainer().ampNotesScored
        )
    }

    private fun undoButtonAction() {
        dataHandler.undoLastNote()
        updateButtonText()
    }

    override fun getDataContainer(): AutonDataContainer {
        return dataHandler.getDataContainer()
    }
}