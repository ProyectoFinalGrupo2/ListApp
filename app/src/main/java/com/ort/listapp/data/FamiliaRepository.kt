package com.ort.listapp.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.ListaAppApplication.Companion.prefsHelper
import com.ort.listapp.domain.model.Familia
import kotlinx.coroutines.tasks.await

class FamiliaRepository {
    val db = Firebase.firestore
    val familiassRef = db.collection("familias")


//    suspend fun getFamiliaById(id: String): Familia {
//        try {
//            val documentSnapshot = familiassRef.document(id).get().await()
//            if (documentSnapshot.exists()) {
//                return documentSnapshot.toObject<Familia>()!!
//            } else {
//                throw Exception("Documento no encontrado")
//            }
//        } catch (e: Exception) {
//            throw Exception("Error en getFamiliaById")
//        }
//    }

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

}