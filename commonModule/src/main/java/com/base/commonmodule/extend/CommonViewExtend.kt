package com.base.commonmodule.extend

import android.content.res.Resources
import android.view.View
import android.widget.Checkable

/**
 * Created by acorn on 2021/3/24.
 */
// 屏幕宽高
val width get() = Resources.getSystem().displayMetrics.widthPixels
val height get() = Resources.getSystem().displayMetrics.heightPixels

//状态栏高度
val statusBarHeight: Int
    get() {
        val resources = Resources.getSystem()
        val id = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(id)
    }

/**
 * 规定时间内只允许单次点击
 */
inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) { //不阻止实现Checkable(如CheckBox)的View连点
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

/**
 * 规定时间内只允许单次点击
 * 兼容点击事件设置为this的情况
 */
fun <T : View> T.singleClick(onClickListener: View.OnClickListener, time: Long = 800) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) { //不阻止实现Checkable(如CheckBox)的View连点
            lastClickTime = currentTimeMillis
            onClickListener.onClick(this)
        }
    }
}

var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0