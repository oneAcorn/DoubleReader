package com.base.commonmodule.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author ChenChong 2018-11-29 13:43
 */
abstract class BaseViewHolder<VHD>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun update(model: VHD?) {

    }

    open fun update(model: VHD?, position: Int) {

    }

    open fun update(model1: VHD?, model2: VHD?, model3: VHD?, position: Int) {

    }
}
