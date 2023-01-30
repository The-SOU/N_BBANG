package com.theone.domain.remote

import com.google.gson.annotations.SerializedName

object LoginResponse {
    /** 기본 result */
    data class GetBase(
        @SerializedName("result")
        var result: Int = 0,
        @SerializedName("msg")
        var msg: String = "",
    )

    /** 로그인 & 자동 로그인 */
    data class GetLogin(
        @SerializedName("result")
        var result: Int = 0,
        @SerializedName("msg")
        var msg: String = "",
        @SerializedName("user_data")
        var user_data: List<UserData> = listOf()
    )

    data class UserData(
        @SerializedName("idx")
        var idx: String = "",
        @SerializedName("user_id")
        var user_id: String = "",
        @SerializedName("user_name")
        var user_name: String = "",
        @SerializedName("user_phone")
        var user_phone: String = "",
        @SerializedName("user_nick")
        var user_nick: String = "",
        @SerializedName("user_img")
        var user_img: String = "",
        @SerializedName("is_push")
        var is_push: String = "",
        @SerializedName("user_token")
        var user_token: String = "",
    )
}