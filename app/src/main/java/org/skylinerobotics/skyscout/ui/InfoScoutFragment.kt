package org.skylinerobotics.skyscout.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datacontainer.InfoDataContainer
import org.skylinerobotics.skyscout.data.datahandler.InfoDataHandler

class InfoScoutFragment(private val teamNumber: Int) : GameScoutFragment(teamNumber) {
    private val dataHandler = InfoDataHandler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_info_scout, container, false)

        updateTeamNumber(layout)

        return layout
    }

    override fun getDataContainer(): InfoDataContainer {
        return dataHandler.getDataContainer()
    }

    private fun submitButtonAction() {
        val parentActivity = activity as GameScoutActivity
        parentActivity.submitScout()
    }

    private fun updateTeamNumber(layout: View) {
        if (teamNumber > 0) {
            dataHandler.setTeamNumber(teamNumber)
            layout.findViewById<EditText>(R.id.team_number_input).setText(teamNumber.toString())
        }
    }
}