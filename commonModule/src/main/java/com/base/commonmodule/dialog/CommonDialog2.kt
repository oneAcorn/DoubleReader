package com.base.commonmodule.dialog

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.base.commonmodule.R
import com.base.commonmodule.extend.singleClick
import com.base.commonmodule.extend.width


/**
 * Created by acorn on 2020/5/11.
 */
class CommonDialog2 : AppCompatDialogFragment() {
    private lateinit var titleTv: TextView
    private lateinit var contentTv: TextView
    private lateinit var cancelTv: TextView
    private lateinit var okTv: TextView
    private var listener: OnCommonDialogClickListener? = null

    companion object {
        fun newInstance(title: String? = null, content: String? = null, listener: OnCommonDialogClickListener?): CommonDialog2 {
            val args = Bundle()
            if (title != null) {
                args.putString("title", title)
            }
            if (content != null) {
                args.putString("content", content)
            }
            val fragment = CommonDialog2()
            fragment.arguments = args
            fragment.listener = listener
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = View.inflate(context, R.layout.common_dialog_common, null)
        initView(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TrainCommonDialog)
    }

    private fun initView(view: View) {
        titleTv = view.findViewById(R.id.titleTv)
        contentTv = view.findViewById(R.id.contentTv)
        cancelTv = view.findViewById(R.id.cancelTv)
        okTv = view.findViewById(R.id.okTv)

        val title: String? = arguments?.getString("title", null)
        val content: String? = arguments?.getString("content", null)
        if (title?.isNotEmpty() == true) {
            titleTv.visibility = View.VISIBLE
            titleTv.text = title
        } else {
            titleTv.visibility = View.GONE
        }
        if (content?.isNotEmpty() == true) {
            contentTv.visibility = View.VISIBLE
            contentTv.text = content
        } else {
            contentTv.visibility = View.GONE
        }

        okTv.singleClick {
            listener?.onOkClick(this)
        }

        cancelTv.singleClick {
            listener?.onCancelClick(this)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            val lp = it.attributes
            lp.width = (width.toFloat() * 0.7f).toInt()
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes = lp
        }
    }

    interface OnCommonDialogClickListener {
        fun onOkClick(dialog: CommonDialog2)

        fun onCancelClick(dialog: CommonDialog2)
    }
}