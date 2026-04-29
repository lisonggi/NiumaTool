package com.song.niumatool.viewmodel

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.song.niumatool.modules.BtManager

class BtViewModel(val btManager: BtManager) : ViewModel() {
    private val _selectedDevice = mutableStateOf<BluetoothDevice?>(null)
    val selectedDevice: State<BluetoothDevice?> get() = _selectedDevice
    fun updateSelectedDevice(bluetoothDevice: BluetoothDevice) {
        _selectedDevice.value = bluetoothDevice
    }

    private val _deviceList = mutableStateOf<List<BluetoothDevice>>(emptyList())
    val deviceList: State<List<BluetoothDevice>> get() = _deviceList

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun reScanDevice() {
        _deviceList.value = btManager.getDevice()
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                BtViewModel(BtManager(application))
            }
        }
    }
}