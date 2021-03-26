package com.base.commonmodule.extend

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment

/**
 * Created by acorn on 2021/3/26.
 */

fun Activity.startBookPicker(requestCode: Int, pickerInitialUri: Uri?) {
    val intent = getFilePickerIntent(
        arrayOf("application/pdf", "application/epub+zip", "text/plain"),
        pickerInitialUri
    )
    startActivityForResult(intent, requestCode)
}

fun Fragment.startBookPicker(requestCode: Int, pickerInitialUri: Uri?) {
    val intent = getFilePickerIntent(
        arrayOf("application/pdf", "application/epub+zip", "text/plain"),
        pickerInitialUri
    )
    startActivityForResult(intent, requestCode)
}

fun Activity.startFilePicker(requestCode: Int, mimeTypes: Array<String>?, pickerInitialUri: Uri?) {
    val intent = getFilePickerIntent(mimeTypes, pickerInitialUri)
    startActivityForResult(intent, requestCode)
}

fun Fragment.startFilePicker(requestCode: Int, mimeTypes: Array<String>?, pickerInitialUri: Uri?) {
    val intent = getFilePickerIntent(mimeTypes, pickerInitialUri)
    startActivityForResult(intent, requestCode)
}

/**
 * 保留权限
当您的应用打开文件进行读取或写入时，系统会向应用授予对该文件的 URI 的访问权限，该授权在用户重启设备之前一直有效。
但是，假设您的应用是图片编辑应用，而且您希望用户能够直接从应用中访问他们最近修改的 5 张图片，那么在用户重启设备后，
您就必须让用户返回到系统选择器来查找这些文件。
如需在设备重启后保留对文件的访问权限并提供更出色的用户体验，您的应用可以“获取”系统提供的永久性 URI 访问权限
 */
fun Activity.saveFilePermission(uri: Uri) {
    saveFilePermission(uri, applicationContext.contentResolver)
}

/**
 * 保留权限
当您的应用打开文件进行读取或写入时，系统会向应用授予对该文件的 URI 的访问权限，该授权在用户重启设备之前一直有效。
但是，假设您的应用是图片编辑应用，而且您希望用户能够直接从应用中访问他们最近修改的 5 张图片，那么在用户重启设备后，
您就必须让用户返回到系统选择器来查找这些文件。
如需在设备重启后保留对文件的访问权限并提供更出色的用户体验，您的应用可以“获取”系统提供的永久性 URI 访问权限
 */
fun Fragment.saveFilePermission(uri: Uri) {
    activity?.applicationContext?.contentResolver?.let {
        saveFilePermission(uri, it)
    }
}

/**
 * 保留权限
当您的应用打开文件进行读取或写入时，系统会向应用授予对该文件的 URI 的访问权限，该授权在用户重启设备之前一直有效。
但是，假设您的应用是图片编辑应用，而且您希望用户能够直接从应用中访问他们最近修改的 5 张图片，那么在用户重启设备后，
您就必须让用户返回到系统选择器来查找这些文件。
如需在设备重启后保留对文件的访问权限并提供更出色的用户体验，您的应用可以“获取”系统提供的永久性 URI 访问权限
 */
private fun saveFilePermission(uri: Uri, contentResolver: ContentResolver) {
    val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    // Check for the freshest data.
    contentResolver.takePersistableUriPermission(uri, takeFlags)
}

private fun getFilePickerIntent(mimeTypes: Array<String>?, pickerInitialUri: Uri?): Intent {
    return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        //Callers must indicate the acceptable document MIME types through setType(String).
        // For example, to select photos, use image/*. If multiple disjoint MIME types are acceptable,
        // define them in EXTRA_MIME_TYPES and setType(String) to */*.
        type = "*/*"
        mimeTypes?.let {
            putExtra(Intent.EXTRA_MIME_TYPES, it)
        }

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        pickerInitialUri?.let {
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, it)
        }
    }
}