package com.theone.n_bbang.widget

import android.content.Context
import android.content.SharedPreferences

class FileData(context: Context, filename: String) {

    private var Data: SharedPreferences

    private var editor: SharedPreferences.Editor

    init {
        Data = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        editor = Data.edit()
        editor.apply()
    }

    //파일의 전체 내용 읽기
    val allValue: ArrayList<String>
        get() {
            val arr = ArrayList<String>()

            val col = Data.all.values
            val it = col.iterator()

            while (it.hasNext()) {
                val msg = it.next() as String
                arr.add(msg)
            }
            return arr
        }

    val allValue1: Array<String?>
        get() {
            val arr = arrayOfNulls<String>(Data.all.size)

            val col = Data.all.values
            val it = col.iterator()

            var i = 0
            while (it.hasNext()) {
                val msg = it.next() as String
                arr[i] = msg
                i++
            }
            return arr
        }

    fun setData(key: String?, value: String?) {
        if (key != null && value != null) {
            editor.putString(key, value)
            editor.commit()
        }
    }

    fun setBoolean(key: String?, value: Boolean?) {
        if (key != null && value != null) {
            editor.putBoolean(key, value)
            editor.commit()
        }
    }

    fun setInt(key: String?, value: Int?) {
        if (key != null && value != null) {
            editor.putInt(key, value)
            editor.commit()
        }
    }

    fun removeData(key: String) {
        if (key.isNotBlank() && key.isNotEmpty()) {
            editor.remove(key)
            editor.commit()
        }
    }

    fun removeAllData() {
        Data.all.keys.map { key ->
            editor.remove(key)
        }
        editor.commit()
    }

    fun getData(key: String): String {
        return Data.getString(key, "") ?: ""
    }

    fun getData(key: String, default_str: String): String {
        return Data.getString(key, default_str) ?: default_str
    }

    fun getBoolean(key: String): Boolean {
        return Data.getBoolean(key, false)
    }

    fun getBoolean(key: String, default_str: Boolean): Boolean {
        return Data.getBoolean(key, default_str)
    }


    fun getInt(key: String, default_str: Int): Int {
        return Data.getInt(key, default_str)
    }

    fun getKeys(): MutableSet<String> {
        return Data.all.keys
    }

}
