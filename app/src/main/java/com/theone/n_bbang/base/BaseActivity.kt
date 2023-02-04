package com.theone.n_bbang.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.theone.n_bbang.widget.Utils.setStatusBarTransparent

abstract class BaseActivity<T: ViewDataBinding>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
//        setStatusBarTransparent(this)

        //        val params = LayoutParams(
//            LayoutParams.WRAP_CONTENT,
//            LayoutParams.WRAP_CONTENT
//        )
//        params.setMargins(0, Utils.getStatusBarHeight(this), 0, 0)
//        binding.toolbar.layoutParams = params

        init()
    }

    abstract fun init()
}