package com.theone.n_bbang.view

import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseFragment
import com.theone.n_bbang.databinding.FragmentMyinfoBinding

class MyInfoFragment : BaseFragment<FragmentMyinfoBinding>(R.layout.fragment_myinfo) {
    override fun init() {
        binding.fragment = this
    }
}