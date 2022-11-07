package com.ort.listapp

import android.app.Application
import com.ort.listapp.utils.PrefsHelper

class ListaAppApplication : Application() {

    companion object {
        lateinit var prefsHelper: PrefsHelper
    }

    override fun onCreate() {
        super.onCreate()
        prefsHelper = PrefsHelper(applicationContext)
    }
}