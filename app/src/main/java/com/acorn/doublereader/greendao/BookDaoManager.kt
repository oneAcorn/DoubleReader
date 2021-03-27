package com.acorn.doublereader.greendao

/**
 * Created by acorn on 2021/3/27.
 */
class BookDaoManager {
    val dao = DbManager.instance.session.bookModelDao

    companion object {
        val instance: BookDaoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { BookDaoManager() }
    }

    fun inserOrReplace(model: BookModel) {
        
    }
}