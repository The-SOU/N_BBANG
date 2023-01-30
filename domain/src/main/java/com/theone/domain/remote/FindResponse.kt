package com.theone.domain.remote

import com.google.gson.annotations.SerializedName

object FindResponse{
    /** 아이디 찾기 */
    data class GetFindId(
        @SerializedName("result")
        var result: Int = 0,
        @SerializedName("msg")
        var msg: String = "",
        @SerializedName("user_id")
        var user_id: String = ""
    )

    /** 비밀번호 찾기 */
    data class GetFindPw(
        @SerializedName("result")
        var result: Int = 0,
        @SerializedName("msg")
        var msg: String = "",
        @SerializedName("user_idx")
        var user_idx: String = ""
    )
}
