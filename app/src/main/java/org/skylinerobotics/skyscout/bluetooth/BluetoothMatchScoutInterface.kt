package org.skylinerobotics.skyscout.bluetooth

import android.content.Context
import org.skylinerobotics.skyscout.data.datacontainer.MatchDataContainer
import java.util.UUID

class BluetoothMatchScoutInterface(private val context: Context) {
    companion object {
        private const val LOG_TAG = "BluetoothMatchScoutInterface"

        private const val MATCH_SERVICE_UUID_STRING = "1af2f6a7-1a19-4b69-b3e0-bb392ca2997c"
        private const val DEVICE_INFO_UUID_STRING = "f20ee2e4-ff05-4b7a-97c0-ded33c1a26c6"
        private const val TABLET_SCOUT_POSITION_UUID_STRING = "f20ee2e4-ff05-4b7a-97c0-ded33c1a26c6"
    }

    private var serverManager = BluetoothServerManager(context)

    fun startMatchServer() {
        serverManager.startServer()

        val deviceInfoUuid = UUID.fromString(DEVICE_INFO_UUID_STRING)
        val scoutPositionUuid = UUID.fromString(TABLET_SCOUT_POSITION_UUID_STRING)
        serverManager.addPrimaryService(deviceInfoUuid)
        serverManager.addCharacteristic(deviceInfoUuid, scoutPositionUuid)
        serverManager.setCharacteristicValue(
            deviceInfoUuid,
            scoutPositionUuid,
            "BLUE 1".toByteArray()
        )

        serverManager.startAdvertisement()
    }

    // Change server values to new match data
    fun updateServerWithMatch(matchData: MatchDataContainer) {
        return
    }
}