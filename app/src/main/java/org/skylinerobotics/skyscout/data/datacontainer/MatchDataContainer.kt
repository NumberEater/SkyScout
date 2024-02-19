package org.skylinerobotics.skyscout.data.datacontainer

data class MatchDataContainer(
    val autonData: AutonDataContainer,
    val teleopData: TeleopDataContainer,
    val infoData: InfoDataContainer
) : DataContainer {}