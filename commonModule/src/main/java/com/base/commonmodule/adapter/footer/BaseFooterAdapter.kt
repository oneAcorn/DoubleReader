package com.base.commonmodule.adapter.footer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * 带加载更多的Footer的Adapter
 * Created by acorn on 2020/6/15.
 */
abstract class BaseFooterAdapter<T>(context: Context,
                                    var datas: MutableList<T>?,
                                    protected var isShowFooter: Boolean = true)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IFooter {
    protected val inflater: LayoutInflater = LayoutInflater.from(context)
    private var isLoadingFinish = false
    var footerFinishText = "没有更多了"
    var footerLoadingText = "加载中.."

    companion object {
        const val ITEM_FOOTER = 6789
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_FOOTER) {
            FooterHolder(inflater.inflate(FooterHolder.layoutId, parent, false),
                    footerLoadingText, footerFinishText)
        } else {
            onDataCreateViewHolder(parent, viewType)
        }
    }

    /**
     * 创建非Header,Footer的数据层的ViewHolder
     */
    protected abstract fun onDataCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterHolder) {
            holder.setLoadingState(isLoadingFinish)
        } else {
            onDataBindViewHolder(datas?.get(position), holder, position)
        }
    }

    protected abstract fun onDataBindViewHolder(data: T?, holder: RecyclerView.ViewHolder, position: Int)

    /**
     * 获取非Header,Footer的数据层的数量
     */
    open fun getDataCount(): Int {
        return datas?.size ?: 0
    }

    override fun getItemCount(): Int {
        return getDataCount() + if (isFooterShouldShow()) 1 else 0
    }

    /**
     * 获取非Header,Footer的数据层的视图类型
     * 默认返回0,当有多种ViewType时需要重写此方法
     */
    protected open fun getDataItemViewType(): Int {
        return 0
    }

    override fun getItemViewType(position: Int): Int {
        if (isFooterShouldShow() && position == itemCount - 1) {
            return ITEM_FOOTER
        }
        val type = getDataItemViewType()
        if (type == ITEM_FOOTER)
            throw IllegalArgumentException("Item type cannot equal $ITEM_FOOTER")
        return type
    }

    protected open fun isFooterShouldShow(): Boolean {
        return isShowFooter && getDataCount() != 0
    }

    override fun isLoadingFinish() = isLoadingFinish

    override fun setLoadingState(isFinished: Boolean) {
        isLoadingFinish = isFinished
        notifyDataSetChanged()
    }

    open fun getItem(position: Int): T? {
        return datas?.get(position)
    }

    open fun addAll(addDatas: MutableList<T>?) {
        if (addDatas?.isEmpty() != false)
            return
        if (datas == null) {
            datas = addDatas
        } else {
            datas?.addAll(addDatas)
        }
        notifyDataSetChanged()
    }

    fun setList(newDatas: MutableList<T>?) {
        datas = newDatas;
        notifyDataSetChanged()
    }

    open fun clear() {
        datas?.clear()
        notifyDataSetChanged()
    }
}