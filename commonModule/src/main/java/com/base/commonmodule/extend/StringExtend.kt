package com.base.commonmodule.extend

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by acorn on 2021/3/18.
 */
/**
 * 对字符串进行MD5处理
 *
 * @return 32位的结果字符串
 */
fun String.md5(): String {
    try {
        val digest = MessageDigest.getInstance("MD5")
        digest.update(this.toByteArray())
        val result = digest.digest()

        val hexString = StringBuffer()
        for (i in result.indices) {
            var h = Integer.toHexString(0xFF and result[i].toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return ""
}