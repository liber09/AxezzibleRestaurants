package com.example.axezziblerestaurants

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

object DataManager {
    //The list that holds all restaurants
    val restaurants = mutableListOf<Restaurant>()
    init{
        getAllRestaurants() //Returns all restaurants
    }

    //return all restaurants
    private fun getAllRestaurants() {
        val db = Firebase.firestore
        val dbRestaurants = db.collection("restaurants") //Get the collection in dv
        dbRestaurants.addSnapshotListener { snapshot, e ->
            if(snapshot != null) {
                restaurants.clear() //Clear list so we get an updated list
                //Loop through all items in the collection and add to list
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