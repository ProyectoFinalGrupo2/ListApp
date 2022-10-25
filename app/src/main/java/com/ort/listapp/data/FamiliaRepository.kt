package com.ort.listapp.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ort.listapp.domain.model.Familia
import kotlinx.coroutines.tasks.await

class FamiliaRepository {
    val db = Firebase.firestore
    val familiassRef = db.collection("familias")

    suspend fun getFamiliaById(id: String): Familia? {
        val documentSnapshot = familiassRef.document(id).get().await()
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject<Familia>()!!
        }
        return null
    }

    suspend fun guardarFamilia(familia: Familia) {
        familiassRef.document(familia.id).set(familia).await()
    }

}