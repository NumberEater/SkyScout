package org.skylinerobotics.skyscout.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.datacontainer.AutonDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.MatchSpeakerShotsDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.TeleopDataContainer
import org.skylinerobotics.skyscout.data.datahandler.AutonDataHandler
import org.skylinerobotics.skyscout.data.datahandler.SpeakerShotDataHandler
import org.skylinerobotics.skyscout.data.datahandler.TeleopDataHandler

class TeleopScoutFragment(private val teamNumber: Int) : GameScoutFragment(teamNumber) {
    private val dataHandler = TeleopDataHandler()
    private val shotsDataHandler = SpeakerShotDataHandler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_teleop_scout, container, false)

        layout.findViewById<TextView>(R.id.team_number_label).text = teamNumber.toString()

        layout.findViewById<Button>(R.id.speaker_shot_button).setOnClickListener {
            loadParentFragment(SpeakerShotMapFragment(this, shotsDataHandler, dataHandler))
        }

        return layout
    }

    override fun getDataContainer(): TeleopDataContainer {
        return dataHandler.getDataContainer()
    }

    fun getShotDataHandler(): SpeakerShotDataHandler {
        return shotsDataHandler
    }

    private fun loadParentFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
}