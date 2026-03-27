package com.song.niumatool.modules

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.annotation.RequiresPermission

class AppBluetoothManager(private val context: Context) {
    val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getDevice(): Set<BluetoothDevice> {
        return bluetoothAdapter.bondedDevices
    }

}