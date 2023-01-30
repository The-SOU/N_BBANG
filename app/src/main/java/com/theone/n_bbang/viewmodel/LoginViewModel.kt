package com.theone.n_bbang.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theone.domain.remote.LoginResponse.GetLogin
import com.theone.domain.remote.NetworkResult
import com.theone.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _getLogin: MutableStateFlow<NetworkResult<GetLogin>> = MutableStateFlow(NetworkResult.Loading())
    val getLogin: StateFlow<NetworkResult<GetLogin>> = _getLogin.asStateFlow()

    private val _getAutoLogin: MutableStateFlow<NetworkResult<GetLogin>> = MutableStateFlow(NetworkResult.Loading())
    val getAutoLogin: StateFlow<NetworkResult<GetLogin>> = _getAutoLogin.asStateFlow()

    fun fetchLoginResponse(user_id: String, user_pw: String, fcm_token: String, app_ver: String, device_uuid: String, device_name: String, device_os: String, device_ver: String) = viewModelScope.launch {
        repository.getLogin(user_id, user_pw, fcm_token, app_ver, device_uuid, device_name, device_os, device_ver).collect() { values ->
            _getLogin.value = values
        }
    }

    fun fetchAutoLoginResponse(user_idx: String, token: String, fcm_token: String, app_ver: String, device_uuid: String, device_name: String, device_os: String, device_ver: String) = viewModelScope.launch {
        repository.getAutoLogin(user_idx, token, fcm_token, app_ver, device_uuid, device_name, device_os, device_ver).collect() { values ->
            _getAutoLogin.value = values
        }
    }
}