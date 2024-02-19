package org.skylinerobotics.skyscout.data.datacontainer

data class AutonDataContainer(
    var leftWing: Boolean = false,
    var ampNotesAttempted: Int = 0,
    var ampNotesScored: Int = 0,
    var speakerNotesAttempted: Int = 0,
    var speakerNotesScored: Int = 0
) : DataContainer {}