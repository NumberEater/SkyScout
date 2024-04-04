package org.skylinerobotics.skyscout.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.skylinerobotics.skyscout.data.datacontainer.MatchDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.MatchSpeakerShotsDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.PitDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.SpeakerShotDataContainer

class ScoutDatabase(context: Context) {
    private val database: SQLiteDatabase

    companion object {
        private const val CREATE_MAIN_TABLE = """
            CREATE TABLE IF NOT EXISTS MatchData(
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
                NotesShuttled INTEGER,
                TrapNotesAttempted INTEGER,
                TrapNotesScored INTEGER,
                Park BOOLEAN,
                Onstage BOOLEAN,
                Spotlight BOOLEAN,
                Harmony BOOLEAN,
                Breakdown BOOLEAN,
                DefenseDescription VARCHAR,
                Notes VARCHAR)"""

        private const val ADD_MATCH_ENTRY_FORMAT = """
            INSERT INTO MatchData VALUES(
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
                "%s",
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
                %f,
                %f,
                %d)
        """

        private const val CREATE_PIT_TABLE = """
            CREATE TABLE IF NOT EXISTS PitData(
                TeamNumber INTEGER,
                CanDriveUnderStage BOOLEAN,
                Amp BOOLEAN,
                Speaker BOOLEAN,
                Trap BOOLEAN,
                GroundIntake BOOLEAN,
                SourceIntake BOOLEAN,
                ShooterType VARCHAR,
                DrivetrainType VARCHAR,
                Climb BOOLEAN,
                CanHarmonize BOOLEAN,
                Notes VARCHAR
            )
        """

        private const val ADD_PIT_ENTRY_FORMAT = """
            INSERT INTO PitData VALUES(
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                %d,
                "%s",
                "%s",
                %d,
                %d,
                "%s"
            )
        """
    }

    init {
        database = context.openOrCreateDatabase("match_data.db", Context.MODE_PRIVATE, null)

        database.execSQL(CREATE_MAIN_TABLE)
        database.execSQL(CREATE_SHOTS_TABLE)
        database.execSQL(CREATE_PIT_TABLE)
    }

    fun addMatchEntry(matchData: MatchDataContainer) {
        val insertCommand = generateInsertMatchCommand(matchData)
        database.execSQL(insertCommand)
    }

    fun addPitEntry(pitData: PitDataContainer) {
        val insertCommand = generateInsertPitCommand(pitData)
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
            matchData.teleopData.notesShuttled,
            matchData.teleopData.trapNotesAttempted,
            matchData.teleopData.trapNotesScored,
            if (matchData.teleopData.parked) 1 else 0,
            if (matchData.teleopData.onstage) 1 else 0,
            if (matchData.teleopData.spotlight) 1 else 0,
            if (matchData.teleopData.harmony) 1 else 0,
            if (matchData.infoData.breakdown) 1 else 0,
            matchData.infoData.defenseDescription,
            matchData.infoData.notes)
    }

    private fun generateInsertPitCommand(pitData: PitDataContainer): String {
        return String.format(ADD_PIT_ENTRY_FORMAT,
            pitData.teamNumber,
            if (pitData.canDriveUnderStage) 1 else 0,
            if (pitData.canDoAmp) 1 else 0,
            if (pitData.canDoSpeaker) 1 else 0,
            if (pitData.canDoTrap) 1 else 0,
            if (pitData.canGroundIntake) 1 else 0,
            if (pitData.canSourceIntake) 1 else 0,
            pitData.shooterType,
            pitData.drivetrainType,
            if (pitData.canClimb) 1 else 0,
            if (pitData.canHarmonize) 1 else 0,
            pitData.notes
        )
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