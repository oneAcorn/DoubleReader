package com.base.commonmodule.utils.cache

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 委托模式实现的缓存
 * Created by acorn on 2020/6/9.
 */
@Suppress("UNCHECKED_CAST")
class MMKVDelegate<T>(private val mmkv: MMKV, private val key: String, private val clazz: Class<T>) : ReadWriteProperty<Any?, T?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return when {
            clazz == Int::class.java -> {
                mmkv.decodeInt(key) as? T
            }
            clazz == String::class.java -> {
                mmkv.decodeString(key) as? T
            }
            clazz == Long::class.java -> {
                mmkv.decodeLong(key) as? T
            }
            clazz == Boolean::class.java -> {
                mmkv.decodeBool(key) as? T
            }
            clazz == Float::class.java -> {
                mmkv.decodeFloat(key) as? T
            }
            clazz == Double::class.java -> {
                mmkv.decodeDouble(key) as? T
            }
            isImplementInterface(clazz, Parcelable::class.java) -> {
                val pClazz = clazz as Class<out Parcelable>
                mmkv.decodeParcelable(key, pClazz) as? T
            }
            else -> null
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value == null) {
            mmkv.removeValueForKey(key)
            return
        }
        when {
            clazz == Int::class.java -> {
                mmkv.encode(key, value as Int)
            }
            clazz == String::class.java -> {
                mmkv.encode(key, value as String)
            }
            clazz == Long::class.java -> {
                mmkv.encode(key, value as Long)
            }
            clazz == Boolean::class.java -> {
                mmkv.encode(key, value as Boolean)
            }
            clazz == Float::class.java -> {
                mmkv.encode(key, value as Float)
            }
            clazz == Double::class.java -> {
                mmkv.encode(key, value as Double)
            }
            isImplementInterface(clazz, Parcelable::class.java) -> {
                mmkv.encode(key, value as Parcelable)
            }
            else -> {
                throw IllegalArgumentException("unsupport value")
            }
        }
    }
}