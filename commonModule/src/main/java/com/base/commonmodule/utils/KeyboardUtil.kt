package com.base.commonmodule.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 键盘工具
 * Created by acorn on 2020/5/12.
 */

/**
 * 隐藏键盘
 */
fun Activity.hideKeyboard() {
    val keyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboardManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

/**
 * 展示键盘
 */
fun EditText.showKeyboard() {
    requestFocus()
    val keyboardManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboardManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}