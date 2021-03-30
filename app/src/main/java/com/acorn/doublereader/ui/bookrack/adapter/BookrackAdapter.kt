package com.acorn.doublereader.ui.bookrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acorn.doublereader.R
import com.acorn.doublereader.base.MyApplication
import com.acorn.doublereader.greendao.BookModel
import com.base.commonmodule.base.BaseApplication
import com.base.commonmodule.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_bookrack_book.view.*
import kotlinx.android.synthetic.main.item_bookrack_header.view.*

/**
 * Created by acorn on 2021/3/29.
 */
class BookrackAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(BaseApplication.appContext)

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
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    inner class HeaderHolder(itemView: View) : BaseViewHolder<Any>(itemView) {
        val latestReadRv: RecyclerView = itemView.latestReadRv

        override fun bindData(data: Any?, position: Int) {
            super.bindData(data, position)
        }
    }

    inner class BookHolder(itemView: View) : BaseViewHolder<BookModel>(itemView) {
        val nameTv = itemView.bookNameTv
        val typeTv = itemView.bookTypeTv
        val bgView = itemView.bookBgView

        override fun bindData(data: BookModel?, position: Int) {
            super.bindData(data, position)
        }
    }
}