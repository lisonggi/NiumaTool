package com.song.niumatool.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    label: String,
    onBack: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(label) }, navigationIcon = {
                IconButton(onClick = {
                    onBack()
                }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "back") }
            }, actions = {
                actions()
            })
        }
    ) { padding ->
        Box(modifier = modifier.padding(padding)) {
            content()
        }
    }
}