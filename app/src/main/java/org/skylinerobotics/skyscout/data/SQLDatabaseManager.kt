package org.skylinerobotics.skyscout.data

interface SQLDatabaseManager {
    fun close()
    fun addEntry()
}