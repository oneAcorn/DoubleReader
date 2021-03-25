package com.base.commonmodule.network

import androidx.lifecycle.ViewModel
import java.nio.ByteBuffer

/**
 * Created by acorn on 2020/9/10.
 */
open class BaseSocketViewModel : ViewModel() {

    private fun send(str: String) {
    }

    private fun send(bytes: ByteArray) {
    }

    private fun send(byteBuffer: ByteBuffer) {
    }

    override fun onCleared() {
        super.onCleared()
    }
}