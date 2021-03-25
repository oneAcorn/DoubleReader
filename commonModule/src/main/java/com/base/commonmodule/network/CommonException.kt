package com.base.commonmodule.network

import java.lang.RuntimeException

/**
 * Created by acorn on 2020/5/4.
 */
class CommonException(msg: String, code: Int) : RuntimeException(msg)