package com.example.mvppetproject.helpers

import android.util.Log
import com.example.mvppetproject.BuildConfig

fun Any.log(message: String?) {
    if (BuildConfig.DEBUG) {
        Log.e(this::class.java.simpleName, message ?: "log empty data!!!")
    }
}