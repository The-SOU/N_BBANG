package com.theone.n_bbang.view

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
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
import com.theone.domain.remote.FileDataModule
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseFragment
import com.theone.n_bbang.databinding.FragmentHomeBinding
import com.theone.n_bbang.widget.CustomDialog
import com.theone.n_bbang.widget.Data
import com.theone.n_bbang.widget.FileData
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @FileDataModule.UserData
    @Inject
    private lateinit var userData: FileData

    private lateinit var loginPopupView: View
    private var dialog: CustomDialog? = null
    override fun init() {
        binding.fragment = this
        Timber.e("onCreate")

        if (userData.getData(Data.USER_IDX, "").isNotEmpty()) {
            setLogin()
        }
//        setLoginPopup()
    }

    private fun setLogin() {
        val activity = context as Activity
        val displayMetrics = DisplayMetrics()

        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val translationValue = width.toFloat()

        binding.layoutInputId.translationX = translationValue * 1f
        binding.btnJoin.translationX = translationValue * 1f
        binding.layoutInputPw.translationX = translationValue * 1f
        binding.layoutResultMsg.scaleX = 0f

        /** 아이디 창 나타나기 */
        binding.layoutInputId.animate().apply {
            translationX(0f)
            interpolator = OvershootInterpolator()
            duration = 300
        }.start()
        binding.btnJoin.animate().apply {
            translationX(0f)
            interpolator = OvershootInterpolator()
            duration = 300
        }.start()

        /** 다음 버튼 */
        binding.btnOk.setOnClickListener {
            binding.layoutInputId.animate().apply {
                translationX(translationValue * -1f)
                interpolator = AccelerateDecelerateInterpolator()
                duration = 300
                withEndAction {
                    binding.layoutInputPw.animate().apply {
                        translationX(0f)
                        interpolator = OvershootInterpolator()
                        duration = 300
                    }.start()
                }
            }.start()

            binding.btnJoin.animate().apply {
                translationX(translationValue * -1f)
                interpolator = AccelerateDecelerateInterpolator()
                duration = 300
            }.start()
        }

        binding.btnJoin.setOnClickListener {
            val intent = Intent(context, JoinActivity::class.java)
            startActivity(intent)
        }
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

    override fun onResume() {
        super.onResume()
        if (userData.getData(Data.USER_IDX, "").isNotEmpty()) {
            setLogin()
        }
    }
}