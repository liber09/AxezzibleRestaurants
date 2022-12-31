package com.example.axezziblerestaurants

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

object DataManager {
    //The list that holds all restaurants
    val restaurants = mutableListOf<Restaurant>()
}