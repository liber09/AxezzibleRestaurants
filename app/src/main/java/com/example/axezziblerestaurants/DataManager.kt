package com.example.axezziblerestaurants

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object DataManager {
    val restaurants = mutableListOf<Restaurant>()

    init{
        getAllRestaurants()
    }

    private fun getAllRestaurants() {
        val db = Firebase.firestore
        val dbRestaurants = db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        dbRestaurants.result
    }
}