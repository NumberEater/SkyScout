package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.data.datacontainer.InfoDataContainer


// TODO("Finish this handler")
class InfoDataHandler : DataHandler() {
    val infoDataContainer = InfoDataContainer()
    override fun getDataContainer(): InfoDataContainer {
        return infoDataContainer
    }
}