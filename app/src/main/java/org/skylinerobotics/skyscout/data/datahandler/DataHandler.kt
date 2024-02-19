package org.skylinerobotics.skyscout.data.datahandler

import org.skylinerobotics.skyscout.data.datacontainer.DataContainer

abstract class DataHandler {
    abstract fun getDataContainer(): DataContainer
}