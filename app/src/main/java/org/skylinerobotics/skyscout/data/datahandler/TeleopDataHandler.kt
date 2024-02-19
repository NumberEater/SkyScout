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

    fun incrementSpeakerNoteScored(amped: Boolean) {
        if (amped) {
            dataContainer.speakerNotesAttemptedAmped++
            dataContainer.speakerNotesScoredAmped++

            addNoteAction(Constants.NoteActions.SPEAKER_NOTE_SCORED_AMPLIFIED)
        } else {
            dataContainer.speakerNotesAttempted++
            dataContainer.speakerNotesScored++

            addNoteAction(Constants.NoteActions.SPEAKER_NOTE_SCORED)
        }
    }

    fun incrementSpeakerNoteFailed(amped: Boolean) {
        if (amped) {
            dataContainer.speakerNotesAttemptedAmped++
            addNoteAction(Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED_AMPLIFIED)
        } else {
            dataContainer.speakerNotesAttempted++
            addNoteAction(Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED)
        }
    }


    // Could be cleaned up
    fun undoLastNote() {
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
            Constants.NoteActions.SPEAKER_NOTE_ATTEMPTED_AMPLIFIED -> {
                dataContainer.speakerNotesAttemptedAmped--
            }
            Constants.NoteActions.SPEAKER_NOTE_SCORED_AMPLIFIED -> {
                dataContainer.speakerNotesAttemptedAmped--
                dataContainer.speakerNotesScoredAmped--
            }
            else -> {}
        }
    }

    private fun addNoteAction(key: Constants.NoteActions) {
        noteActions.push(key)

        if (noteActions.size > 30) {
            noteActions.removeAt(0)
        }
    }

    override fun getDataContainer(): TeleopDataContainer {
        return dataContainer
    }
}