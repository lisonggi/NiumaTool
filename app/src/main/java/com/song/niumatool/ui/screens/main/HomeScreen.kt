package com.song.niumatool.ui.screens.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.song.niumatool.R
import com.song.niumatool.compose.MySurface
import com.song.niumatool.model.Route
import com.song.niumatool.utils.wrapMixed
import com.song.niumatool.viewmodel.BtViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun createPrinter(): EscPosPrinter? {
    val connection = BluetoothPrintersConnections.selectFirstPaired()
        ?: return null

    return try {
        EscPosPrinter(
            connection,
            203,
            40f,
            32, // 不要用 8，后面解释
            EscPosCharsetEncoding("GB2312", 16)
        )
    } catch (e: Exception) {
        null
    }
}
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
@Composable
fun HomeScreen(btViewModel: BtViewModel, onNavigate: (route: Route) -> Unit) {
    val selectDevice by btViewModel.selectedDevice
    val isConnect by mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")
        return now.format(formatter)
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MySurface(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            if (selectDevice == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNavigate(Route.SelectDeviceScreen)
                        }
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "未选择设备")
                }

            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = selectDevice!!.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = selectDevice!!.address,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    if (isConnect) {
                        Icon(
                            painter = painterResource(R.drawable.bluetooth_connected_24px),
                            contentDescription = "已连接",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.bluetooth_disabled_24px),
                            contentDescription = "未连接",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        var addressValue by remember { mutableStateOf("") }
        var codeValue by remember { mutableStateOf("") }
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = addressValue,
                onValueChange = { input ->
                    addressValue = input
                },
                label = { Text("收货地址") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = codeValue,
                onValueChange = { input ->
                    codeValue = input.filter { it.isDigit() }
                },
                label = { Text("取件码") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button({
                    val text = "[L]"+"收货地址:${addressValue}".wrapMixed(32)+"\n"+
                            "[L]"+ "取件码:${codeValue}".wrapMixed(32).trimIndent()
                    createPrinter()?.printFormattedText(
                        text
                    )
                }) {
                    Text("打印")
                }
            }
        }

    }

}