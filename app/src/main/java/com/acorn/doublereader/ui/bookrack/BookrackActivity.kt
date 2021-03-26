package com.acorn.doublereader.ui.bookrack

import android.content.res.Configuration
import android.os.Bundle
import com.acorn.doublereader.R
import com.base.commonmodule.base.CommonBaseActivity

/**
 * Created by acorn on 2021/3/26.
 */
class BookrackActivity : CommonBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookrack)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initListener() {
    }
}