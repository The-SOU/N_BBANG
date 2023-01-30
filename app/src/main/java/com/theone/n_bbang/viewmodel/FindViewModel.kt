package com.theone.n_bbang.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theone.domain.remote.LoginResponse.GetBase
import com.theone.domain.remote.FindResponse.GetFindPw
import com.theone.domain.remote.FindResponse.GetFindId
import com.theone.domain.remote.LoginResponse
import com.theone.domain.remote.NetworkResult
import com.theone.domain.repository.FindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindViewModel @Inject constructor(private val repository: FindRepository) : ViewModel() {

    private val _getFindId: MutableStateFlow<NetworkResult<GetFindId>> = MutableStateFlow(NetworkResult.Loading())
    val getFindId: StateFlow<NetworkResult<GetFindId>> = _getFindId.asStateFlow()

    private val _getFindPw: MutableStateFlow<NetworkResult<GetFindPw>> = MutableStateFlow(NetworkResult.Loading())
    val getFindPw: StateFlow<NetworkResult<GetFindPw>> = _getFindPw.asStateFlow()

    private val _getChangePw: MutableStateFlow<NetworkResult<GetBase>> = MutableStateFlow(NetworkResult.Loading())
    val getChangePw: StateFlow<NetworkResult<GetBase>> = _getChangePw.asStateFlow()


    fun fetchFindIdResponse(user_name: String, user_phone: String) = viewModelScope.launch {
        repository.getFindId(user_name, user_phone).collect() { values ->
            _getFindId.value = values
        }
    }

    fun fetchFindPwResponse(user_id: String, user_name: String, user_phone: String) = viewModelScope.launch {
        repository.getFindPw(user_id, user_name, user_phone).collect(){ values ->
            _getFindPw.value = values
        }
    }

    fun fetchChangePwResponse(user_pw: String, user_idx: String) = viewModelScope.launch {
        repository.getChangePw(user_pw, user_idx).collect(){ values ->
            _getChangePw
        }
    }
}