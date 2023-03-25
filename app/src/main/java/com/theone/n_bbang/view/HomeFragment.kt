package com.theone.n_bbang.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.lifecycleScope
import com.google.firebase.BuildConfig
import com.theone.domain.remote.FileDataModule
import com.theone.domain.remote.NetworkResult
import com.theone.domain.utils.Data
import com.theone.domain.utils.FileData
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseFragment
import com.theone.n_bbang.databinding.FragmentHomeBinding
import com.theone.n_bbang.viewmodel.LoginViewModel
import com.theone.n_bbang.widget.CustomDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment(val loginViewModel: LoginViewModel) : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @FileDataModule.UserData
    @Inject
    lateinit var userData: FileData

    var translationValue = 0f
    var idLogin = ""

    private lateinit var loginPopupView: View
    private var dialog: CustomDialog? = null
    override fun init() {
        binding.fragment = this
        Timber.d("onCreate")

        fetchLogin()

        if (userData.getData(Data.USER_IDX, "").isEmpty()) {
            setLogin()
        }
//        setLoginPopup()
    }

    private fun setLogin() {

//        val activity = context as Activity
//        val activity = FragmentComponentManager.findActivity(view?.context) as Activity
        val displayMetrics = DisplayMetrics()

        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        translationValue = width.toFloat()

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

        /** 아이디 다음 버튼 */
        binding.btnOk.setOnClickListener {
            idLogin = binding.etInputId.text.toString()

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

        /** 회원가입 */
        binding.btnJoin.setOnClickListener {
            val intent = Intent(context, JoinActivity::class.java)
            startActivity(intent)
        }

        /** 비밀번호 입력 */
        binding.btnOkPw.setOnClickListener {
            Timber.d("btnOkPw.setOnClickListener")
            val pwLogin = binding.etInputPw.text.toString()

            val appVersion = (BuildConfig.VERSION_CODE).toString()
            val deviceUUID = UUID.randomUUID().toString()
            val deviceName = Build.MODEL
            val deviceOS = "ANDROID"
            val deviceVersion = Build.VERSION.SDK_INT

            loginViewModel.fetchLoginResponse(
                idLogin,
                pwLogin,
                userData.getData(Data.USER_TOKEN, ""),
                appVersion,
                deviceUUID,
                deviceName,
                deviceOS,
                deviceVersion.toString(),
            )
        }
    }

    private fun fetchLogin() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.getLogin.collectLatest { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        Timber.d("fetchLogin().Success")
                        response.data?.let {
                            if (it.result == 1) {
                                it.user_data.apply {
                                    userData.setData(Data.USER_IDX, idx)
                                    userData.setData(Data.USER_ID, user_id)
                                    userData.setData(Data.USER_NAME, user_name)
                                    userData.setData(Data.USER_PHONE, user_phone)
                                    userData.setData(Data.USER_NICK, user_nick)
                                    userData.setData(Data.USER_PROFILE_IMG, user_img)
                                    userData.setData(Data.USER_IS_PUSH, is_push)
                                    userData.setData(Data.USER_TOKEN, user_token)
                                }
                                binding.layoutInputPw.animate().apply {
                                    translationX(translationValue * -1f)
                                    interpolator = AccelerateDecelerateInterpolator()
                                    duration = 300
                                }.start()
                            } else {
                                binding.tvResultMsg.text = it.msg
                                binding.layoutResultMsg.let {
                                    it.animate()
                                        .scaleX(1f)
                                        .setInterpolator(AccelerateDecelerateInterpolator())
                                        .setDuration(500)
                                        .withEndAction {
                                            Handler().postDelayed({
                                                it.animate()
                                                    .scaleX(0f)
                                                    .setInterpolator(AccelerateDecelerateInterpolator())
                                                    .setDuration(300)
                                                    .start()
                                            }, 1000)
                                        }
                                        .start()
                                }
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        Timber.e("fetchLogin().Error: ${response.message}")
                    }
                    is NetworkResult.Setting -> {
                        Timber.d("fetchLogin().Setting")
                    }
                    is NetworkResult.Loading -> {
                        Timber.d("fetchLogin().Loading")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (userData.getData(Data.USER_IDX, "").isEmpty()) {
            setLogin()
        }
    }
}