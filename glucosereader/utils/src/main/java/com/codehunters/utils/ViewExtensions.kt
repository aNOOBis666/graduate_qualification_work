package com.codehunters.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(messageText: String) {
    Snackbar.make(
        this,
        messageText,
        Snackbar.LENGTH_SHORT
    ).show()

}