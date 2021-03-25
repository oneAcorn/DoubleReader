package com.base.commonmodule.network

/**
 * Created by acorn on 2020/9/4.
 */
data class CommonLiveData(
    val isSuccess: Boolean,
    val errMsg: String? = null,
    val errCode: Int? = null
)