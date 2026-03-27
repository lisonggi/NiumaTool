package com.song.niumatool.viewmodel

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BtViewModel : ViewModel() {
    private val _selectedDevice = MutableStateFlow<BluetoothDevice?>(null)
    val selectDevice = _selectedDevice.asStateFlow()

    fun selectDevice(device: BluetoothDevice) {
        _selectedDevice.value = device
    }
}