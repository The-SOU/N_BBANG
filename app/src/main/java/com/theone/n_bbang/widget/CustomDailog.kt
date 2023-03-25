package com.theone.n_bbang.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.theone.n_bbang.R

//class CustomDialog(context: Context, layout: View) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
class CustomDialog(context: Context, layout: View) : Dialog(context, R.style.AppTheme_LightGray) {
    private var Width = 0
    private var Anim = 0
    private var Background = android.R.color.transparent
    private var P_width = 0
    private var P_height = 0
    private var StatusBar_size = 0
    internal var Height = 0f
    internal var Alpha = 0.8f

    private var layout: View? = layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dm = context!!.resources.displayMetrics

        val window = getWindow()!!.attributes

        window.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.width = dm.widthPixels
//        window.height = dm.heightPixels - StatusBar_size
        window.dimAmount = Alpha

        getWindow()!!.setBackgroundDrawableResource(Background)
        getWindow()!!.attributes = window
        getWindow()!!.attributes.windowAnimations = Anim
        setContentView(layout!!)
    }

    fun setAnim(anim: Int) {
        this.Anim = anim
    }

    fun setBackground(background: Int) {
        this.Background = background
    }

    fun setAlpha(alpha: Float) {
        this.Alpha = alpha
    }
    fun getLayout() : View? {
        return layout
    }

    companion object {
        val FULL_SCREEN = "fullscreen"
    }

}
