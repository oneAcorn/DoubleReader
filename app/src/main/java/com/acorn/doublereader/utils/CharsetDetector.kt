package com.acorn.doublereader.utils

import org.mozilla.universalchardet.UniversalDetector
import java.io.InputStream

/**
 * 获取文本文件编码
 * Created by acorn on 2021/4/1.
 */
object CharsetDetector {
    /**
     * 获取txt编码方式
     * (此方法通过遍历识别文本编码特征方式获取编码格式,可能耗时较长)
     */
    fun detectCharset(inputStream: InputStream?): String? {
        inputStream ?: return null
        val buf = ByteArray(4096)
        val detector = UniversalDetector()
        var nread: Int
        while (inputStream.read(buf).also { block -> nread = block } > 0 &&
            !detector.isDone) {
            detector.handleData(buf, 0, nread)
        }
        detector.dataEnd()
        val encoding = detector.detectedCharset
        detector.reset()
        inputStream.close()
        return encoding
    }
}