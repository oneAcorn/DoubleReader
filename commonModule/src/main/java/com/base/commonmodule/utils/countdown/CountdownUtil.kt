package com.base.commonmodule.utils.countdown

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.base.commonmodule.utils.logI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/7/21.
 */
object CountdownUtil {
    private var countBinder: CountService.MyBinder? = null
    private var countDisposable: Disposable? = null
    private val disposeObserver = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY && countDisposable?.isDisposed == false) {
            countDisposable?.dispose()
            countDisposable = null
        }
    }

    /**
     * 轻量化版的倒计时，不需要绑定service，长时间后台运行不稳定
     */
    fun countDownlite(
        lifecycleOwner: LifecycleOwner, totalSec: Int,
        countingCallback: ((sec: Int) -> Unit)? = null,
        countFinishCallback: (() -> Unit)? = null
    ): Disposable {
        countDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .map {
                totalSec.toLong() - it
            }
            .take(totalSec + 1L)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                countingCallback?.invoke(it.toInt())
            }, {
                it.printStackTrace()
            }, {
                countFinishCallback?.invoke()
            })
        //移除上次添加的Observer，防止多次调用
        lifecycleOwner.lifecycle.removeObserver(disposeObserver)
        lifecycleOwner.lifecycle.addObserver(disposeObserver)
        return countDisposable!!
    }

    /**
     * 倒计时
     * @param sec 总倒计时(秒)
     * @param secStep 计时描述间隔(秒)
     */
    fun startCountdown(
        activity: AppCompatActivity,
        sec: Int,
        secStep: Int = 1,
        callback: (sec: Int) -> Unit
    ) {
        bindCountService(activity) {
            it.startCountdown(sec * 1000L, secStep * 1000L, object : CountService.OnCountListener {
                override fun onTick(millSec: Long, sec: Int) {
                    callback(sec)
                }
            })
        }
    }

    /**
     * 倒计时
     * @param millSec 总倒计时(毫秒)
     * @param millSecStep 计时描述间隔(毫秒)
     */
    fun startCountdownMill(
        activity: AppCompatActivity,
        millSec: Long,
        millSecStep: Long = 1000L,
        callback: (millSec: Long) -> Unit
    ) {
        bindCountService(activity) {
            it.startCountdown(millSec, millSecStep, object : CountService.OnCountListener {
                override fun onTick(millSec: Long, sec: Int) {
                    callback(millSec)
                }
            })
        }
    }

    /**
     * 正计时
     * @param secStep 计时描述间隔(毫秒)
     */
    fun startCount(activity: AppCompatActivity, secStep: Int = 1, callback: (sec: Int) -> Unit) {
        bindCountService(activity) {
            it.startCount(secStep * 1000L, object : CountService.OnCountListener {
                override fun onTick(millSec: Long, sec: Int) {
                    callback(sec)
                }
            })
        }
    }

    /**
     * 正计时
     * @param millSecStep 计时描述间隔(毫秒)
     */
    fun startCountMill(
        activity: AppCompatActivity,
        millSecStep: Long = 1000L,
        callback: (millSec: Long) -> Unit
    ) {
        bindCountService(activity) {
            it.startCount(millSecStep, object : CountService.OnCountListener {
                override fun onTick(millSec: Long, sec: Int) {
                    callback(millSec)
                }
            })
        }
    }

    private fun bindCountService(
        activity: AppCompatActivity,
        callback: (CountService.MyBinder) -> Unit
    ) {
        if (null != countBinder) {
            callback(countBinder!!)
            return
        }
        val serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                if (service !is CountService.MyBinder) return
                countBinder = service
                callback(service)
            }

        }

        activity.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            logI("event:$event")
            if (event == Lifecycle.Event.ON_DESTROY) {
                activity.unbindService(serviceConnection)
            }
        })

        val serviceIntent = Intent(activity, CountService::class.java)
        activity.bindService(serviceIntent, serviceConnection, AppCompatActivity.BIND_AUTO_CREATE)
    }
}