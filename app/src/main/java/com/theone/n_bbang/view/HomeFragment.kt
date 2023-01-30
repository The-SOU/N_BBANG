package com.theone.n_bbang.view

import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseFragment
import com.theone.n_bbang.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun init() {
        binding.fragment = this
    }
}