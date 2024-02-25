package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.data.datacontainer.AutonDataContainer

class AutonDataHandler : DataHandler() {
    private val autonDataContainer = AutonDataContainer()

    fun setLeftWing(leftWing: Boolean) {
        autonDataContainer.leftWing = leftWing
    }

    fun incrementAmpNoteScored() {
        autonDataContainer.ampNotesAttempted++
        autonDataContainer.ampNotesScored++
    }

    fun incrementSpeakerNoteScored() {
        autonDataContainer.speakerNotesAttempted++
        autonDataContainer.speakerNotesScored++
    }

    fun incrementAmpNoteFailed() {
        autonDataContainer.ampNotesAttempted++
    }

    fun incrementSpeakerNoteFailed() {
        autonDataContainer.speakerNotesAttempted++
    }

    override fun getDataContainer(): AutonDataContainer {
        return autonDataContainer
    }
}