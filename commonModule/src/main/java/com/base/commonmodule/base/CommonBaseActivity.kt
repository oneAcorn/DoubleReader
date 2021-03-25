package com.base.commonmodule.base

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.base.commonmodule.R
import com.base.commonmodule.dialog.ProgressDialog
import com.base.commonmodule.extend.singleClick
import com.base.commonmodule.network.INetworkUI
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.base_activity_base_layout.*

/**
 * Created by acorn on 2020/5/3.
 */
abstract class CommonBaseActivity : AppCompatActivity(), INetworkUI {
    val disposable = CompositeDisposable()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog.newInstance() }
    private var isProgressShowing = false
    private var isPausing = false


    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.base_activity_base_layout)
        LayoutInflater.from(this).inflate(layoutResID, baseContentLayout)
        initData()
        initView()
        initListener()
    }

    /**
     * 初始化数据,如通过Intent获取的数据,MVVM的ViewModel的初始化等
     */
    protected abstract fun initData()

    /**
     *  初始化View
     */
    protected abstract fun initView()

    /**
     *  初始化各种监听器
     */
    protected abstract fun initListener()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        dismissProgressDialog()
    }

    override fun onPause() {
        super.onPause()
        isPausing = true
    }

    override fun onResume() {
        super.onResume()
        isPausing = false
    }

    open fun onBack() {
        finish()
    }

    protected open fun errorLayoutResId(): Int {
        return R.layout.common_net_fail_view
    }

    protected open fun nullLayoutResId(): Int {
        return R.layout.common_empty_view
    }

    override fun showProgressDialog() {
        if (!isFinishing && progressDialog.dialog?.isShowing != true && !isPausing) {
            progressDialog.show(supportFragmentManager, "progressDialog")
            isProgressShowing = true
        }
    }

    override fun dismissProgressDialog() {
        //不能使用progressDialog.dialog?.isShowing，因为在刚刚show之后的瞬间调用此方法dialog为空
        if (!isFinishing && isProgressShowing) {
            progressDialog.dismiss()
            isProgressShowing = false
        }
    }

    override fun showErrorLayout() {
        baseContentLayout.visibility = View.GONE
        baseErrorLayout.visibility = View.VISIBLE
        baseErrorLayout.removeAllViews()
        LayoutInflater.from(this).inflate(errorLayoutResId(), baseErrorLayout)
        baseErrorLayout.findViewById<View>(R.id.net_fresh)?.singleClick {
            retry()
        }
    }

    override fun showContentLayout() {
        baseContentLayout.visibility = View.VISIBLE
        baseErrorLayout.visibility = View.GONE
    }

    override fun showNullLayout() {
        baseContentLayout.visibility = View.GONE
        baseErrorLayout.visibility = View.VISIBLE
        baseErrorLayout.removeAllViews()
        LayoutInflater.from(this).inflate(nullLayoutResId(), baseErrorLayout)
    }

    protected fun showToast(@StringRes strRes: Int) {
        showToast(getString(strRes))
    }

    override fun showToast(string: String) {
        Toast.makeText(applicationContext, null, Toast.LENGTH_SHORT).apply {
            //在这里setText，防止MIUI toast时带上app名称
            setText(string)
            setGravity(Gravity.CENTER, 0, 0)
        }.show()
    }

    protected open fun retry() {
    }
}