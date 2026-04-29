package com.song.niumatool.ui.screens

import android.Manifest
import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresPermission
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.song.niumatool.model.Route
import com.song.niumatool.ui.screens.main.MainScreen
import com.song.niumatool.viewmodel.BtViewModel

@Composable
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun AppScreen(
    btViewModel: BtViewModel,
    onSelectedDevice: (bluetoothDevice: BluetoothDevice) -> Unit,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.MainScreen(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable<Route.MainScreen> {
            MainScreen(navController, btViewModel)
        }
        composable<Route.SelectDeviceScreen> {
            DeviceScreen(btViewModel, {
                onSelectedDevice(it)
            }) {
                navController.popBackStack()
            }
        }
        composable<Route.PaperSettingsScreen> {
            PaperSettingsScreen() {
                navController.popBackStack()
            }
        }
    }
}