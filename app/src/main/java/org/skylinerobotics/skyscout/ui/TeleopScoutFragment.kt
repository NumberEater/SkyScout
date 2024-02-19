package org.skylinerobotics.skyscout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datacontainer.AutonDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.TeleopDataContainer
import org.skylinerobotics.skyscout.data.datahandler.AutonDataHandler
import org.skylinerobotics.skyscout.data.datahandler.TeleopDataHandler

class TeleopScoutFragment(private val teamNumber: Int) : GameScoutFragment(teamNumber) {
    private val dataHandler = TeleopDataHandler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_teleop_scout, container, false)

        layout.findViewById<TextView>(R.id.team_number_label).text = teamNumber.toString()

        return layout
    }

    override fun getDataContainer(): TeleopDataContainer {
        return dataHandler.getDataContainer()
    }
}