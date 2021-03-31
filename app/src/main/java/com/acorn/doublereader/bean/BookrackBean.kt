package com.acorn.doublereader.bean

import com.acorn.doublereader.greendao.BookModel

/**
 * Created by acorn on 2021/3/29.
 */
data class BookrackBean(val headerData: BookrackHeaderBean, val books: List<BookModel>?)

data class BookrackHeaderBean(val latestReadBooks: List<BookModel>?, val totalReadSecond: Long?)