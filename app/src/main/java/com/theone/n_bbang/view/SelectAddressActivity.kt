package com.theone.n_bbang.view

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.theone.domain.remote.NetworkResult
import com.theone.n_bbang.R
import com.theone.n_bbang.adapter.SearchAddressAdapter
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivitySelectAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SelectAddressActivity : BaseActivity<ActivitySelectAddressBinding>(R.layout.activity_select_address) {

//    private val addressViewModel by viewModels<AddressViewModel>()

    private lateinit var searchAddressAdapter: SearchAddressAdapter

    override fun init() {
        binding.activity = this

        initRecyclerAdapter()
//        fetchSearchAddress()

    }

    private fun initRecyclerAdapter() {
        searchAddressAdapter = SearchAddressAdapter()
        binding.recyclerView.adapter = searchAddressAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

//    private fun fetchSearchAddress() {
//        lifecycleScope.launchWhenStarted {
//            addressViewModel.getSearchAddress.collectLatest { response ->
//                when(response) {
//                    is NetworkResult.Success -> {
//                        /** bind data to the view */
//                    }
//                    is NetworkResult.Error -> {
//                        /** show error message */
//                    }
//                    is NetworkResult.Loading -> {
//                        /** show a progress bar */
//                    }
//                }
//            }
//        }
//    }
}