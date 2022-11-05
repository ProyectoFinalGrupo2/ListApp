package com.ort.listapp.ui.auth

data class AuthState(
    val loggedSinFamilia: Boolean = false,
    val loggedConFamilia: Boolean = false,
    val error: Boolean = false,
    val isDataValid: Boolean = false,
    val toastMessage: String = ""
)
