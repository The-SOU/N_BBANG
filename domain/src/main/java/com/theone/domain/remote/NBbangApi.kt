package com.theone.domain.remote

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.theone.domain.remote.LoginResponse.GetBase
import com.theone.domain.remote.LoginResponse.GetLogin
import com.theone.domain.remote.FindResponse.GetFindId
import com.theone.domain.remote.FindResponse.GetFindPw

interface NBbangApi {
    /** 로그인 */
    @FormUrlEncoded
    @POST("/member/login")
    suspend fun getLogin(
        @Field("user_id") user_id: String,
        @Field("user_pw") user_pw: String,
        @Field("fcm_token") fcm_token: String,
        @Field("app_ver") app_ver: String,
        @Field("device_uuid") device_uuid: String,
        @Field("device_name") device_name: String,
        @Field("device_os") device_os: String,
        @Field("device_ver") device_ver: String
    ): Response<GetLogin>

    /** 자동로그인 */
    @FormUrlEncoded
    @POST("/member/auto_login")
    suspend fun getAutoLogin(
        @Field("user_idx") user_idx: String,
        @Field("token") token: String,
        @Field("fcm_token") fcm_token: String,
        @Field("app_ver") app_ver: String,
        @Field("device_uuid") device_uuid: String,
        @Field("device_name") device_name: String,
        @Field("device_os") device_os: String,
        @Field("device_ver") device_ver: String
    ): Response<GetLogin>

    /** 아이디 찾기 */
    @FormUrlEncoded
    @POST("/member/search_id")
    suspend fun getFindId(
        @Field("user_name") user_name: String,
        @Field("user_phone") user_phone: String
    ): Response<GetFindId>

    /** 비밀번호 찾기 */
    @FormUrlEncoded
    @POST("/member/search_pw/search")
    suspend fun getFindPw(
        @Field("user_id") user_id: String,
        @Field("user_name") user_name: String,
        @Field("user_phone") user_phone: String
    ): Response<GetFindPw>

    /** 비밀번호 변경 */
    @FormUrlEncoded
    @POST("/member/search_pw/change")
    suspend fun getChangePw(
        @Field("user_pw") user_pw: String,
        @Field("user_idx") user_idx: String
    ): Response<GetBase>

    /** 회원가입 */
    @FormUrlEncoded
    @POST("/member/join")
    suspend fun getJoin(
        @Field("user_id") user_id: String,
        @Field("user_pw") user_pw: String,
        @Field("user_name") user_name: String,
        @Field("user_phone") user_phone: String,
        @Field("user_nick") user_nick: String,
        @Field("is_push") is_push: String
    ): Response<GetBase>

    /** 회원 중복체크 */
    @FormUrlEncoded
    @POST("/member/user_check")
    suspend fun getUserCheck(
        @Field("user_name") user_name: String,
        @Field("user_phone") user_phone: String
    ): Response<GetBase>

    /** 아이디 중복체크 */
    @FormUrlEncoded
    @POST("/member/user_id_check")
    suspend fun getUserIdCheck(
        @Field("user_id") user_id: String
    ): Response<GetBase>
}