package com.theone.domain.repository

import com.theone.domain.remote.BaseApiResponse
import com.theone.domain.remote.LoginResponse.GetBase
import com.theone.domain.remote.NBbangApi
import com.theone.domain.remote.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JoinRepository @Inject constructor(private val nBbangApi: NBbangApi) : BaseApiResponse() {
    suspend fun getJoin(user_id: String, user_pw: String, user_name: String, user_phone: String, user_nick: String, is_push: String) : Flow<NetworkResult<GetBase>> {
        return flow {
            emit(safeApiCall { nBbangApi.getJoin(user_id, user_pw, user_name, user_phone, user_nick, is_push) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserCheck(user_name: String, user_phone: String) : Flow<NetworkResult<GetBase>> {
        return flow {
            emit(safeApiCall { nBbangApi.getUserCheck(user_name, user_phone) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserIdCheck(user_id: String) : Flow<NetworkResult<GetBase>> {
        return flow {
            emit(safeApiCall { nBbangApi.getUserIdCheck(user_id) })
        }.flowOn(Dispatchers.IO)
    }
}