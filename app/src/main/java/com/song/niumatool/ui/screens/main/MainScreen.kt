package com.song.niumatool.ui.screens.main

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.song.niumatool.model.Route
import com.song.niumatool.viewmodel.BtViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
@Composable
fun MainScreen(navController: NavHostController, btViewModel: BtViewModel) {
    val mainNavController = rememberNavController()
    val currentDestination by mainNavController.currentBackStackEntryAsState()
    val bottomItems = remember {
        listOf(
            Triple(Icons.Default.Home, "主页", Route.MainScreen.Home),
            Triple(Icons.Default.Build, "管理", Route.MainScreen.Manager),
            Triple(Icons.Default.Settings, "设置", Route.MainScreen.Settings)
        )
    }
    val label = bottomItems.firstOrNull {
        currentDestination?.destination?.hasRoute(it.third::class) == true
    }?.second ?: ""

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(label) },
        )
    }, bottomBar = {
        NavigationBar {
            bottomItems.forEach { item ->
                val isCurrent = currentDestination?.destination?.hasRoute(item.third::class) == true
                NavigationBarItem(selected = isCurrent, onClick = {
                    if (isCurrent) {
                        return@NavigationBarItem
                    }
                    mainNavController.navigate(item.third) {
                        popUpTo(mainNavController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }, icon = {
                    Icon(item.first, contentDescription = null)
                }, label = { Text(item.second) })
            }
        }
    }) { padding ->
        NavHost(
            navController = mainNavController,
            startDestination = Route.MainScreen.Home,
            modifier = Modifier.padding(padding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }) {
            composable<Route.MainScreen.Home> {
                HomeScreen(btViewModel) {
                    navController.navigate(it)
                }
            }
            composable<Route.MainScreen.Manager> {
                ManagerScreen()
            }
            composable<Route.MainScreen.Settings> {
                SettingsScreen {
                    navController.navigate(it)
                }
            }
        }
    }
}