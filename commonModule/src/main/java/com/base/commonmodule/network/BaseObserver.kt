package com.base.commonmodule.network

import android.accounts.NetworkErrorException
import android.os.Handler
import android.os.Message
import com.base.commonmodule.R
import com.base.commonmodule.base.BaseApplication
import com.base.commonmodule.utils.NetworkUtil
import io.reactivex.rxjava3.observers.DisposableObserver
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by acorn on 2019-08-20.
 */
open class BaseObserver<T : IResponse>(
    networkUI: INetworkUI?,
    private val model: ERROR_MODEL = ERROR_MODEL.TOAST,
    private val isShowProgressDialog: Boolean = true
) : DisposableObserver<T>() {
    private val weakUI = if (networkUI == null) null else WeakReference(networkUI)
    private val handler: ProgressHandler by lazy { ProgressHandler(weakUI) }
    private val showProgressDelayMill = 500L

    override fun onComplete() {
        if (isShowProgressDialog) {
//            weakUI?.get()?.dismissProgressDialog()
            handler.sendEmptyMessage(ProgressHandler.DISMISS_DIALOG)
        }
    }

    override fun onStart() {
        super.onStart()
        if (isShowProgressDialog) {
//            weakUI?.get()?.showProgressDialog()
            handler.sendEmptyMessageDelayed(ProgressHandler.SHOW_DIALOG, showProgressDelayMill)
        }
    }

    override fun onNext(t: T) {
        val isNullData = isNullData(t)
        if (t.isSuccess() && !isNullData) {
            weakUI?.get()?.showContentLayout()
            success(t)
        } else if (isNullData) {
            weakUI?.get()?.showNullLayout()
            success(t)
        } else {
            doError(t = t)
        }
    }

    override fun onError(e: Throwable) {
        doError(e = e)
    }

    private fun doError(t: T? = null, e: Throwable? = null) {
        if (isShowProgressDialog) {
//            weakUI?.get()?.dismissProgressDialog()
            handler.sendEmptyMessage(ProgressHandler.DISMISS_DIALOG)
        }
        weakUI?.get()?.let {
            if (model == ERROR_MODEL.LAYOUT) {
                it.showErrorLayout()
            } else if (model == ERROR_MODEL.TOAST) {
                it.showToast(getFailMessage(t, e))
            } else if (model == ERROR_MODEL.LAYOUT_TOAST) {
                it.showErrorLayout()
                it.showToast(getFailMessage(t, e))
            }
        }
        error(t, e)
    }

    private fun getFailMessage(t: T? = null, e: Throwable? = null): String {
        var msg = ""
        if (t != null) {
            if (t.failMessage()?.isNotEmpty() == true) {
                msg = t.failMessage() ?: "网络异常"
            } else {
                if (t.code() == 501) {
                    msg = "您1分钟内已获取过验证码，请1分钟后再获取)"
                } else if (t.code() == 502) {
                    msg = "该手机号今天已累计获取10条短信验证码，请明天再试"
                }
            }
        } else if (e != null) {
            msg = when {
                e is CommonException -> e.message
                    ?: BaseApplication.appContext.getString(R.string.server_exception)
                e is NetworkErrorException -> {
                    BaseApplication.appContext.getString(R.string.network_error)
                }
                e is SocketTimeoutException -> {
                    BaseApplication.appContext.getString(R.string.get_data_timeout)
                }
                e is UnknownHostException -> {
                    BaseApplication.appContext.getString(R.string.no_connect_service)
                }
                NetworkUtil.isNetworkConnected(BaseApplication.appContext) -> {
                    BaseApplication.appContext.getString(R.string.network_error)
                }
                else -> e.message ?: BaseApplication.appContext.getString(R.string.server_exception)
            }
        }
        return msg
    }

    protected open fun isNullData(t: T): Boolean {
        return false
    }

    protected open fun error(t: T?, e: Throwable?) {

    }

    protected open fun success(t: T) {

    }

    enum class ERROR_MODEL {
        TOAST,
        LAYOUT,
        NONE,
        LAYOUT_TOAST
    }

    class ProgressHandler(private val weakUI: WeakReference<INetworkUI?>?) : Handler() {

        companion object {
            const val SHOW_DIALOG = 1
            const val DISMISS_DIALOG = 2
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                SHOW_DIALOG -> {
                    val ui = weakUI?.get()
//                    logI("handler showDialog $ui")
                    ui?.showProgressDialog()
                }
                DISMISS_DIALOG -> {
                    val ui = weakUI?.get()
//                    logI("handler dismissDialog $ui")
                    ui?.dismissProgressDialog()
                    removeCallbacksAndMessages(null)
                }
            }
        }
    }
}