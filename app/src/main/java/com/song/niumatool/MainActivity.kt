package com.song.niumatool

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.song.niumatool.ui.screens.AppScreen
import com.song.niumatool.ui.theme.NiumaToolTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    @RequiresPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        )
        enableEdgeToEdge()
        setContent {
            NiumaToolTheme {
                AppScreen()
            }
        }
    }


    val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions: Map<String, Boolean> ->
        var granted = true
        permissions.entries.forEach {
            if (!it.value && !shouldShowRequestPermissionRationale(it.key)) {
                granted = false
            }
        }
        if (!granted) {
            Toast.makeText(this, "权限已被拒绝 请允许使用 附近的设备", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}