package com.theone.n_bbang.view

import android.content.Intent
import androidx.activity.viewModels
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityLoadingBinding
import com.theone.n_bbang.databinding.ActivityLoginBinding
import com.theone.n_bbang.viewmodel.LoginViewModel
import com.theone.n_bbang.widget.Utils.setStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint
import java.util.logging.Handler

class LoadingActivity : BaseActivity<ActivityLoadingBinding>(R.layout.activity_loading) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.activity = this
        setStatusBarTransparent(this)
        android.os.Handler().postDelayed({
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}