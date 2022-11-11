package com.ort.listapp.utils

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.android.material.R
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.ort.listapp.utils.SysConstants.TAG

internal object HelperClass {
    fun logErrorMessage(errorMessage: String?) {
        Log.d(TAG, errorMessage!!)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getRandomCode(size: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(size) { charPool.random() }.joinToString("")
    }

    fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun getCircularProgress(context: Context) =
        IndeterminateDrawable.createCircularDrawable(
            context, CircularProgressIndicatorSpec(
                context, null, 0,
                R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall
            )
        )

}