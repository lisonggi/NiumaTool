package com.song.niumatool.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object LoginScreen: Route()
    @Serializable
    open class MainScreen : Route() {
        @Serializable
        data object Home:MainScreen()

        @Serializable
        data object Manager:MainScreen()

        @Serializable
        data object Settings:MainScreen()

    }

    @Serializable
    data object SelectDeviceScreen: Route()
    @Serializable
    data object PaperSettingsScreen: Route()
}