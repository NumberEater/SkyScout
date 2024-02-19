package org.skylinerobotics.skyscout.data.datacontainer

data class InfoDataContainer(
    var name: String = "",
    var matchNumber: Int = 0,
    var teamNumber: Int = 0,
    var breakdown: Boolean = false,
    var notes: String = ""
) : DataContainer {}