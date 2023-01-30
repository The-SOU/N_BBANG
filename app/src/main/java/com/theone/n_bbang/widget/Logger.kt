package com.theone.n_bbang.widget

import android.util.Log

object Logger {
    fun d(Tag : String, Message : String) {
        Log.d(Tag, Message)
    }
    fun e(Tag : String, Message : String){
        Log.e(Tag , Message)
    }
    fun i(Tag : String, Message : String){
//        Log.i(Tag , Message)
    }
    fun v(Tag : String, Message : String){
//        Log.v(Tag , Message)
    }
    fun w(Tag : String, Message : String){
//        Log.w(Tag , Message)
    }
}