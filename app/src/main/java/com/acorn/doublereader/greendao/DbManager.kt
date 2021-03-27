package com.acorn.doublereader.greendao

import android.content.Context
import com.acorn.doublereader.greendao.generate.DaoMaster
import com.acorn.doublereader.greendao.generate.DaoSession

/**
 * Created by acorn on 2021/3/27.
 */
class DbManager {
    lateinit var session: DaoSession
        private set

    companion object {
        val instance: DbManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DbManager() }
    }

    fun init(context: Context) {
        val devOpenHelper = DaoMaster.DevOpenHelper(context, "doubleReader.db")
        val db = devOpenHelper.writableDb
        //创建数据库连接
        val daoMaster = DaoMaster(db)
        session = daoMaster.newSession()
    }
}