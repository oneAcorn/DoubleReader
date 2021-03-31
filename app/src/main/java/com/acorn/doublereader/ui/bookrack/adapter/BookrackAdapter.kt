package com.acorn.doublereader.ui.bookrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acorn.doublereader.R
import com.acorn.doublereader.base.MyApplication
import com.acorn.doublereader.bean.BookrackBean
import com.acorn.doublereader.bean.BookrackHeaderBean
import com.acorn.doublereader.greendao.BookModel
import com.base.commonmodule.base.BaseApplication
import com.base.commonmodule.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_bookrack_book.view.*
import kotlinx.android.synthetic.main.item_bookrack_header.view.*

/**
 * Created by acorn on 2021/3/29.
 */
class BookrackAdapter(private var data: BookrackBean?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(BaseApplication.appContext)
    var onAddBookCallback: (() -> Unit)? = null

    companion object {
        const val ITEM_HEADER = 1
        const val ITEM_BOOKRACK = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_HEADER) {
            HeaderHolder(inflater.inflate(R.layout.item_bookrack_header, parent, false))
        } else {
            BookHolder(inflater.inflate(R.layout.item_bookrack_book, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderHolder) {
            holder.bindData(data?.headerData, position)
        } else if (holder is BookHolder) {
            holder.bindData(data?.books?.get(position - 1), position - 1)
        }
    }

    override fun getItemCount(): Int {
        return 1 + (data?.books?.size ?: 0)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                ITEM_HEADER
            }
            else -> {
                ITEM_BOOKRACK
            }
        }
    }

    fun setData(newData: BookrackBean?) {
        data = newData
        notifyDataSetChanged()
    }

    inner class HeaderHolder(itemView: View) : BaseViewHolder<BookrackHeaderBean>(itemView) {
        private val latestReadRv: RecyclerView = itemView.latestReadRv
        private val adapter = BookrackHeaderAdapter(null)

        init {
            latestReadRv.isFocusableInTouchMode = false
            latestReadRv.isFocusable = false
            latestReadRv.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter.onAddBookCallback = onAddBookCallback
            latestReadRv.adapter = adapter
        }

        override fun bindData(data: BookrackHeaderBean?, position: Int) {
            super.bindData(data, position)
            adapter.setData(data)
        }
    }

    inner class BookHolder(itemView: View) : BaseViewHolder<BookModel>(itemView) {
        private val nameTv = itemView.bookNameTv
        private val typeTv = itemView.bookTypeTv
        private val bgView = itemView.bookBgView
        private val titleTv = itemView.bookTitleTv

        override fun bindData(data: BookModel?, position: Int) {
            super.bindData(data, position)
            nameTv.text = data?.name
            titleTv.text = data?.name
            typeTv.text = when (data?.type) {
                0 -> {
                    "txt"
                }
                1 -> {
                    "epub"
                }
                2 -> {
                    "pdf"
                }
                else -> {
                    "未知"
                }
            }
        }
    }
}