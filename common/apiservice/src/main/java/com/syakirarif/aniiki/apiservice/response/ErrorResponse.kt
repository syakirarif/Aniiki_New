package com.syakirarif.aniiki.apiservice.response

data class ErrorResponse(
    var status: Int = 0,
    var type: String = "",
    var error: String = ""
)
