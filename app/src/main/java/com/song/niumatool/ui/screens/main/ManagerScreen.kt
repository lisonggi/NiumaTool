package com.song.niumatool.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.song.niumatool.utils.textToImageBitmap

@Composable
fun ManagerScreen() {
    Box(){
        Image(textToImageBitmap("123aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",50f),"123")
    }
}