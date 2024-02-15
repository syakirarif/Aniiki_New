package com.syakirarif.aniiki.apiservice.utils

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message

// Define your custom error model.
data class ErrorEnvelope(
    val code: Int,
    val codeMessage: String,
    val message: String
)

// Create a mapper for error responses.
// Within the `map` function, construct an instance of your custom model using the information from `ApiResponse.Failure.Error`.
object ErrorEnvelopeMapper : ApiErrorModelMapper<ErrorEnvelope> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorEnvelope {
        val statusCode = apiErrorResponse.statusCode.code
        val apiMessage = apiErrorResponse.message()
        val codeMsg = when (statusCode) {
            401 -> NetworkConsts.CODE_401
            403 -> NetworkConsts.CODE_403
            404 -> NetworkConsts.CODE_404
            415 -> NetworkConsts.CODE_415
            500 -> NetworkConsts.CODE_500
            503 -> NetworkConsts.CODE_503
            504 -> NetworkConsts.CODE_504
            else -> handleErrorMessage(apiMessage)
        }
        return ErrorEnvelope(code = statusCode, codeMessage = codeMsg, message = apiMessage)
    }
}