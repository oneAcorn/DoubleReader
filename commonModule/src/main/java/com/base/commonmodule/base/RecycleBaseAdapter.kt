package com.base.commonmodule.base

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @author ChenChong 2018-11-29 13:43
 */
abstract class RecycleBaseAdapter<D, VH : BaseViewHolder<D>> : RecyclerView.Adapter<VH>() {
    private var layoutInflater: LayoutInflater? = null
    val dataArrayList = ArrayList<D>()

    protected fun getLayoutInflater(): LayoutInflater {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(BaseApplication.appContext)
        }
        return layoutInflater!!
    }

    fun addData(dataArrayList: List<D>?) {
        if (dataArrayList != null) {
            this.dataArrayList.addAll(dataArrayList)
        }
        notifyDataSetChanged()
    }

    fun addData(dataArrayList: List<D>?, isClearOldData: Boolean) {
        if (isClearOldData) {
            this.dataArrayList.clear()
        }
        if (dataArrayList != null) {
            this.dataArrayList.addAll(dataArrayList)
        }
        notifyDataSetChanged()
    }

    fun addDataDoNotRefresh(dataArrayList: List<D>?, isClearOldData: Boolean) {
        if (isClearOldData) {
            this.dataArrayList.clear()
        }
        if (dataArrayList != null) {
            this.dataArrayList.addAll(dataArrayList)
        }
    }

    fun removeData(position: Int) {
        dataArrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getDataArrayList(): List<D> {
        return dataArrayList
    }

    open fun getItem(position: Int): D? {
        return if (position >= 0 && position < dataArrayList.size) {
            dataArrayList[position]
        } else {
            null
        }
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }
}
