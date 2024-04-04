package org.skylinerobotics.skyscout.data.datacontainer

data class InfoDataContainer(
    var scoutName: String = "",
    var matchNumber: Int = 0,
    var teamNumber: Int = 0,
    var breakdown: Boolean = false,
    var defenseDescription: String = "",
    var notes: String = ""
) : DataContainer {}