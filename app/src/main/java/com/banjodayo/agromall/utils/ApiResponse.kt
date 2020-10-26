package com.banjodayo.agromall.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @SerializedName("status")
    @Expose
    var status: String = "false"

    @SerializedName("data")
    @Expose
    var data: T? = null

    @SerializedName("error")
    @Expose
    var error: ApiError? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("nextIndex")
    @Expose
    var nextIndex: Int? = null

}

data class ApiError(
    @SerializedName("status")
    @Expose
    var status: Int = 0,
    @SerializedName("message")
    @Expose
    var message: String? = null
)