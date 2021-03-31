package com.acorn.doublereader.ui.bookrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acorn.doublereader.R
import com.acorn.doublereader.bean.BookrackHeaderBean
import com.acorn.doublereader.greendao.BookModel
import com.base.commonmodule.base.BaseApplication
import com.base.commonmodule.base.BaseViewHolder
import com.base.commonmodule.extend.singleClick
import kotlinx.android.synthetic.main.item_bookrack_header_adapter.view.*

/**
 * Created by acorn on 2021/3/30.
 */
class BookrackHeaderAdapter(private val data: BookrackHeaderBean?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(BaseApplication.appContext)
    var onAddBookCallback: (() -> Unit)? = null

    companion object {
        const val ITEM_ADD_BOOK = 1
        const val ITEM_LATEST_READ_BOOK = 2
    }

    override fun getItemCount(): Int {
        return 1 + (data?.latestReadBooks?.size ?: 0)
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount == position + 1) ITEM_ADD_BOOK else ITEM_LATEST_READ_BOOK
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_ADD_BOOK) {
            HeaderAddBookHolder(
                inflater.inflate(
                    R.layout.item_bookrack_header_addbook_adapter,
                    parent,
                    false
                )
            )
        } else {
            HeaderBookHolder(inflater.inflate(R.layout.item_bookrack_header_adapter, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderBookHolder) {
            holder.bindData(data?.latestReadBooks?.get(position), position)
        }
    }

    inner class HeaderBookHolder(itemView: View) : BaseViewHolder<BookModel>(itemView) {
        override fun bindData(data: BookModel?, position: Int) {
            super.bindData(data, position)
            itemView.bookNameTv.text = data?.name
        }
    }

    inner class HeaderAddBookHolder(itemView: View) : BaseViewHolder<BookModel>(itemView) {
        init {
            itemView.singleClick {
                onAddBookCallback?.invoke()
            }
        }
    }
}