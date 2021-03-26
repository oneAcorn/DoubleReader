package com.acorn.doublereader.ui.bookrack

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import com.acorn.doublereader.R
import com.base.commonmodule.base.CommonBaseFragment
import com.base.commonmodule.extend.*
import com.base.commonmodule.utils.logI
import kotlinx.android.synthetic.main.fragment_bookrack.*

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
                startBookPicker(PICK_FILE_REQUEST_CODE, null)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_FILE_REQUEST_CODE -> { //选择书籍
                    val uriStr = data?.dataString
                    val uri = Uri.parse(uriStr)
                    saveFilePermission(uri)
                    logI("sdf")
                }
            }
        }
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_bookrack
    }
}