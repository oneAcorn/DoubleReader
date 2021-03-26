package com.acorn.doublereader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acorn.doublereader.ui.bookrack.BookrackActivity
import com.base.commonmodule.extend.singleClick
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        btn1.singleClick {
            startActivity(Intent(this, BookrackActivity::class.java))
        }
    }
}