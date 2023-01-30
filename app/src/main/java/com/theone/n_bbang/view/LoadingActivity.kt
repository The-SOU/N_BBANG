package com.theone.n_bbang.view

import android.content.Intent
import androidx.activity.viewModels
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityLoadingBinding
import com.theone.n_bbang.databinding.ActivityLoginBinding
import com.theone.n_bbang.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Handler

@AndroidEntryPoint
class LoadingActivity : BaseActivity<ActivityLoadingBinding>(R.layout.activity_loading) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.activity = this

        android.os.Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
        }, 3000)
    }
}