package org.skylinerobotics.skyscout.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class BluetoothAvailabilityManager(private val activity: AppCompatActivity) {

    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { result ->
            for (key in result.keys) {
                if (result[key] == true) {
                    Log.i("Bluetooth", "Granted Permission To: $key")
                } else {
                    Log.i("Bluetooth", "Denied Permission To: $key")
                }
            }
        }

    private val enableBluetoothLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.i("Bluetooth", "Bluetooth Enabled.")
        } else {
            Log.i("Bluetooth", "Bluetooth Enable Denied.")
        }
    }

    fun enableBluetoothIfDisabled() {
        if (!isBluetoothEnabled()) {
            enableBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
    }

    // Has bluetooth feature, bluetooth is enabled, and no permissions are denied
    fun isBluetoothReady(): Boolean {
        return hasBluetoothFeature() && isBluetoothEnabled() && getDeniedPermissions().isEmpty()
    }

    // Request all permissions we don't have
    fun requestDeniedPermissions() {
        requestPermissionLauncher.launch(getDeniedPermissions())
    }

    // Does device have BLE functionality
    private fun hasBluetoothFeature(): Boolean {
        return activity.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    // Does user have bluetooth turned on
    private fun isBluetoothEnabled(): Boolean {
        return activity.getSystemService(BluetoothManager::class.java).adapter.isEnabled
    }

    private fun getDeniedPermissions(): Array<String> {
        val output = mutableListOf<String>()
        for (permission in getBluetoothPermissionsArray()) {
            if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                output.add(permission)
            }
        }
        return output.toTypedArray()
    }

    private fun getBluetoothPermissionsArray(): Array<String> {
        // Legacy bluetooth support for the super old tablets smh
        // I just love amazon so much
        // How is it possible to make such a great system
        return if (Build.VERSION.SDK_INT < 31) {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )
        }
    }
}