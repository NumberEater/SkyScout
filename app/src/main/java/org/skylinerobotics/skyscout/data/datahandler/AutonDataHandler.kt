package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.data.datacontainer.AutonDataContainer

class AutonDataHandler : DataHandler() {
    private val autonDataContainer = AutonDataContainer()

    fun setLeftWing(leftWing: Boolean) {
        autonDataContainer.leftWing = leftWing
    }

    fun ampNoteScored() {
        autonDataContainer.ampNotesAttempted++
        autonDataContainer.ampNotesScored++
    }

    fun speakerNoteScored() {
        autonDataContainer.speakerNotesAttempted++
        autonDataContainer.speakerNotesScored++
    }

    fun ampNoteFailed() {
        autonDataContainer.ampNotesAttempted++
    }

    fun speakerNoteFailed() {
        autonDataContainer.ampNotesAttempted++
    }

    override fun getDataContainer(): AutonDataContainer {
        return autonDataContainer
    }
}