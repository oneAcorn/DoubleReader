package com.base.commonmodule.base

import android.content.Context
import android.graphics.Typeface
import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.base.commonmodule.BuildConfig
import com.tencent.mmkv.MMKV

/**
 * Created by acorn on 2020/6/18.
 */
open class BaseApplication : MultiDexApplication() {
    companion object {
        lateinit var appContext: Context
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        appContext = base
    }

    override fun onCreate() {
        super.onCreate()
        setupStrictMode()
        //初始化缓存工具
        MMKV.initialize(this)
    }

    /**
     * 在Debug模式检查各种问题，包括内存泄漏，以log形式提示
     */
    private fun setupStrictMode() {
        if (!BuildConfig.DEBUG) return
        val builder = StrictMode.VmPolicy.Builder()
        builder
            .detectActivityLeaks()
            .detectLeakedClosableObjects()
            .detectLeakedRegistrationObjects()
            .detectLeakedSqlLiteObjects()
        builder.penaltyLog()
        StrictMode.setVmPolicy(builder.build())
    }
}