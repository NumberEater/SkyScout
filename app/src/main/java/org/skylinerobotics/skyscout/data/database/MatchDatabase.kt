package org.skylinerobotics.skyscout.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.skylinerobotics.skyscout.data.datacontainer.MatchDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.MatchSpeakerShotsDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.SpeakerShotDataContainer

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

        private const val ADD_MATCH_ENTRY_FORMAT = """
            INSERT INTO ScoutData VALUES(
                "%s",
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
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                "%s")
        """

        private const val CREATE_SHOTS_TABLE = """
            CREATE TABLE IF NOT EXISTS SpeakerShots(
                MatchNumber INTEGER,
                TeamNumber INTEGER,
                ShotX REAL,
                ShotY REAL,
                Scored BOOLEAN)
        """

        private const val ADD_SHOT_ENTRY_FORMAT = """
            INSERT INTO SpeakerShots VALUES(
                %d,
                %d,
                %.2f,
                %.2f,
                %d)
        """
    }

    init {
        database = context.openOrCreateDatabase("match_data.db", Context.MODE_PRIVATE, null)

        database.execSQL(CREATE_MAIN_TABLE)
        database.execSQL(CREATE_SHOTS_TABLE)
    }

    fun addMatchEntry(matchData: MatchDataContainer) {
        val insertCommand = generateInsertMatchCommand(matchData)
        database.execSQL(insertCommand)
    }

    fun addShotEntries(matchSpeakerShotData: MatchSpeakerShotsDataContainer) {
        for (shot in matchSpeakerShotData.speakerShots) {
            val insertCommand = generateInsertShotCommand(shot)
            database.execSQL(insertCommand)
        }
    }

    fun close() {
        database.close()
    }

    private fun generateInsertMatchCommand(matchData: MatchDataContainer): String {
        return String.format(ADD_MATCH_ENTRY_FORMAT,
            matchData.infoData.scoutName,
            matchData.infoData.matchNumber,
            matchData.infoData.teamNumber,
            if (matchData.autonData.leftWing) 1 else 0,
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
            if (matchData.teleopData.parked) 1 else 0,
            if (matchData.teleopData.onstage) 1 else 0,
            if (matchData.teleopData.spotlight) 1 else 0,
            if (matchData.teleopData.harmony) 1 else 0,
            if (matchData.infoData.breakdown) 1 else 0,
            matchData.infoData.notes)
    }

    private fun generateInsertShotCommand(shotData: SpeakerShotDataContainer): String {
        return String.format(ADD_SHOT_ENTRY_FORMAT,
            shotData.matchNumber,
            shotData.teamNumber,
            shotData.shotX,
            shotData.shotY,
            if (shotData.scored) 1 else 0)
    }
}