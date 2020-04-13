package com.android.trendingcermati.extention

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

fun Context.hideKeyboard(view: View) {
    val inputMethodManager: InputMethodManager by lazy {
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}