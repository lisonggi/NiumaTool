package com.song.niumatool.ui.screens

import androidx.annotation.RequiresPermission
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.song.niumatool.model.Route
import com.song.niumatool.viewmodel.BtViewModel

@Composable
@RequiresPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
fun AppScreen() {
    val btViewModel = viewModel<BtViewModel>()
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
            DeviceScreen(btViewModel) {
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