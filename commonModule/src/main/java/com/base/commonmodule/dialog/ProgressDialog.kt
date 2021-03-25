package com.base.commonmodule.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import com.base.commonmodule.R

/**
 * Created by acorn on 2019-08-20.
 */
class ProgressDialog : AppCompatDialogFragment() {

    companion object {
        fun newInstance(): ProgressDialog {
            val args = Bundle()
            val fragment = ProgressDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        return View.inflate(context, R.layout.base_dialog_progress, null)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            val attr = it.attributes.apply {
                dimAmount = 0f
            }
            it.attributes = attr
        }
    }
}