package com.acorn.doublereader.ui.bookrack

import com.acorn.doublereader.greendao.BookDaoManager
import com.acorn.doublereader.greendao.BookModel
import com.base.commonmodule.network.BaseNetViewModel
import io.reactivex.rxjava3.core.Observable

/**
 * Created by acorn on 2021/3/29.
 */
class BookrackViewModel : BaseNetViewModel() {

    fun requestBookrack() {
        Observable.just("")
            .flatMap {
                Observable.create<List<BookModel>> {
                    it.onNext(BookDaoManager.instance.queryLatestReadBooks())
                    it.onComplete()
                }
            }
            .map {

            }
    }
}