package com.theone.n_bbang.view

import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseFragment
import com.theone.n_bbang.databinding.FragmentJoinListBinding

class JoinListFragment : BaseFragment<FragmentJoinListBinding>(R.layout.fragment_join_list) {
    override fun init() {
        binding.fragment = this
    }
}