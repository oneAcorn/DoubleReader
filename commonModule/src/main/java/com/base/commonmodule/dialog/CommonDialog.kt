package com.base.commonmodule.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.commonmodule.R
import com.base.commonmodule.extend.width
import kotlinx.android.synthetic.main.common_dialog_layout.view.*

/**
 * Author:yu
 * Date: 2019/1/4
 * Description: 信息提示dialog
 **/
class CommonDialog {
    /**
     * 显示dialog
     * @param context 上下文
     * @param title 标题
     * @param msg 提示语
     * @param negativeStr 取消按钮的文字，传空字符传时，取消按钮不显示
     * @param positionStr 确定按钮文字
     * @param onButtonClick 按钮点击的监听
     */
    fun show(context: Context?, title: String?, msg: String?, negativeStr: String, positionStr: String, onButtonClick: OnButtonClickListener?) {
        context?.let {
            val view = LayoutInflater.from(it).inflate(R.layout.common_dialog_layout, null)
            val tvTitleTips = view.tvTitleTips
            val tvMessage = view.tvMessage
            val tvCancel = view.tvCancel
            val tvConfirm = view.tvConfirm
            if (!title.isNullOrEmpty()) {
                tvTitleTips.visibility = View.VISIBLE
                tvTitleTips.text = title
            } else {
                tvTitleTips.visibility = View.GONE
            }
            msg?.let { item ->
                tvMessage.text = item
            }
            if (negativeStr.isNullOrBlank())
                tvCancel.visibility = View.GONE
            else
                tvCancel.text = negativeStr
            tvConfirm.text = positionStr
            val builder = AlertDialog.Builder(it)
            builder.setCancelable(false)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
            dialog.setContentView(view)
            dialog.window?.setLayout((width * 0.3).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            tvCancel.setOnClickListener {
                dialog.dismiss()
                onButtonClick?.onNegativeClick()
            }
            tvConfirm.setOnClickListener {
                dialog.dismiss()
                onButtonClick?.onPositiveClick()
            }
        }
    }

    interface OnButtonClickListener {
        fun onNegativeClick()
        fun onPositiveClick()
    }
}