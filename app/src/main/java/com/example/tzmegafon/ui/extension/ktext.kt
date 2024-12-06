package com.example.tzmegafon.ui.extension

import android.database.Cursor
import android.provider.OpenableColumns
import android.util.Log


fun convertFile(cursor: Cursor):Long{
    val getSizeFile = cursor.getColumnIndex(OpenableColumns.SIZE)
    val convertFile = cursor.getLong(getSizeFile)
    val convertKb = convertFile / 1024
    Log.d("size1","$convertKb")
    return convertKb
}