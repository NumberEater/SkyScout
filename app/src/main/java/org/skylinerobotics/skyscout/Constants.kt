package org.skylinerobotics.skyscout

object Constants {
    enum class NoteActions {
        AMP_NOTE_ATTEMPTED,
        AMP_NOTE_SCORED,
        SPEAKER_NOTE_ATTEMPTED,
        SPEAKER_NOTE_SCORED,
        SPEAKER_NOTE_ATTEMPTED_AMPLIFIED,
        SPEAKER_NOTE_SCORED_AMPLIFIED,
        TRAP_NOTE_ATTEMPTED,
        TRAP_NOTE_SCORED
    }

    enum class ScoutType {
        MATCH,
        PIT
    }
}