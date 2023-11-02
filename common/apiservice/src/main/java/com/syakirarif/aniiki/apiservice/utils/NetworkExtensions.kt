package com.syakirarif.aniiki.apiservice.utils

import com.google.gson.GsonBuilder
import okhttp3.ResponseBody

fun handleResponseFailed(
    tag: String,
    errorCode: Int,
    errorBody: ResponseBody?,
    message: String
): ApiResponse.Failed {
    val prettyErrorBody =
        GsonBuilder().setPrettyPrinting().create().toJson(errorBody)
    debugPrint(msg = "RDS $tag => ERROR CODE: $errorCode, ERROR BODY: $prettyErrorBody")
    return when (errorCode) {
        401 -> ApiResponse.Failed(
            errorMessage = NetworkConsts.CODE_401, errCode = errorCode
        )

        403 -> ApiResponse.Failed(
            errorMessage = NetworkConsts.CODE_403, errCode = errorCode
        )

        404 -> ApiResponse.Failed(
            errorMessage = NetworkConsts.CODE_404, errCode = errorCode
        )

        415 -> ApiResponse.Failed(
            errorMessage = NetworkConsts.CODE_415, errCode = errorCode
        )

        500 -> ApiResponse.Failed(
            errorMessage = NetworkConsts.CODE_500, errCode = errorCode
        )

        503 -> ApiResponse.Failed(
            errorMessage = NetworkConsts.CODE_503, errCode = errorCode
        )

        504 -> ApiResponse.Failed(
            errorMessage = NetworkConsts.CODE_504, errCode = errorCode
        )

        else -> ApiResponse.Failed(errorMessage = handleErrorMessage(message))
    }
}

fun handleErrorMessage(msg: String): String {
    return if (msg.contains("oppal")) {
        NetworkConsts.CANNOT_CONNECT_TO_SERVER
    } else {
        msg
    }
}