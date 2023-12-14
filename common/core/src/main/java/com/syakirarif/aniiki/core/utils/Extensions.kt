package com.syakirarif.aniiki.core.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


//fun String.asDateTime(): String {
//    return try {
//        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("id", "ID"))
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val convertedDate = sdf.parse(this)
//        convertedDate?.let { formatter.format(convertedDate) } ?: this
//    } catch (e: Exception) {
//        this
//    }
//}

//fun String.asDateTimeLong(): String {
//    return try {
//        val formatter = SimpleDateFormat("d MMMM yyyy HH:mm", Locale("id", "ID"))
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val convertedDate = sdf.parse(this)
//        convertedDate?.let { formatter.format(convertedDate) } ?: this
//    } catch (e: Exception) {
//        this
//    }
//}

//fun String.asOnlyDate(): String {
//    return try {
//        val formatter = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val convertedDate = sdf.parse(this)
//        convertedDate?.let { formatter.format(convertedDate) } ?: this
//    } catch (e: Exception) {
//        this
//    }
//}

//fun String.asOnlyTime(): String {
//    return try {
//        val formatter = SimpleDateFormat("HH:mm:ss", Locale("id", "ID"))
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        val convertedDate = sdf.parse(this)
//        convertedDate?.let { formatter.format(convertedDate) } ?: this
//    } catch (e: Exception) {
//        this
//    }
//}

//fun String.decodeFromBase64(): String {
//    return Base64.decode(this, Base64.NO_WRAP).toString(charset("UTF-8"))
//}

//fun String.encodeToBase64(): String {
//    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.NO_WRAP)
//}

//fun String.stringToRupiah(): String {
//    val localeID = Locale("in", "ID")
//    val doubleValue = this.toDoubleOrNull() ?: return this
//    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
//    numberFormat.minimumFractionDigits = 0
//    return numberFormat.format(doubleValue)
//}

//fun View.setViewVisibility(visible: Boolean) {
//    visibility = if (visible) {
//        View.VISIBLE
//    } else {
//        View.GONE
//    }
//}

//fun Fragment.hideKeyboard() {
//    view?.let { activity?.hideKeyboard(it) }
//}

//fun Activity.hideKeyboard() {
//    hideKeyboard(currentFocus ?: View(this))
//}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun debugPrint(head: String? = "", msg: String? = "") {
    Timber.e("$head => %s", msg)
}

fun debugPrint(msg: String? = "") {
    Timber.e(msg)
}

fun getCurrentYear(): String {
    return LocalDate.now().year.toString()
}

fun getCurrentMonth(): String {
    return LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
}

fun getCurrentDay(): String {
    return LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
}

fun getCurrentAnimeSeason(): String {
    val month = getCurrentMonth().lowercase()

    val winter = arrayOf("january", "february", "march")
    val spring = arrayOf("april", "may", "june")
    val summer = arrayOf("july", "august", "september")
    val fall = arrayOf("october", "november", "december")

    var season = ""

    if (winter.contains(month.lowercase())) {
        season = "winter"
    } else if (spring.contains(month.lowercase())) {
        season = "spring"
    } else if (summer.contains(month.lowercase())) {
        season = "summer"
    } else if (fall.contains(month.lowercase())) {
        season = "fall"
    }
    return season
}

fun String?.orNullEmpty(): String {
    return if (this != null && this != "null" && this != "")
        if (this.toString() == "null")
            "-"
        else
            this
    else
        "-"
}

fun Any?.orNullEmpty(): String {
    return if (this != null && this != "null" && this != "")
        if (this.toString() == "null")
            "-"
        else
            this.toString()
    else
        "-"
}