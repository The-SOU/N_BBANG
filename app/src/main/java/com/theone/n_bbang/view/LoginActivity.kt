package com.theone.n_bbang.view

import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.theone.domain.remote.NetworkResult
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityLoginBinding
import com.theone.n_bbang.viewmodel.LoginViewModel
import com.theone.n_bbang.widget.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.activity = this

        Handler().postDelayed(Runnable {
//            fetchAutoLogin()
        }, 300)
    }

//    private fun fetchAutoLogin() {
//        loginViewModel.fetchAutoLoginResponse()
//        lifecycleScope.launchWhenStarted {
//            loginViewModel.getAutoLogin.collectLatest { response ->
//                when(response){
//                    is NetworkResult.Success -> {
//                        /** bind data to the view */
//                        Logger.d("AutoLogin::", "fetchAutoLogin : ${response.data}")
//
//                        // TODO: result data 받고 main activity 이동
//                    }
//                    is NetworkResult.Error -> {
//                        /** show error message */
//                        Logger.d("AutoLogin::", "fetchAutoLogin : ${response.message}")
//                    }
//                    is NetworkResult.Loading -> {
//                        /** show a progress bar */
//                        Logger.d("AutoLogin::", "fetchAutoLogin : Loading")
//                    }
//                }
//            }
//        }
//    }
}