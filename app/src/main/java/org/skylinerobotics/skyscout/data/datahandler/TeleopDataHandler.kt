package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.Constants
import org.skylinerobotics.skyscout.data.datacontainer.TeleopDataContainer
import java.util.Stack

class TeleopDataHandler : DataHandler() {
    private val dataContainer = TeleopDataContainer()

    private val noteActions = Stack<Constants.NoteActions>()

    fun setParked(parked: Boolean) {
        dataContainer.parked = parked
    }

    fun setOnstage(onstage: Boolean) {
        dataContainer.onstage = onstage
    }

    fun setSpotlight(spotlight: Boolean) {
        dataContainer.spotlight = spotlight
    }

    fun setHarmony(harmony: Boolean) {
        dataContainer.harmony = harmony
    }

    fun incrementAmpNoteScored() {
        dataContainer.ampNotesAttempted++
        dataContainer.ampNotesScored++

        addNoteAction(Constants.NoteActions.AMP_NOTE_SCORED)
    }

    fun incrementAmpNoteFailed() {
        dataContainer.ampNotesAttempted++

        addNoteAction(Constants.NoteActions.AMP_NOTE_ATTEMPTED)
    }

    fun incrementSpeakerNoteScored() {
        dataContainer.speakerNotesAttempted++
        dataContainer.speakerNotesScored++
        addNoteAction(Constants.NoteActions.SPEAKER_NOTE_SCORED)
    }

    fun incrementSpeakerNoteFailed() {
        dataContainer.speakerNotesAttempted++
        addNoteAction(Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED)
    }

    fun incrementTrapNoteScored() {
        dataContainer.trapNotesAttempted++
        dataContainer.trapNotesScored++
        addNoteAction(Constants.NoteActions.TRAP_NOTE_SCORED)
    }

    fun incrementTrapNoteFailed() {
        dataContainer.trapNotesAttempted++
        addNoteAction(Constants.NoteActions.TRAP_NOTE_ATTEMPTED)
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
                Constants.NoteActions.TRAP_NOTE_ATTEMPTED -> {
                    dataContainer.trapNotesAttempted--
                }
                Constants.NoteActions.TRAP_NOTE_SCORED -> {
                    dataContainer.trapNotesAttempted--
                    dataContainer.trapNotesScored--
                }
                else -> {}
            }
        }
    }

    fun getLastNoteAction(): Constants.NoteActions? {
        return if (noteActions.isEmpty()) null
        else noteActions.peek()
    }

    private fun addNoteAction(key: Constants.NoteActions) {
        noteActions.push(key)

        if (noteActions.size > 200) {
            noteActions.removeAt(0)
        }
    }

    override fun getDataContainer(): TeleopDataContainer {
        return dataContainer
    }
}