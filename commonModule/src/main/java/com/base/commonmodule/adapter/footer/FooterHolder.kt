package com.base.commonmodule.adapter.footer

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.commonmodule.R

/**
 * Created by acorn on 2020/5/7.
 */
class FooterHolder(itemView: View, private val footerLoadingText: String = "加载中..",
                   private val footerFinishText: String = "没有更多了") : RecyclerView.ViewHolder(itemView) {
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressbar)
    private val loadStateTv = itemView.findViewById<TextView>(R.id.loadStateTv).apply {
        text = footerLoadingText
    }

    companion object {
        val layoutId = R.layout.common_item_load_more
    }

    fun setLoadingState(isFinished: Boolean) {
        progressBar.visibility = if (isFinished) View.GONE else View.VISIBLE
        loadStateTv.text = if (isFinished) footerFinishText else footerLoadingText
    }
}