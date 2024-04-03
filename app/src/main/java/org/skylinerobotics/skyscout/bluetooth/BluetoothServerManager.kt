package org.skylinerobotics.skyscout.bluetooth

import android.bluetooth.*
import android.bluetooth.le.*
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import java.util.UUID

class BluetoothServerManager(private val context: Context) {
    companion object {
        private const val LOG_TAG = "BluetoothServerManager"
    }

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val bluetoothLeAdvertiser: BluetoothLeAdvertiser? = bluetoothAdapter?.bluetoothLeAdvertiser
    private var gattServer: BluetoothGattServer? = null
    private var currentAdvertisingSet: AdvertisingSet? = null

    private var isClientConnected = false

    private val gattServerCallback = object : BluetoothGattServerCallback() {
        override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.i(LOG_TAG, "Connected.")
                    isClientConnected = true
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.i(LOG_TAG, "Disconnected.")
                    isClientConnected = false
                }
            }
        }

        override fun onServiceAdded(status: Int, service: BluetoothGattService?) {
            super.onServiceAdded(status, service)
            Log.i(LOG_TAG, "A service was added.")
        }
    }

    private val advertisingParameters = AdvertisingSetParameters.Builder()
        .setLegacyMode(true)
        .setConnectable(true)
        .setScannable(true)
        .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
        .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_HIGH)

    private val advertisingData = AdvertiseData.Builder()
        .setIncludeDeviceName(true)

    private val advertisingCallback = object : AdvertisingSetCallback() {
        override fun onAdvertisingSetStarted(advertisingSet: AdvertisingSet?, txPower: Int, status: Int) {
            super.onAdvertisingSetStarted(advertisingSet, txPower, status)
            if (status == ADVERTISE_SUCCESS) {
                Log.i(LOG_TAG, "Advertising set started.")
                currentAdvertisingSet = advertisingSet
            } else {
                Log.e(LOG_TAG, "Advertising set failed.")
            }
        }
    }

    fun startServer() {
        if (gattServer == null) {
            gattServer = bluetoothManager.openGattServer(context, gattServerCallback)
        }
    }

    fun startAdvertisement() {
        bluetoothLeAdvertiser?.startAdvertisingSet(
            advertisingParameters.build(),
            advertisingData.build(),
            null, null, null,
            advertisingCallback
        )
    }

    fun addPrimaryService(uuid: UUID) {
        val service = BluetoothGattService(uuid, BluetoothGattService.SERVICE_TYPE_PRIMARY)
        gattServer?.addService(service)
    }

    fun addCharacteristic(serviceUuid: UUID, characteristicUuid: UUID) {
        val characteristic = BluetoothGattCharacteristic(
            characteristicUuid,
            BluetoothGattCharacteristic.FORMAT_UINT32,
            BluetoothGattCharacteristic.PERMISSION_READ
        )
        gattServer?.getService(serviceUuid)?.addCharacteristic(characteristic)
    }

    fun setCharacteristicValue(serviceUuid: UUID, characteristicUuid: UUID, value: ByteArray) {
        val service = gattServer?.getService(serviceUuid)
        val characteristic = service?.getCharacteristic(characteristicUuid)
        characteristic?.setValue(value)
    }

    fun addAdvertisementService(uuid: UUID) {
        advertisingData.addServiceUuid(ParcelUuid(uuid))
    }

    fun isConnected(): Boolean {
        return isClientConnected
    }
}