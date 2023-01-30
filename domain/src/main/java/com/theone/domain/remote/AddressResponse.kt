package com.theone.domain.remote

import com.google.gson.annotations.SerializedName

object AddressResponse {
    data class GetSearchAddress(
        @SerializedName("result")
        var result: Int = 0,
        @SerializedName("msg")
        var msg: String = ""
    )
}