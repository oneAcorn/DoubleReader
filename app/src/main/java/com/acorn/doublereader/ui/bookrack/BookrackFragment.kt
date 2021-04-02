package com.acorn.doublereader.ui.bookrack

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.GridLayoutManager
import com.acorn.doublereader.R
import com.acorn.doublereader.extend.createViewModel
import com.acorn.doublereader.ui.bookrack.adapter.BookrackAdapter
import com.acorn.doublereader.ui.bookrack.viewmodel.BookrackViewModel
import com.acorn.doublereader.ui.reader.BookDetailActivity
import com.acorn.doublereader.utils.Caches
import com.acorn.doublereader.utils.CharsetDetector
import com.base.commonmodule.base.CommonBaseFragment
import com.base.commonmodule.extend.requestPermission
import com.base.commonmodule.extend.saveFilePermission
import com.base.commonmodule.extend.singleClick
import com.base.commonmodule.extend.startBookPicker
import kotlinx.android.synthetic.main.fragment_bookrack.*

/**
 * Created by acorn on 2021/3/26.
 */
class BookrackFragment : CommonBaseFragment() {
    private val viewModel: BookrackViewModel by lazy { createViewModel(BookrackViewModel::class.java) }
    private var adapter: BookrackAdapter = BookrackAdapter(null)

    companion object {
        const val PICK_FILE_REQUEST_CODE = 2
    }

    override fun initView() {
        rv.isFocusable = false
        rv.isFocusableInTouchMode = false
        rv.layoutManager = GridLayoutManager(context, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0)
                        3
                    else {
                        1
                    }
                }
            }
        }
        rv.adapter = adapter
    }

    override fun initListener() {
        testBtn.singleClick {

        }
        refreshBtn.singleClick {
            refreshData()
        }
        adapter.onAddBookCallback = {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, allPermGrantedCallback = {
                val lastBookUri =
                    if (Caches.lastBookUriStr.isNullOrEmpty()) null else Uri.parse(Caches.lastBookUriStr)
                startBookPicker(PICK_FILE_REQUEST_CODE, lastBookUri)
            })
        }
        adapter.onBookClickCallback = {
            var encoding: String? = null
            if (it?.path?.isNotEmpty() == true) {
                encoding = CharsetDetector.detectCharset(
                    requireContext().contentResolver.openInputStream(Uri.parse(it.path))
                )
            }
            showToast("book:${it?.name},$encoding")
            if (it != null) {
                startActivity(Intent(context, BookDetailActivity::class.java).apply {
                    putExtra("book", it)
                })
            }
        }
    }

    override fun initData() {
        viewModel.commonState.observe(this, this)
        viewModel.getBookrackLiveData().observe(this, {
            adapter.setData(it)
            rv.adapter = adapter
        })
        viewModel.getAddBookLiveData().observe(this, {
            saveFilePermission(Uri.parse(it.path))
            refreshData()
        })
        refreshData()
    }

    private fun refreshData() {
        viewModel.requestBookrack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_FILE_REQUEST_CODE -> { //选择书籍
                    addBook(data?.dataString)
                    refreshData()
                }
            }
        }
    }

    private fun addBook(uriStr: String?) {
        viewModel.addBook(uriStr)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_bookrack
    }
}