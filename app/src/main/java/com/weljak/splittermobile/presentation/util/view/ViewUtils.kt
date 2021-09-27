package com.weljak.splittermobile.presentation.util.view

import android.view.View
import android.widget.Button
import android.widget.ProgressBar

object ViewUtils {
    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.INVISIBLE
    }

    fun makeButtonClickable(button: Button) {
        button.isClickable = true
    }

    fun makeButtonUnClickable(button: Button) {
        button.isClickable = false
    }
}