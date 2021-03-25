package com.base.commonmodule.network

/**
 * Created by acorn on 2019-08-21.
 */
interface IResponse {
    fun isSuccess(): Boolean

    fun failMessage(): String?

    fun code():Int
}