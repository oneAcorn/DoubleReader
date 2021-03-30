package com.acorn.doublereader.greendao

import com.acorn.doublereader.greendao.generate.BookModelDao
import com.base.commonmodule.utils.logI

/**
 * Created by acorn on 2021/3/27.
 */
class BookDaoManager {
    val dao = DbManager.instance.session.bookModelDao

    companion object {
        val instance: BookDaoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { BookDaoManager() }
    }

    fun inserOrUpdate(bookModel: BookModel) {
        //清除缓存,防止获取到旧数据
        dao.detachAll()
        val bookQueried = dao.queryBuilder()
            .whereOr(
                BookModelDao.Properties.Id.eq(bookModel.id ?: -1L),
                dao.queryBuilder().and(
                    BookModelDao.Properties.Name.eq(bookModel.name ?: ""),
                    BookModelDao.Properties.Type.eq(bookModel.type),
                    BookModelDao.Properties.Path.eq(bookModel.path ?: "")
                )
            ).list()?.takeIf {
                it.isNotEmpty()
            }?.get(0)
        if (null != bookQueried) {
            bookModel.id = bookQueried.id
            dao.update(bookModel)
        } else {
            dao.insert(bookModel)
        }
    }

    fun test(){
        dao.queryBuilder().where(BookModelDao.Properties.LatestReadDate.notEq(null))
        logI("fff")
    }

    fun queryLatestReadBooks(): List<BookModel> {
        return dao.queryBuilder()
            .where(BookModelDao.Properties.LatestReadDate.isNotNull)
            .orderDesc(BookModelDao.Properties.LatestReadDate)
            .list()
    }
}