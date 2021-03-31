package com.acorn.doublereader.ui.bookrack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acorn.doublereader.bean.BookrackBean
import com.acorn.doublereader.bean.BookrackHeaderBean
import com.acorn.doublereader.extend.commonRequest
import com.acorn.doublereader.greendao.BookDaoManager
import com.acorn.doublereader.greendao.BookModel
import com.acorn.doublereader.network.BaseResponse
import com.base.commonmodule.network.BaseNetViewModel
import com.base.commonmodule.network.BaseObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction

/**
 * Created by acorn on 2021/3/29.
 */
class BookrackViewModel : BaseNetViewModel() {
    private val bookrackLiveData: MutableLiveData<BookrackBean> by lazy { MutableLiveData() }

    fun requestBookrack() {
        Observable.zip(
            getLatestReadBooksObservable(),
            getAllBooksObservable(),
            BiFunction { t1, t2 ->
                BaseResponse(BookrackBean(t1, t2))
            })
            .commonRequest(disposable)
            .subscribe(object : BaseObserver<BaseResponse<BookrackBean>>(commonState) {
                override fun success(t: BaseResponse<BookrackBean>) {
                    super.success(t)
                    bookrackLiveData.value = t.data
                }
            })
    }

    private fun getLatestReadBooksObservable(): Observable<BookrackHeaderBean> {
        return Observable.create {
            it.onNext(BookrackHeaderBean(BookDaoManager.instance.queryLatestReadBooks(), 0L))
            it.onComplete()
        }
    }

    private fun getAllBooksObservable(): Observable<List<BookModel>> {
        return Observable.create {
            it.onNext(BookDaoManager.instance.dao.loadAll())
            it.onComplete()
        }
    }

    fun getBookrackLiveData(): LiveData<BookrackBean> = bookrackLiveData
}