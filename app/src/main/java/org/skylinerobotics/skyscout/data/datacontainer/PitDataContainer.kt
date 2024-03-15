package org.skylinerobotics.skyscout.data.datacontainer

data class PitDataContainer(
    val teamNumber: Int,
    val canDriveUnderStage: Boolean,
    val canDoAmp: Boolean,
    val canDoSpeaker: Boolean,
    val canDoTrap: Boolean,
    val canGroundIntake: Boolean,
    val canSourceIntake: Boolean,
    val shooterType: String,
    val drivetrainType: String,
    val canClimb: Boolean,
    val canHarmonize: Boolean,
    val notes: String
) : DataContainer {}
