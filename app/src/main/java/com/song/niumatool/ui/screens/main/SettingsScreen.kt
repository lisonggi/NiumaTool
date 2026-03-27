package com.song.niumatool.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.song.niumatool.compose.MySurface
import com.song.niumatool.model.FunctionMenu
import com.song.niumatool.model.Route

private val functionMenuList = listOf(
    FunctionMenu("选择设备", Route.SelectDeviceScreen),
    FunctionMenu("纸张设置", Route.PaperSettingsScreen)
)

@Composable
fun SettingsScreen(navController: NavHostController) {
    MySurface(modifier = Modifier.padding(horizontal = 10.dp)) {
        LazyColumn {
            items(functionMenuList) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable() {
                            navController.navigate(it.route)
                        }
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(it.label)
                }
            }
        }
    }
}