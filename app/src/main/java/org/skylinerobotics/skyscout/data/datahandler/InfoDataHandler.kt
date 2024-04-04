package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.data.datacontainer.InfoDataContainer


class InfoDataHandler : DataHandler() {
    private val infoDataContainer = InfoDataContainer()

    fun setScoutName(name: String) {
        infoDataContainer.scoutName = name
    }

    fun setMatchNumber(number: Int) {
        infoDataContainer.matchNumber = number
    }

    fun setTeamNumber(number: Int) {
        infoDataContainer.teamNumber = number
    }

    fun setBreakdown(breakdown: Boolean) {
        infoDataContainer.breakdown = breakdown
    }

    fun setNotes(notes: String) {
        infoDataContainer.notes = notes
    }

    fun setDefenseDescription(description: String) {
        infoDataContainer.defenseDescription = description
    }

    override fun getDataContainer(): InfoDataContainer {
        return infoDataContainer
    }
}