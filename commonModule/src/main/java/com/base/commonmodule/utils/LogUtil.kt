package com.base.commonmodule.utils

import android.util.Log
import com.base.commonmodule.BuildConfig

/**
 * Created by acorn on 2020/5/3.
 */
fun logI(str: String) {
    if (BuildConfig.DEBUG)
        Log.i("acornTag", str)
}

fun logE(str: String) {
    if (BuildConfig.DEBUG)
        Log.e("acornTag", str)
}

fun logE(e: Throwable) {
    if (BuildConfig.DEBUG)
        Log.e("acornTag", "err", e)
}