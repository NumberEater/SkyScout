package org.skylinerobotics.skyscout.data.datacontainer

data class PitDataContainer(
    val teamNumber: Int,
    val length: Int,
    val width: Int,
    val height: Int,
    val canDoAmp: Boolean,
    val canDoSpeaker: Boolean,
    val canDoTrap: Boolean,
    val canGroundIntake: Boolean,
    val canSourceIntake: Boolean,
    val shooterType: String,
    val drivetrainType: String,
    val canClimb: Boolean,
    val canOffense: Boolean,
    val canDefense: Boolean
) : DataContainer {}
