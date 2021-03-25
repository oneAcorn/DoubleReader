package com.base.commonmodule.adapter.footer

/**
 * Created by acorn on 2020/6/15.
 */
interface IFooter {
    fun isLoadingFinish(): Boolean

    fun setLoadingState(isFinished: Boolean)
}