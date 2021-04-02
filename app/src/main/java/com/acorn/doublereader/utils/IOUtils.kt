package com.acorn.doublereader.utils

import java.io.*

/**
 * Created by acorn on 2021/4/2.
 */
object IOUtils {
    fun readByInputStream(inputStream: InputStream, encoding: String? = "UTF8"): String? {
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream, encoding)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        inputStreamReader ?: return null
        val reader = BufferedReader(inputStreamReader)
        val sb = StringBuffer("")
        var line = ""
        try {
            while (reader.readLine()?.also { block -> line = block } != null) {
                sb.append(line)
                sb.append("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}