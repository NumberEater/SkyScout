package org.skylinerobotics.skyscout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.skylinerobotics.skyscout.Constants
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datacontainer.TeleopDataContainer
import org.skylinerobotics.skyscout.data.datahandler.SpeakerShotDataHandler
import org.skylinerobotics.skyscout.data.datahandler.TeleopDataHandler
import org.skylinerobotics.skyscout.settings.SettingsDatabase

class TeleopScoutFragment(private val teamNumber: Int) : GameScoutFragment(teamNumber) {
    private val dataHandler = TeleopDataHandler()
    private val shotsDataHandler = SpeakerShotDataHandler()
    private lateinit var layout: View

    private lateinit var speakerScoredButton: Button
    private lateinit var speakerFailedButton: Button
    private lateinit var ampShotScoredButton: Button
    private lateinit var ampShotFailedButton: Button
    private lateinit var trapScoredButton: Button
    private lateinit var trapFailedButton: Button
    private lateinit var parkedCheckbox: CheckBox
    private lateinit var onstageCheckbox: CheckBox
    private lateinit var spotlightCheckbox: CheckBox
    private lateinit var harmonyCheckbox: CheckBox
    private lateinit var undoButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_teleop_scout, container, false)

        layout.findViewById<TextView>(R.id.team_number_label).text = teamNumber.toString()

        initializeButtons()
        setButtonActions()
        updateButtonText()

        return layout
    }

    override fun getDataContainer(): TeleopDataContainer {
        return dataHandler.getDataContainer()
    }

    fun getShotDataHandler(): SpeakerShotDataHandler {
        return shotsDataHandler
    }

    private fun initializeButtons() {
        speakerScoredButton = layout.findViewById(R.id.speaker_shot_scored_button)
        speakerFailedButton = layout.findViewById(R.id.speaker_shot_failed_button)
        ampShotScoredButton = layout.findViewById(R.id.amp_shot_scored_button)
        ampShotFailedButton = layout.findViewById(R.id.amp_shot_failed_button)
        trapScoredButton = layout.findViewById(R.id.trap_scored_button)
        trapFailedButton = layout.findViewById(R.id.trap_failed_button)
        undoButton = layout.findViewById(R.id.undo_button)

        parkedCheckbox = layout.findViewById(R.id.parked_checkbox)
        onstageCheckbox = layout.findViewById(R.id.onstage_checkbox)
        spotlightCheckbox = layout.findViewById(R.id.spotlight_checkbox)
        harmonyCheckbox = layout.findViewById(R.id.harmony_checkbox)
    }

    private fun updateButtonText() {
        speakerScoredButton.text = getString(
            R.string.teleop_fragment_speaker_shot_scored,
            dataHandler.getDataContainer().speakerNotesScored
        )
        speakerFailedButton.text = getString(
            R.string.teleop_fragment_speaker_shot_failed,
            (dataHandler.getDataContainer().speakerNotesAttempted - dataHandler.getDataContainer().speakerNotesScored)
        )

        ampShotScoredButton.text = getString(
            R.string.teleop_fragment_amp_shot_scored,
            dataHandler.getDataContainer().ampNotesScored
        )

        ampShotFailedButton.text = getString(
            R.string.teleop_fragment_amp_shot_failed,
            dataHandler.getDataContainer().ampNotesAttempted - dataHandler.getDataContainer().ampNotesScored
        )

        trapScoredButton.text = getString(
            R.string.teleop_fragment_trap_scored,
            dataHandler.getDataContainer().trapNotesScored
        )

        trapFailedButton.text = getString(
            R.string.teleop_fragment_trap_failed,
            dataHandler.getDataContainer().trapNotesAttempted - dataHandler.getDataContainer().trapNotesScored
        )
    }

    private fun setButtonActions() {
        speakerScoredButton.setOnClickListener { speakerScoredButtonAction() }
        speakerFailedButton.setOnClickListener { speakerFailedButtonAction() }
        ampShotScoredButton.setOnClickListener { ampShotScoredButtonAction() }
        ampShotFailedButton.setOnClickListener { ampShotFailedButtonAction() }
        trapScoredButton.setOnClickListener { trapScoredButtonAction() }
        trapFailedButton.setOnClickListener { trapFailedButtonAction() }
        undoButton.setOnClickListener { undoButtonAction() }

        parkedCheckbox.setOnClickListener { dataHandler.setParked(parkedCheckbox.isChecked) }
        onstageCheckbox.setOnClickListener { dataHandler.setOnstage(onstageCheckbox.isChecked) }
        spotlightCheckbox.setOnClickListener { dataHandler.setSpotlight(spotlightCheckbox.isChecked) }
        harmonyCheckbox.setOnClickListener { dataHandler.setHarmony(harmonyCheckbox.isChecked) }
    }

    private fun speakerScoredButtonAction() {
        loadParentFragment(SpeakerShotMapFragment(this, shotsDataHandler, dataHandler, true, loadScoutingPosition()))
    }

    private fun speakerFailedButtonAction() {
        loadParentFragment(SpeakerShotMapFragment(this, shotsDataHandler, dataHandler, true, loadScoutingPosition()))
    }

    private fun loadScoutingPosition(): String? {
        val settings = SettingsDatabase(requireContext())
        return settings.loadSetting("scouting-position")
    }

    private fun ampShotScoredButtonAction() {
        dataHandler.incrementAmpNoteScored()
        ampShotScoredButton.text = getString(
            R.string.teleop_fragment_amp_shot_scored,
            dataHandler.getDataContainer().ampNotesScored
        )
    }

    private fun ampShotFailedButtonAction() {
        dataHandler.incrementAmpNoteFailed()
        ampShotFailedButton.text = getString(
            R.string.teleop_fragment_amp_shot_failed,
            dataHandler.getDataContainer().ampNotesAttempted - dataHandler.getDataContainer().ampNotesScored
        )
    }

    private fun trapScoredButtonAction() {
        dataHandler.incrementTrapNoteScored()
        trapScoredButton.text = getString(
            R.string.teleop_fragment_trap_scored,
            dataHandler.getDataContainer().trapNotesScored
        )
    }

    private fun trapFailedButtonAction() {
        dataHandler.incrementTrapNoteFailed()
        trapFailedButton.text = getString(
            R.string.teleop_fragment_trap_failed,
            dataHandler.getDataContainer().trapNotesAttempted - dataHandler.getDataContainer().trapNotesScored
        )
    }

    private fun undoButtonAction() {
        val lastNoteAction = dataHandler.getLastNoteAction()
        if (lastNoteAction == Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED
            || lastNoteAction == Constants.NoteActions.SPEAKER_NOTE_SCORED
            || lastNoteAction == Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED_AMPLIFIED
            || lastNoteAction == Constants.NoteActions.SPEAKER_NOTE_SCORED_AMPLIFIED) {
            shotsDataHandler.undoLastShot()
        }
        dataHandler.undoLastNote()
        updateButtonText()
    }

    private fun loadParentFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
}