package com.base.commonmodule.extend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Parcelable
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

/**
 * 扩展函数
 * @author jin
 * @date 2019/4/28
 */

/**
 * 显示fragment,会隐藏之前显示的fragment
 */
fun FragmentManager.show(@IdRes resId: Int, fragment: Fragment) {
    val currentFragment = findCurrentShowFragment(resId)
    if (currentFragment != fragment) {
        val transaction = beginTransaction()
        if (currentFragment != null && currentFragment.isAdded) {
            transaction.hide(currentFragment)
        }
        if (!fragment.isAdded) {
            transaction.add(resId, fragment)
        }
        transaction.show(fragment).commit()
    }
}

/**
 * 获取当前显示fragment
 */
fun AppCompatActivity.findCurrentShowFragment(@IdRes resId: Int): Fragment? {
    return supportFragmentManager.findCurrentShowFragment(resId)
}

/**
 * 获取当前显示fragment
 */
fun FragmentManager.findCurrentShowFragment(@IdRes resId: Int): Fragment? {
    if (fragments.isNotEmpty()) {
        fragments.forEach {
            if (it.id == resId && !it.isHidden) {
                return it
            }
        }
    }
    return null
}

/**
 * 清空fragment
 * @param resId 要清空的layoutId
 */
fun FragmentManager.clearFragments(@IdRes resId: Int, fristFragment: Fragment? = null) {
    if (fragments.isNotEmpty()) {
        val transaction = beginTransaction()
        val iterator = fragments.iterator()
        while (iterator.hasNext()) {
            val fragment = iterator.next()
            if ((fristFragment == null || fristFragment != fragment) && resId == fragment.id) {
                if (fragment.isAdded) {
                    transaction.remove(fragment)
                }
                iterator.remove()
            }
        }
        transaction.commit()
    }

}

/**
 * 启动Activity
 * @param params 参数
 */
inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any?>) {
    if (params.isNotEmpty()) {
        val intent = Intent(this, T::class.java)
        intent.fillIntentArguments(params)
        startActivity(intent)
    } else {
        startActivity(Intent(this, T::class.java))
    }
}

/**
 * 启动Activity
 * @param params 参数
 * @param T Activity以及子类
 */
inline fun <reified T : Activity> Context.startActivity(flag: Int, vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    intent.flags = flag
    if (params.isNotEmpty()) {
        intent.fillIntentArguments(params)
    }
    startActivity(intent)
}

/**
 * 启动Activity
 * @param params 参数
 */
inline fun <reified T : Activity> Activity.startActivity(vararg params: Pair<String, Any?>) {
    if (params.isNotEmpty()) {
        val intent = Intent(this.applicationContext, T::class.java)
        intent.fillIntentArguments(params)
        startActivity(intent)
    } else {
        startActivity(Intent(this.applicationContext, T::class.java))
    }
}

/**
 * 启动activity
 * @param bundle 参数
 */
inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle) {
    val intent = Intent(this.applicationContext, T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}

/**
 * 启动Activity，并返回结果
 * @param params 参数
 * @param requestCode 请求的code值
 */
inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) {
    if (params.isNotEmpty()) {
        val intent = Intent(this.applicationContext, T::class.java)
        intent.fillIntentArguments(params)
        startActivityForResult(intent, requestCode)
    } else {
        startActivityForResult(Intent(this.applicationContext, T::class.java), requestCode)
    }
}

/**
 * 启动Activity，并返回结果
 * @param bundle 参数
 * @param requestCode 请求的code值
 */
inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int, bundle: Bundle) {
    val intent = Intent(this.applicationContext, T::class.java)
    intent.putExtras(bundle)
    startActivityForResult(intent, requestCode)
}


/**
 * 启动Activity
 * @param params 参数
 */
inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) {
    if (params.isNotEmpty()) {
        val intent = Intent(this.context, T::class.java)
        intent.fillIntentArguments(params)
        startActivity(intent)
    } else {
        startActivity(Intent(this.context, T::class.java))
    }
}

/**
 * 启动Activity
 * @param bundle 参数
 */
inline fun <reified T : Activity> Fragment.startActivity(bundle: Bundle) {
    val intent = Intent(this.context, T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}

/**
 * 启动Activity，并返回结果
 * @param requestCode 请求code值
 * @param params 参数
 */
inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) {
    if (params.isNotEmpty()) {
        val intent = Intent(this.context, T::class.java)
        intent.fillIntentArguments(params)
        startActivityForResult(intent, requestCode)
    } else {
        startActivityForResult(Intent(this.context, T::class.java), requestCode)
    }
}

/**
 * 启动Activity，并返回结果
 * @param requestCode 请求code值
 * @param bundle 参数
 */
inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, bundle: Bundle) {
    val intent = Intent(this.context, T::class.java)
    intent.putExtras(bundle)
    startActivityForResult(intent, requestCode)
}

/**
 * 初始化Bundle
 */
fun mutableBundle(vararg params: Pair<String, Any?>): Bundle {
    val bundle = Bundle()
    bundle.fillArguments(params)
    return bundle
}

/**
 * 把可变参数变成bundle对象
 */
fun Bundle.fillArguments(params: Array<out Pair<String, Any?>>) {
    params.forEach {
        val value = it.second
        when (value) {
            null -> this.putSerializable(it.first, null as Serializable?)
            is Int -> this.putInt(it.first, value)
            is Long -> this.putLong(it.first, value)
            is CharSequence -> this.putCharSequence(it.first, value)
            is String -> this.putString(it.first, value)
            is Float -> this.putFloat(it.first, value)
            is Double -> this.putDouble(it.first, value)
            is Char -> this.putChar(it.first, value)
            is Short -> this.putShort(it.first, value)
            is Boolean -> this.putBoolean(it.first, value)
            is java.io.Serializable -> this.putSerializable(it.first, value)
            is Bundle -> this.putBundle(it.first, value)
            is Parcelable -> this.putParcelable(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> this.putCharSequenceArray(it.first, value as Array<out CharSequence>)
                value.isArrayOf<String>() -> this.putStringArray(it.first, value as Array<out String>)
                value.isArrayOf<Parcelable>() -> this.putParcelableArray(it.first, value as Array<out Parcelable>)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> this.putIntArray(it.first, value)
            is LongArray -> this.putLongArray(it.first, value)
            is FloatArray -> this.putFloatArray(it.first, value)
            is DoubleArray -> this.putDoubleArray(it.first, value)
            is CharArray -> this.putCharArray(it.first, value)
            is ShortArray -> this.putShortArray(it.first, value)
            is BooleanArray -> this.putBooleanArray(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}

/**
 * 传递可变参数的bundle
 */
fun Fragment.fillVarargArguments(vararg params: Pair<String, Any?>) {
    val bundle = Bundle()
    bundle.fillArguments(params)
    arguments = bundle
}

/**
 * 传递数组参数
 */
fun Fragment.fillArguments(params: Array<out Pair<String, Any?>>) {
    val bundle = Bundle()
    bundle.fillArguments(params)
    arguments = bundle
}

/**
 * 把传递的参数赋值给Intent
 */
fun Intent.fillIntentArguments(params: Array<out Pair<String, Any?>>) {
    params.forEach {
        val value = it.second
        when (value) {
            null -> this.putExtra(it.first, null as Serializable?)
            is Int -> this.putExtra(it.first, value)
            is Long -> this.putExtra(it.first, value)
            is CharSequence -> this.putExtra(it.first, value)
            is String -> this.putExtra(it.first, value)
            is Float -> this.putExtra(it.first, value)
            is Double -> this.putExtra(it.first, value)
            is Char -> this.putExtra(it.first, value)
            is Short -> this.putExtra(it.first, value)
            is Boolean -> this.putExtra(it.first, value)
            is java.io.Serializable -> this.putExtra(it.first, value)
            is Bundle -> this.putExtra(it.first, value)
            is Parcelable -> this.putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> this.putExtra(it.first, value)
                value.isArrayOf<String>() -> this.putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> this.putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> this.putExtra(it.first, value)
            is LongArray -> this.putExtra(it.first, value)
            is FloatArray -> this.putExtra(it.first, value)
            is DoubleArray -> this.putExtra(it.first, value)
            is CharArray -> this.putExtra(it.first, value)
            is ShortArray -> this.putExtra(it.first, value)
            is BooleanArray -> this.putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}

/**
 * 设置返回结果
 * @param params 参数
 */
fun Activity.setResult(vararg params: Pair<String, Any?>) {
    if (params.isNotEmpty()) {
        val intent = Intent()
        intent.fillIntentArguments(params)
        setResult(Activity.RESULT_OK, intent)
    } else
        setResult(Activity.RESULT_OK)
}

/**
 * 设置返回结果
 * @param resultCode 结果码
 * @param params 参数
 */
fun Activity.setResult(resultCode: Int, vararg params: Pair<String, Any?>) {
    if (params.isNotEmpty()) {
        val intent = Intent()
        intent.fillIntentArguments(params)
        setResult(resultCode, intent)
    } else
        setResult(resultCode)
}

fun Context.dp2px(dpVal: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dpVal, resources.displayMetrics)
}

/**
 * 发送EventBus
 * @param what 类型
 * @param obj 数据
 */
fun postEventBus(what: Int, obj: Any? = null) {
    val message = Message.obtain()
    message.what = what
    obj?.let {
        message.obj = obj
    }
    EventBus.getDefault().post(message)
}

/**
 * 发送EventBus
 * @param what 类型
 * @param bundle 数据
 */
fun postEventBus(what: Int, bundle: Bundle) {
    val message = Message.obtain()
    message.what = what
    message.data = bundle
    EventBus.getDefault().post(message)
}

/**
 * 发送EventBus
 * @param what 类型
 * @param params 数据
 */
fun postEventBus(what: Int, vararg params: Pair<String, Any?>) {
    val message = Message.obtain()
    message.what = what
    val bundle = Bundle()
    bundle.fillArguments(params)
    message.data = bundle
    EventBus.getDefault().post(message)
}

/**
 * 关闭键盘
 */
fun Activity.hideSoftInputFromWindow() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun AppCompatActivity.replaceFragment(@IdRes layout: Int, fragment: Fragment) {
    supportFragmentManager
            .beginTransaction()
            .replace(layout, fragment)
            .commit()
}

/**
 * 设置文字阴影
 * @param radius 角度
 * @param dx x轴偏移
 * @param dy y轴偏移
 * @param textViews TextView对象
 */
fun setShadowLayer(radius: Float, dx: Float, dy: Float, @ColorInt color: Int, vararg textViews: TextView) {
    textViews.forEach {
        it.setShadowLayer(radius, dx, dy, color)
    }
}

/**
 * 是否为null
 */
fun Any?.isNull(): Boolean {
    return null == this
}

/**
 * 关闭键盘
 */
fun Fragment.hideSoftInputFromWindow() {
    (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity?.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

inline fun <reified T> JSONObject.formToJson(key: String, obj: Class<T> = T::class.java): T? {
    if (has(key)) {
        when (obj) {
            Integer.TYPE -> return getInt(key) as T
            java.lang.Boolean.TYPE -> return getBoolean(key) as T
            java.lang.Double.TYPE -> return getDouble(key) as T
            String::class.java -> return getString(key) as T
            JSONArray::class.java -> return getJSONArray(key) as T
            JSONObject::class.java -> return getJSONObject(key) as T
            java.lang.Long.TYPE -> return getLong(key) as T
        }
    }
    return null
}

fun String.isUrl(): Boolean {
    return startsWith("http") || startsWith("https")
}

