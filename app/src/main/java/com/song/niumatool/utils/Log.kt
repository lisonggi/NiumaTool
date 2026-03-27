package com.song.niumatool.utils

import android.util.Log

private val TAG = "MyApp"

class LoggerWrapper(val msg: String) {
    fun info() = Log.i(TAG, msg)
    fun debug() = Log.d(TAG, msg)
    fun warn() = Log.w(TAG, msg)
    fun error() = Log.e(TAG, msg)
}

val String.log: LoggerWrapper
    get() = LoggerWrapper(this)
val Long.log: LoggerWrapper
    get() = LoggerWrapper(this.toString())
val Int.log: LoggerWrapper
    get() = LoggerWrapper(this.toString())
val Any.log: LoggerWrapper
    get() = LoggerWrapper(this.toString())
val Float.log: LoggerWrapper
    get() = LoggerWrapper(this.toString())