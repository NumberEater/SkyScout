package org.skylinerobotics.skyscout.data.datacontainer


data class TeleopDataContainer(
    var ampNotesAttempted: Int = 0,
    var ampNotesScored: Int = 0,
    var speakerNotesAttempted: Int = 0,
    var speakerNotesScored: Int = 0,
    var trapNotesAttempted: Int = 0,
    var trapNotesScored: Int = 0,
    var parked: Boolean = false,
    var onstage: Boolean = false,
    var spotlight: Boolean = false,
    var harmony: Boolean = false
) : DataContainer {}
