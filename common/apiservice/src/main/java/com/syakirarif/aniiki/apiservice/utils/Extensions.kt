package com.syakirarif.aniiki.apiservice.utils

import timber.log.Timber

fun debugPrint(head: String? = "", msg: String? = "") {
    Timber.e("$head => %s", msg)
}

fun debugPrint(msg: String? = "") {
    Timber.e(msg)
}