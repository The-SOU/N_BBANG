package com.theone.n_bbang.view

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseFragment
import com.theone.n_bbang.databinding.FragmentHomeBinding
import com.theone.n_bbang.widget.CustomDialog
import timber.log.Timber

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private var id = ""

    private lateinit var loginPopupView: View
    private var dialog: CustomDialog? = null
    override fun init() {
        binding.fragment = this
        Timber.e("onCreate")

        setLoginPopup()


    }

    private fun setLoginPopup() {
        loginPopupView = LayoutInflater.from(context).inflate(R.layout.popup_login, null)
        val layout_input_id = loginPopupView.findViewById<ConstraintLayout>(R.id.layout_input_id)
        val btn_ok = loginPopupView.findViewById<TextView>(R.id.btn_ok)
        val btn_join = loginPopupView.findViewById<ConstraintLayout>(R.id.btn_join)
        val layout_input_pw = loginPopupView.findViewById<ConstraintLayout>(R.id.layout_input_pw)

        val activity = context as Activity
        val displayMetrics = DisplayMetrics()

        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val translationValue = width.toFloat()
        layout_input_id.translationX = translationValue * 1f
        btn_join.translationX = translationValue * 1f
        layout_input_pw.translationX = translationValue * 1f

        btn_ok.setOnClickListener {
            layout_input_id.animate().apply {
                translationX(translationValue * -1f)
                interpolator = AccelerateDecelerateInterpolator()
                duration = 400
                withEndAction {
                    layout_input_pw.animate().apply {
                        translationX(0f)
                        interpolator = OvershootInterpolator()
                        duration = 500
                    }.start()
                }
            }.start()
            btn_join.animate().apply {
                translationX(translationValue * -1f)
                interpolator = AccelerateDecelerateInterpolator()
                duration = 400
            }.start()

        }

        dialog = CustomDialog(context!!, loginPopupView)
        dialog!!.setAlpha(0f)

        dialog!!.show()

        Handler().postDelayed({
            layout_input_id.animate().apply {
                translationX(0f)
                interpolator = OvershootInterpolator()
                duration = 500
            }.start()
            btn_join.animate().apply {
                translationX(0f)
                interpolator = OvershootInterpolator()
                duration = 500
            }.start()
        },1000)

    }
}