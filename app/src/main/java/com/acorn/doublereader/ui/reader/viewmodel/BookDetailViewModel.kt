package com.acorn.doublereader.ui.reader.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acorn.doublereader.extend.commonRequest
import com.acorn.doublereader.greendao.BookModel
import com.acorn.doublereader.network.BaseResponse
import com.acorn.doublereader.utils.IOUtils
import com.base.commonmodule.base.BaseApplication
import com.base.commonmodule.exception.MyException
import com.base.commonmodule.network.BaseNetViewModel
import com.base.commonmodule.network.BaseObserver
import io.reactivex.rxjava3.core.Observable
import java.util.*

/**
 * Created by acorn on 2021/4/2.
 */
class BookDetailViewModel : BaseNetViewModel() {
    private val txtBookLiveData: MutableLiveData<String?> by lazy { MutableLiveData() }

    fun loadTxtBook(book: BookModel) {
        if (book.type != 0) {
            commonState.showToast("This book is not a Txt Book")
            return
        }
        Observable.create<BaseResponse<String?>> { emitter ->
            val uri = Uri.parse(book.path)
            val inputStream = BaseApplication.appContext.contentResolver.openInputStream(uri)
            if (null != inputStream) {
                emitter.onNext(BaseResponse(IOUtils.readByInputStream(inputStream, book.charset)))
                emitter.onComplete()
            } else {
                emitter.onError(MyException("Can't load book:inputStream is null"))
            }
        }
            .commonRequest(disposable)
            .subscribe(object : BaseObserver<BaseResponse<String?>>(commonState) {
                override fun success(t: BaseResponse<String?>) {
                    super.success(t)
                    txtBookLiveData.value = t.data
                }
            })
    }

    fun getTxtBookLiveData(): LiveData<String?> = txtBookLiveData
}