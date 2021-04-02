package com.acorn.doublereader.ui.reader

import android.os.Bundle
import android.os.PersistableBundle
import com.acorn.doublereader.R
import com.acorn.doublereader.greendao.BookModel
import com.base.commonmodule.base.CommonBaseActivity

/**
 * Created by acorn on 2021/3/31.
 */
class BookDetailActivity : CommonBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
    }

    override fun initIntentData() {
        super.initIntentData()
        val bookModel = intent.getParcelableExtra<BookModel>("book")
        if (bookModel != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BookDetailFragment.newInstance(bookModel))
                .commit()
        }
    }
}