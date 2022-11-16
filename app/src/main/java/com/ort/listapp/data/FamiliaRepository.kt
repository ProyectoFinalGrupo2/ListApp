package com.ort.listapp.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.domain.model.Familia
import com.ort.listapp.utils.SysConstants.DB_FAMILIAS
import kotlinx.coroutines.tasks.await

class FamiliaRepository {
    private val db = Firebase.firestore
    private val familiassRef = db.collection(DB_FAMILIAS)

    fun suscribeFamilia(familia: MutableLiveData<Familia>) {
        val docRef = familiassRef.document(prefsHelper.getFamilyId())
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                familia.value = snapshot.toObject<Familia>()
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }
    }

    suspend fun guardarFamilia(familia: Familia) {
        familiassRef.document(familia.id).set(familia).await()
    }

    suspend fun crearFamilia(nombre: String, id: String, password: String) {
        val familia = Familia(
            id = id,
            nombre = nombre,
            password = password,
        )
        familiassRef.document(id).set(familia).await()
    }

    suspend fun checkIdPassEnFamilia(idFamilia: String, passFamilia: String): Boolean {
        var resp = false
        val familia = obtenerFamiliaPorId(idFamilia)
        if ((familia != null) && (familia.password == passFamilia)) {
            resp = true
        }
        return resp
    }

    suspend fun existeFamilia(id: String): Boolean =
        obtenerFamiliaPorId(id) != null

    private suspend fun obtenerFamiliaPorId(id: String): Familia? {
        var familia: Familia? = null
        val documentSnapshot = familiassRef.document(id).get().await()
        if (documentSnapshot.exists()) {
            familia = documentSnapshot.toObject<Familia>()
        }
        return familia
    }


}