package com.theone.domain.repository

import com.theone.domain.remote.*
import com.theone.domain.remote.LoginResponse.GetBase
import com.theone.domain.remote.FindResponse.GetFindId
import com.theone.domain.remote.FindResponse.GetFindPw
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FindRepository @Inject constructor(private val nBbangApi: NBbangApi) : BaseApiResponse() {
    suspend fun getFindId(user_name: String, user_phone: String) : Flow<NetworkResult<GetFindId>> {
        return flow {
            emit(safeApiCall { nBbangApi.getFindId(user_name, user_phone) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFindPw(user_id: String, user_name: String, user_phone: String) : Flow<NetworkResult<GetFindPw>> {
        return flow {
            emit(safeApiCall { nBbangApi.getFindPw(user_id, user_name, user_phone) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getChangePw(user_pw: String, user_idx: String) : Flow<NetworkResult<GetBase>> {
        return flow {
            emit(safeApiCall { nBbangApi.getChangePw(user_pw, user_idx) })
        }.flowOn(Dispatchers.IO)
    }
}