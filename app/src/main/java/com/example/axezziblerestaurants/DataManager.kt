package com.example.axezziblerestaurants

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

object DataManager {
    val restaurants = mutableListOf<Restaurant>()

    init{
        getAllRestaurants()
    }

    private fun getAllRestaurants() {
        val db = Firebase.firestore
        val dbRestaurants = db.collection("restaurants")
        dbRestaurants.addSnapshotListener { snapshot, e ->
            if(snapshot != null) {
                restaurants.clear()
                for ( document in snapshot.documents) {
                    val item = document.toObject<Restaurant>()
                    if (item != null) {
                        restaurants.add(item)
                    }
                }
            }
        }
    }
}