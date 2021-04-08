package com.acorn.doublereader.ui.reader

import android.os.Bundle
import android.view.View
import com.acorn.doublereader.R
import com.acorn.doublereader.extend.createViewModel
import com.acorn.doublereader.greendao.BookModel
import com.acorn.doublereader.ui.reader.viewmodel.BookDetailViewModel
import com.base.commonmodule.base.CommonBaseFragment
import com.base.commonmodule.utils.logI
import kotlinx.android.synthetic.main.fragment_book_detail.*

/**
 * Created by acorn on 2021/3/31.
 */
class BookDetailFragment : CommonBaseFragment() {
    private val viewModel: BookDetailViewModel by lazy { createViewModel(BookDetailViewModel::class.java) }
    private var bookModel: BookModel? = null

    companion object {
        fun newInstance(bookModel: BookModel): BookDetailFragment {
            val args = Bundle()
            args.putParcelable("book", bookModel)
            val fragment = BookDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutResId(): Int = R.layout.fragment_book_detail

    override fun initIntentData() {
        super.initIntentData()
        bookModel = arguments?.getParcelable("book")
    }

    override fun initData() {
        super.initData()
        viewModel.commonState.observe(this, this)
        viewModel.getTxtBookLiveData().observe(this, {
            readerView.setText(it)
        })

        bookModel?.also { viewModel.loadTxtBook(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logI("fdsf")
    }
}