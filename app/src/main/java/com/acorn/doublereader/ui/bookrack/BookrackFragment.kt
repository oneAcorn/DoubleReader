package com.acorn.doublereader.ui.bookrack

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.acorn.doublereader.R
import com.acorn.doublereader.greendao.BookDaoManager
import com.acorn.doublereader.greendao.BookModel
import com.acorn.doublereader.utils.Caches
import com.base.commonmodule.base.CommonBaseFragment
import com.base.commonmodule.extend.requestPermission
import com.base.commonmodule.extend.saveFilePermission
import com.base.commonmodule.extend.singleClick
import com.base.commonmodule.extend.startBookPicker
import kotlinx.android.synthetic.main.fragment_bookrack.*
import java.util.*

/**
 * Created by acorn on 2021/3/26.
 */
class BookrackFragment : CommonBaseFragment() {

    companion object {
        const val PICK_FILE_REQUEST_CODE = 2
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initListener() {
        addBookBtn.singleClick {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, allPermGrantedCallback = {
                val lastBookUri =
                    if (Caches.lastBookUriStr.isNullOrEmpty()) null else Uri.parse(Caches.lastBookUriStr)
                startBookPicker(PICK_FILE_REQUEST_CODE, lastBookUri)
            })
        }
//        testBtn1.singleClick {
//            val list=BookDaoManager.instance.dao.loadAll()
//            logI("fdsfas")
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_FILE_REQUEST_CODE -> { //选择书籍
                    addBook(data?.dataString)
                }
            }
        }
    }

    private fun addBook(uriStr: String?) {
        uriStr ?: return
        val uri = Uri.parse(uriStr)
        Caches.lastBookUriStr = uriStr
        saveFilePermission(uri)
        val uriChinese = Uri.decode(uriStr)
        val nameStartIndex = uriChinese.lastIndexOf('/') + 1
        val nameEndIndex = uriChinese.lastIndexOf('.')
        if (nameStartIndex == -1 || nameEndIndex == -1) {
            showToast("获取书籍名称失败")
            return
        }
        val name = uriChinese.substring(nameStartIndex, nameEndIndex)
        val type = when (uriChinese.substring(nameEndIndex + 1, uriChinese.length)) {
            "txt" -> 0
            "epub" -> 1
            "pdf" -> 2
            else -> 0
        }
        addBookBtn.text = name
        BookDaoManager.instance.inserOrUpdate(BookModel().apply {
            this.path = uriStr
            this.name = name
            this.type = type
            this.addDate = Date()
        })
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_bookrack
    }
}