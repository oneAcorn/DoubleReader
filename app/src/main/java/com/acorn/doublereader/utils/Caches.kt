package com.acorn.doublereader.utils

import com.base.commonmodule.extend.string
import com.tencent.mmkv.MMKV

/**
 * Created by acorn on 2021/3/26.
 */
object Caches {
    private val mmkv = MMKV.mmkvWithID("doubleReader", MMKV.SINGLE_PROCESS_MODE)

    //上次打开书籍时的URI
    var lastBookUriStr: String? by mmkv.string()
}