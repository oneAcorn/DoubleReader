package com.acorn.doublereader.ui.bookrack.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acorn.doublereader.bean.BookrackBean
import com.acorn.doublereader.bean.BookrackHeaderBean
import com.acorn.doublereader.extend.commonRequest
import com.acorn.doublereader.greendao.BookDaoManager
import com.acorn.doublereader.greendao.BookModel
import com.acorn.doublereader.network.BaseResponse
import com.acorn.doublereader.utils.Caches
import com.acorn.doublereader.utils.CharsetDetector
import com.base.commonmodule.base.BaseApplication
import com.base.commonmodule.exception.MyException
import com.base.commonmodule.network.BaseNetViewModel
import com.base.commonmodule.network.BaseObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import java.util.*

/**
 * Created by acorn on 2021/3/29.
 */
class BookrackViewModel : BaseNetViewModel() {
    private val bookrackLiveData: MutableLiveData<BookrackBean> by lazy { MutableLiveData() }
    private val addBookLiveData: MutableLiveData<BookModel> by lazy { MutableLiveData() }

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

    fun addBook(uriStr: String?) {
        uriStr ?: return
        Observable.create<BaseResponse<BookModel>> { emitter ->
            Caches.lastBookUriStr = uriStr
            val uriChinese = Uri.decode(uriStr)
            val nameStartIndex = uriChinese.lastIndexOf('/') + 1
            val nameEndIndex = uriChinese.lastIndexOf('.')
            if (nameStartIndex == -1 || nameEndIndex == -1) {
                emitter.onError(MyException("get book name failed", 1))
                return@create
            }
            val name = uriChinese.substring(nameStartIndex, nameEndIndex)
            val type = when (uriChinese.substring(nameEndIndex + 1, uriChinese.length)) {
                "txt" -> 0
                "epub" -> 1
                "pdf" -> 2
                else -> -1
            }
            if (type == -1) {
                emitter.onError(MyException("get book type failed", 2))
                return@create
            }
            val encoding = if (type == 0) {
                val contentResolver=BaseApplication.appContext.contentResolver
                val uri = Uri.parse(uriStr)
                CharsetDetector.detectCharset(contentResolver.openInputStream(uri))
            } else {
                null
            }
            val bookModel = BookModel().apply {
                this.path = uriStr
                this.name = name
                this.type = type
                this.addDate = Date()
                this.charset = encoding
            }
            BookDaoManager.instance.inserOrUpdate(bookModel)
            emitter.onNext(BaseResponse(bookModel))
            emitter.onComplete()
        }
            .commonRequest(disposable)
            .subscribe(object : BaseObserver<BaseResponse<BookModel>>(commonState) {
                override fun success(t: BaseResponse<BookModel>) {
                    super.success(t)
                    addBookLiveData.value = t.data
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
    fun getAddBookLiveData(): LiveData<BookModel> = addBookLiveData
}