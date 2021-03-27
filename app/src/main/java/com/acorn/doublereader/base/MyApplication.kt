package com.acorn.doublereader.base

import com.acorn.doublereader.greendao.DbManager
import com.base.commonmodule.base.BaseApplication

/**
 * Created by acorn on 2021/3/26.
 */
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        DbManager.instance.init(this)
    }
}