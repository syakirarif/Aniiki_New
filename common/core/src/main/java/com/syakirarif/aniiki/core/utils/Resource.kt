package com.syakirarif.aniiki.core.utils

data class Resource<out T>(
    val status: Status? = null,
    val data: T? = null,
    val message: String? = null,
    val errCode: Int? = null
) {
    companion object {
        fun <T> success(msg: String? = null, data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, msg)
        }

        fun <T> error(msg: String? = null, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> empty(msg: String? = null, data: T? = null): Resource<T> {
            return Resource(Status.EMPTY, data, msg)
        }

        fun <T> failed(msg: String? = null, errCode: Int? = null, data: T? = null): Resource<T> {
            return Resource(Status.FAILED, data, msg, errCode)
        }
    }
}