package org.skylinerobotics.skyscout.data.datacontainer

data class SpeakerShotDataContainer(
    var matchNumber: Int,
    var teamNumber: Int,
    val shotX: Float,
    val shotY: Float,
    val scored: Boolean
) : DataContainer {}