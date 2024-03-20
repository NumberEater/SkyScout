package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.data.datacontainer.DataContainer
import org.skylinerobotics.skyscout.data.datacontainer.MatchSpeakerShotsDataContainer
import org.skylinerobotics.skyscout.data.datacontainer.SpeakerShotDataContainer

class SpeakerShotDataHandler : DataHandler() {
    private val matchSpeakerShots = MatchSpeakerShotsDataContainer()

    fun addShot(x: Float, y: Float, scored: Boolean) {
        matchSpeakerShots.speakerShots.add(
            SpeakerShotDataContainer(0, 0, x, y, scored)
        )
    }

    fun setShotsTeamNumber(teamNumber: Int) {
        for (shot in matchSpeakerShots.speakerShots) {
            shot.teamNumber = teamNumber
        }
    }

    fun setShotsMatchNumber(matchNumber: Int) {
        for (shot in matchSpeakerShots.speakerShots) {
            shot.matchNumber = matchNumber
        }
    }

    fun getLastShot(): SpeakerShotDataContainer? {
        return if (matchSpeakerShots.speakerShots.size > 0) {
            matchSpeakerShots.speakerShots.last()
        } else {
            null
        }
    }

    fun undoLastShot() {
        if (matchSpeakerShots.speakerShots.size > 0) {
            matchSpeakerShots.speakerShots.removeLast()
        }
    }

    override fun getDataContainer(): MatchSpeakerShotsDataContainer {
        return matchSpeakerShots
    }
}