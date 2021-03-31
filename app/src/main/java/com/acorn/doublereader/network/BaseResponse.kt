package com.acorn.doublereader.network

import com.base.commonmodule.network.IResponse

/**
 * Created by acorn on 2021/3/31.
 */
class BaseResponse<T>(val data: T?, val code: Int = 200, val msg: String? = null) : IResponse {
    override fun isSuccess(): Boolean = code == 200

    override fun failMessage(): String? = msg

    override fun code(): Int = code
}