package com.theone.domain.repository

import com.theone.domain.remote.BaseApiResponse
import com.theone.domain.remote.LoginResponse.GetLogin
import com.theone.domain.remote.NBbangApi
import com.theone.domain.remote.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val nBbangApi: NBbangApi) : BaseApiResponse() {

    suspend fun getLogin(user_id: String, user_pw: String, fcm_token: String, app_ver: String, device_uuid: String, device_name: String, device_os: String, device_ver: String) : Flow<NetworkResult<GetLogin>> {
        return flow {
            emit(safeApiCall { nBbangApi.getLogin(user_id, user_pw, fcm_token, app_ver, device_uuid, device_name, device_os, device_ver) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAutoLogin(user_idx: String, token: String, fcm_token: String, app_ver: String, device_uuid: String, device_name: String, device_os: String, device_ver: String) : Flow<NetworkResult<GetLogin>> {
        return flow {
            emit(safeApiCall { nBbangApi.getAutoLogin(user_idx, token, fcm_token, app_ver, device_uuid, device_name, device_os, device_ver) })
        }.flowOn(Dispatchers.IO)
    }
}