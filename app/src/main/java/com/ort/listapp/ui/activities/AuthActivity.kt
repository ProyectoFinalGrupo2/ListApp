package com.ort.listapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ort.listapp.R
import com.ort.listapp.utils.HelperClass

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onDestroy() {
        super.onDestroy()
        HelperClass.logErrorMessage("AuthActivity destroyed")
    }
}