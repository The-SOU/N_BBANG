package com.theone.n_bbang.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theone.domain.remote.LoginResponse.GetBase
import com.theone.domain.remote.NetworkResult
import com.theone.domain.repository.JoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(private val repository: JoinRepository) : ViewModel() {

    private val _getJoin: MutableStateFlow<NetworkResult<GetBase>> = MutableStateFlow(NetworkResult.Loading())
    val getJoin: StateFlow<NetworkResult<GetBase>> = _getJoin.asStateFlow()

    private val _getUserCheck: MutableStateFlow<NetworkResult<GetBase>> = MutableStateFlow(NetworkResult.Loading())
    val getUserCheck: StateFlow<NetworkResult<GetBase>> = _getUserCheck.asStateFlow()

    private val _getUserIdCheck: MutableStateFlow<NetworkResult<GetBase>> = MutableStateFlow(NetworkResult.Loading())
    val getUserIdCheck: StateFlow<NetworkResult<GetBase>> = _getUserIdCheck.asStateFlow()

    fun fetchJoinResponse(user_id: String, user_pw: String, user_name: String, user_phone: String, user_nick: String, is_push: String) = viewModelScope.launch {
        repository.getJoin(user_id, user_pw, user_name, user_phone, user_nick, is_push).collect() { values ->
            _getJoin.value = values
        }
    }

    fun fetchUserCheck(user_name: String, user_phone: String) = viewModelScope.launch {
        repository.getUserCheck(user_name, user_phone).collect() { values ->
            _getUserCheck.value = values
        }
    }

    fun fetchUserIdCheck(user_id: String) = viewModelScope.launch {
        repository.getUserIdCheck(user_id).collect() { values ->
            _getUserIdCheck.value = values
        }
    }
}