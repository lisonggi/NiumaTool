package com.song.niumatool.ui.screens.main

import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.song.niumatool.compose.MySurface
import com.song.niumatool.viewmodel.BtViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
@Composable
fun HomeScreen(btViewModel: BtViewModel) {
    val selectDevice by btViewModel.selectDevice.collectAsState()
    val isConnect = false
    Column {
        MySurface(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 30.dp)
        ) {
            Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                if (selectDevice == null) {
                    Text("未选择设备")
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            Text(selectDevice!!.name)
                            Text(selectDevice!!.address)
                        }
                        if (isConnect) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "已连接",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "未连接",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}