package org.skylinerobotics.skyscout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datacontainer.AutonDataContainer
import org.skylinerobotics.skyscout.data.datahandler.AutonDataHandler

class AutonScoutFragment(private val teamNumber: Int) : GameScoutFragment(teamNumber) {
    private val dataHandler = AutonDataHandler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_auton_scout, container, false)

        layout.findViewById<TextView>(R.id.team_number_label).text = teamNumber.toString()

        return layout
    }

    override fun getDataContainer(): AutonDataContainer {
        return dataHandler.getDataContainer()
    }
}