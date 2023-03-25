package com.theone.n_bbang.view

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.theone.domain.remote.FileDataModule
import com.theone.domain.remote.NetworkResult
import com.theone.domain.utils.Data
import com.theone.domain.utils.FileData
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityLoadingBinding
import com.theone.n_bbang.viewmodel.LoginViewModel
import com.theone.n_bbang.widget.Utils.setStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class LoadingActivity : BaseActivity<ActivityLoadingBinding>(R.layout.activity_loading) {

    @Inject
    @FileDataModule.UserData
    lateinit var userData: FileData

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.activity = this
        setStatusBarTransparent(this)

        fetchAutoLogin()

        if (userData.getData(Data.USER_IDX, "").isNotEmpty()) {
            loginViewModel.fetchAutoLoginResponse(
                userData.getData(Data.USER_IDX),
                userData.getData(Data.USER_ID),
                userData.getData(Data.USER_NAME),
                userData.getData(Data.USER_PHONE),
                userData.getData(Data.USER_NICK),
                userData.getData(Data.USER_PROFILE_IMG),
                userData.getData(Data.USER_IS_PUSH),
                userData.getData(Data.USER_TOKEN)
            )
        } else {
            FirebaseApp.initializeApp(this)
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val pushToken = task.result
                    userData.setData(Data.USER_TOKEN, pushToken)
                }
            }
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, 3000)
        }
    }

    private fun fetchAutoLogin() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.getAutoLogin.collectLatest { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        Timber.d("fetchAutoLogin().Success")
                        response.data?.let {
                            if (it.result != 1) Toast.makeText(this@LoadingActivity, "${it.msg}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoadingActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    is NetworkResult.Error -> {
                        Timber.e("fetchAutoLogin().Error: ${response.message}")
                    }
                    is NetworkResult.Setting -> {
                        Timber.d("fetchAutoLogin().Setting")
                    }
                    is NetworkResult.Loading -> {
                        Timber.d("fetchAutoLogin().Loading")
                    }
                }

            }
        }
    }
}