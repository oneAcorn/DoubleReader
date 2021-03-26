package com.base.commonmodule.extend

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import androidx.fragment.app.Fragment

/**
 * 深色模式工具类(android Q以上新增模式)
 * Created by acorn on 2021/3/26.
 */

/**
 * 当前是否为深色(夜间)模式
 */
fun Activity.isNightMode(): Boolean {
    return resources.isNightMode()
}

/**
 * 当前是否为深色(夜间)模式
 */
fun Fragment.isNightMode():Boolean{
    return resources.isNightMode()
}

/**
 * 当前是否为深色(夜间)模式
 */
private fun Resources.isNightMode(): Boolean {
    var res = false
    when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) { // Night mode is not active, we're using the light theme
        Configuration.UI_MODE_NIGHT_NO -> {
            res = false
        }
        Configuration.UI_MODE_NIGHT_YES -> { // Night mode is active, we're using dark theme
            res = true
        }
    }
    return res
}