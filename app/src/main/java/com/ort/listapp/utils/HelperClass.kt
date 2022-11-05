package com.ort.listapp.utils

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ort.listapp.utils.SysConstants.TAG

internal object HelperClass {
    fun logErrorMessage(errorMessage: String?) {
        Log.d(TAG, errorMessage!!)
    }

    fun showAlert(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .create()
            .show()
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}