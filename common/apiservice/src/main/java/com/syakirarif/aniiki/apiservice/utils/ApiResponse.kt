package com.syakirarif.aniiki.apiservice.utils

sealed class ApiResponse<out R> {
    data class Success<out T>(val message: String, val data: T? = null) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    data class Failed(val errorMessage: String, val errCode: Int? = null) : ApiResponse<Nothing>()
    data class Empty(val errorMessage: String) : ApiResponse<Nothing>()
//    object Empty : ApiResponse<Nothing>()
}