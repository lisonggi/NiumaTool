package com.song.niumatool.ui.screens

import android.Manifest
import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.song.niumatool.compose.MySurface
import com.song.niumatool.compose.TitleBar
import com.song.niumatool.viewmodel.BtViewModel

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
@Composable
fun DeviceScreen(
    btViewModel: BtViewModel,
    onSelectedDevice: (bluetoothDevice: BluetoothDevice) -> Unit,
    onBack: () -> Unit
) {
    btViewModel.reScanDevice()
    val selectDevice by remember { btViewModel.selectedDevice }
    val deviceList by remember { btViewModel.deviceList }

    TitleBar(label = "选择设备", onBack = onBack, actions = {
        IconButton(
            modifier = Modifier.padding(end = 10.dp),
            onClick = {
                btViewModel.reScanDevice()
            }) {
            Icon(Icons.Default.Refresh, "scanner")
        }
    }) {
        MySurface(modifier = Modifier.padding(horizontal = 10.dp)) {
            if (deviceList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.Center // 上下左右居中
                ) {
                    Text(
                        text = "没有设备",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn {
                    items(deviceList) { device ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectedDevice(device) }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // 左边设备名称和地址
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = device.name ?: "未知设备",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = device.address,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }

                            // 右边选择标记
                            if (selectDevice?.address == device.address) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "已选择",
                                    tint = MaterialTheme.colorScheme.primary // 绿色
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}