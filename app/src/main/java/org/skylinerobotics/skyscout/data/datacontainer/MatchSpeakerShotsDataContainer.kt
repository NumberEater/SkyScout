package org.skylinerobotics.skyscout.data.datacontainer

data class MatchSpeakerShotsDataContainer(
    val speakerShots: MutableList<SpeakerShotDataContainer> = mutableListOf()
) : DataContainer {}