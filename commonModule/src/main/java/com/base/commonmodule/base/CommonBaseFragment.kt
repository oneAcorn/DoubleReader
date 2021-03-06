package com.base.commonmodule.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.base.commonmodule.R
import com.base.commonmodule.dialog.ProgressDialog
import com.base.commonmodule.extend.singleClick
import com.base.commonmodule.network.INetworkUI
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.base_fragment_base_layout.*

/**
 * Created by acorn on 2020/5/3.
 */
abstract class CommonBaseFragment : Fragment(), INetworkUI {
    val disposable = CompositeDisposable()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog.newInstance() }
    protected var rootView: View? = null
    private var isProgressShowing = false
    private var isPausing = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.base_fragment_base_layout, container, false)
            val contentLayout = rootView?.findViewById<ViewGroup>(R.id.baseContentLayout)
            inflater.inflate(layoutResId(), contentLayout)
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initIntentData()
        initView()
        initListener()
        initData()
    }

    protected open fun initIntentData() {}

    protected open fun initView() {}

    protected open fun initListener() {}

    protected open fun initData() {}

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

    protected abstract fun layoutResId(): Int

    protected open fun errorLayoutResId(): Int {
        return R.layout.common_net_fail_view
    }

    protected open fun nullLayoutResId(): Int {
        return R.layout.common_empty_view
    }

    override fun showProgressDialog() {
        if (!isHidden && progressDialog.dialog?.isShowing != true && !isPausing) {
            progressDialog.show(childFragmentManager, "progressDialog")
            isProgressShowing = true
        }
    }

    override fun dismissProgressDialog() {
        //????????????progressDialog.dialog?.isShowing??????????????????show??????????????????????????????,
        // Fragment??????????????????????????????getDialog()??????
        if (isProgressShowing) {
            progressDialog.dismiss()
            isProgressShowing = false
        }
    }

    override fun showErrorLayout() {
        baseContentLayout.visibility = View.GONE
        baseErrorLayout.visibility = View.VISIBLE
        baseErrorLayout.removeAllViews()
        LayoutInflater.from(context).inflate(errorLayoutResId(), baseErrorLayout)
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
        LayoutInflater.from(context).inflate(nullLayoutResId(), baseErrorLayout)
    }

    protected fun showToast(@StringRes strRes: Int) {
        showToast(getString(strRes))
    }

    override fun showToast(string: String) {
        Toast.makeText(context?.applicationContext, null, Toast.LENGTH_SHORT).apply {
            //?????????setText?????????MIUI toast?????????app??????
            setText(string)
            setGravity(Gravity.CENTER, 0, 0)
        }.show()
    }

    protected open fun retry() {
    }
}