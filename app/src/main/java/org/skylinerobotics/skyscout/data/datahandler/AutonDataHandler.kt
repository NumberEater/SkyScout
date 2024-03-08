package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.Constants
import org.skylinerobotics.skyscout.data.datacontainer.AutonDataContainer
import java.util.Stack

class AutonDataHandler : DataHandler() {
    private val dataContainer = AutonDataContainer()

    private val noteActions = Stack<Constants.NoteActions>()

    fun setLeftWing(leftWing: Boolean) {
        dataContainer.leftWing = leftWing
    }

    fun incrementAmpNoteScored() {
        dataContainer.ampNotesAttempted++
        dataContainer.ampNotesScored++
        addNoteAction(Constants.NoteActions.AMP_NOTE_SCORED)
    }

    fun incrementSpeakerNoteScored() {
        dataContainer.speakerNotesAttempted++
        dataContainer.speakerNotesScored++
        addNoteAction(Constants.NoteActions.SPEAKER_NOTE_SCORED)
    }

    fun incrementAmpNoteFailed() {
        dataContainer.ampNotesAttempted++
        addNoteAction(Constants.NoteActions.AMP_NOTE_ATTEMPTED)
    }

    fun incrementSpeakerNoteFailed() {
        dataContainer.speakerNotesAttempted++
        addNoteAction(Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED)
    }

    fun undoLastNote() {
        if (!noteActions.isEmpty()) {
            when (noteActions.pop()) {
                Constants.NoteActions.AMP_NOTE_ATTEMPTED -> {
                    dataContainer.ampNotesAttempted--
                }
                Constants.NoteActions.AMP_NOTE_SCORED -> {
                    dataContainer.ampNotesAttempted--
                    dataContainer.ampNotesScored--
                }
                Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED -> {
                    dataContainer.speakerNotesAttempted--
                }
                Constants.NoteActions.SPEAKER_NOTE_SCORED -> {
                    dataContainer.speakerNotesAttempted--
                    dataContainer.speakerNotesScored--
                }
                else -> {}
            }
        }
    }

    private fun addNoteAction(key: Constants.NoteActions) {
        noteActions.push(key)

        if (noteActions.size > 200) {
            noteActions.removeAt(0)
        }
    }

    override fun getDataContainer(): AutonDataContainer {
        return dataContainer
    }
}