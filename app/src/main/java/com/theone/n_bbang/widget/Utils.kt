package com.theone.n_bbang.widget

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

object Utils {
    fun setStatusBarTransparent(activity: AppCompatActivity) {
        activity.window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        }
    }

    fun editViewRequestFocus(context: Context, editView: EditText){
        editView.isFocusableInTouchMode = true
        editView.requestFocus()
        editView.setSelection(editView.length())
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            editView,
            InputMethodManager.SHOW_IMPLICIT
        )
    }

    fun getStatusBarHeight(context: Context): Int {
        val screenSizeType = context.getResources().getConfiguration().screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        var statusbar = 0
        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) statusbar = context.getResources().getDimensionPixelSize(resourceId)
        }
        return statusbar
    }

}