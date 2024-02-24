package org.skylinerobotics.skyscout.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.skylinerobotics.skyscout.data.datacontainer.MatchDataContainer

class MatchDatabase(context: Context) {
    private val database: SQLiteDatabase

    companion object {
        private const val CREATE_MAIN_TABLE = """
            CREATE TABLE IF NOT EXISTS ScoutData(
                ScoutName VARCHAR,
                MatchNumber INTEGER,
                TeamNumber INTEGER,
                LeftWing BOOLEAN,
                AutonAmpNotesAttempted INTEGER,
                AutonAmpNotesScored INTEGER,
                AutonSpeakerNotesAttempted INTEGER,
                AutonSpeakerNotesScored INTEGER,
                TeleopAmpNotesAttempted INTEGER,
                TeleopAmpNotesScored INTEGER,
                TeleopSpeakerNotesAttempted INTEGER,
                TeleopSpeakerNotesScored INTEGER,
                TeleopSpeakerNotesAttemptedAmped INTEGER,
                TeleopSpeakerNotesScoredAmped INTEGER,
                TrapNotesAttempted INTEGER,
                TrapNotesScored INTEGER,
                Park BOOLEAN,
                Onstage BOOLEAN,
                Spotlight BOOLEAN,
                Harmony BOOLEAN,
                Breakdown BOOLEAN,
                Notes VARCHAR)"""

        private const val ADD_ENTRY_FORMAT = """
            INSERT INTO ScoutData VALUES(
                "%s",
                %d,
                %d,
                %s,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %s,
                %s,
                %s,
                %s,
                %s,
                "%s")
        """
    }

    init {
        database = context.openOrCreateDatabase("match_data.db", Context.MODE_PRIVATE, null)

        database.execSQL(CREATE_MAIN_TABLE)
    }

    fun addEntry(matchData: MatchDataContainer) {
        val insertCommand = generateInsertCommand(matchData)
        database.execSQL(insertCommand)
    }

    fun close() {
        database.close()
    }

    private fun generateInsertCommand(matchData: MatchDataContainer): String {
        return String.format(ADD_ENTRY_FORMAT,
            matchData.infoData.scoutName,
            matchData.infoData.matchNumber,
            matchData.infoData.teamNumber,
            if (matchData.autonData.leftWing) "TRUE" else "FALSE",
            matchData.autonData.ampNotesAttempted,
            matchData.autonData.ampNotesScored,
            matchData.autonData.speakerNotesAttempted,
            matchData.autonData.speakerNotesScored,
            matchData.teleopData.ampNotesAttempted,
            matchData.teleopData.ampNotesScored,
            matchData.teleopData.speakerNotesAttempted,
            matchData.teleopData.speakerNotesScored,
            matchData.teleopData.speakerNotesAttemptedAmped,
            matchData.teleopData.speakerNotesScoredAmped,
            matchData.teleopData.trapNotesAttempted,
            matchData.teleopData.trapNotesScored,
            if (matchData.teleopData.parked) "TRUE" else "FALSE",
            if (matchData.teleopData.onstage) "TRUE" else "FALSE",
            if (matchData.teleopData.spotlight) "TRUE" else "FALSE",
            if (matchData.teleopData.harmony) "TRUE" else "FALSE",
            if (matchData.infoData.breakdown) "TRUE" else "FALSE",
            matchData.infoData.notes)
    }
}