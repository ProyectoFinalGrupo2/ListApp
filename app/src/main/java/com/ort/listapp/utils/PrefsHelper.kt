package com.ort.listapp.utils

import android.content.Context

class PrefsHelper(val context: Context) {

    private val SHARED_NAME = "MyPrefs"
    private val SHARED_FAMILIA_ID = "familiaId"
    private val SHARED_NOMBRE_USUARIO = "nombreUsuario"
    private val SHARED_ID_USUARIO = "idUsuario"

    private val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveFamilyId(familyId: String) {
        storage.edit().putString(SHARED_FAMILIA_ID, familyId).apply()
    }

    fun getFamilyId(): String {
        return storage.getString(SHARED_FAMILIA_ID, "")!!
    }

    fun saveUserName(userName: String) {
        storage.edit().putString(SHARED_NOMBRE_USUARIO, userName).apply()
    }

    fun getUserName(): String {
        return storage.getString(SHARED_NOMBRE_USUARIO, "")!!
    }

    fun saveUserId(userId: String) {
        storage.edit().putString(SHARED_ID_USUARIO, userId).apply()
    }

    fun getUserId(): String {
        return storage.getString(SHARED_ID_USUARIO, "")!!
    }

    fun wipe() {
        storage.edit().clear().apply()
    }
}