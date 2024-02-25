package org.skylinerobotics.skyscout.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datacontainer.InfoDataContainer
import org.skylinerobotics.skyscout.data.datahandler.InfoDataHandler

class InfoScoutFragment(private val teamNumber: Int) : GameScoutFragment(teamNumber) {
    private val dataHandler = InfoDataHandler()
    private lateinit var layout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_info_scout, container, false)

        updateTeamNumber()

        layout.findViewById<Button>(R.id.submit_button).setOnClickListener { submitButtonAction() }

        return layout
    }

    override fun getDataContainer(): InfoDataContainer {
        return dataHandler.getDataContainer()
    }

    private fun submitButtonAction() {
        syncDataHandlerToUI()
        val parentActivity = activity as GameScoutActivity
        parentActivity.submitScout()
    }

    private fun updateTeamNumber() {
        if (teamNumber > 0) {
            layout.findViewById<EditText>(R.id.team_number_input).setText(teamNumber.toString())
        }
    }

    private fun syncDataHandlerToUI() {
        val nameEntry: EditText = layout.findViewById(R.id.scout_name_input)
        val matchNumberEntry: EditText = layout.findViewById(R.id.match_number_input)
        val teamNumberEntry: EditText = layout.findViewById(R.id.team_number_input)
        val notesEntry: EditText = layout.findViewById(R.id.notes_input)
        val robotBreakdown = layout.findViewById<CheckBox>(R.id.robot_breakdown_checkbox).isChecked

        dataHandler.setScoutName(nameEntry.editableText.toString())
        dataHandler.setMatchNumber(matchNumberEntry.editableText.toString().toIntOrNull() ?: 0)
        dataHandler.setTeamNumber(teamNumberEntry.editableText.toString().toIntOrNull() ?: 0)
        dataHandler.setNotes(notesEntry.editableText.toString())
        dataHandler.setBreakdown(robotBreakdown)
    }
}