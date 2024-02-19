package org.skylinerobotics.skyscout.ui

import androidx.fragment.app.Fragment
import org.skylinerobotics.skyscout.data.datacontainer.DataContainer

abstract class GameScoutFragment(private val teamNumber: Int) : Fragment() {
 abstract fun getDataContainer(): DataContainer
}