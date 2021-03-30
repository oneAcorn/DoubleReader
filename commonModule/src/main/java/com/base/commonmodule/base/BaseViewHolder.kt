package com.base.commonmodule.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ChenChong 2018-11-29 13:43
 */
abstract class BaseViewHolder<VHD>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun bindData(data: VHD?, position: Int) {

    }
}
