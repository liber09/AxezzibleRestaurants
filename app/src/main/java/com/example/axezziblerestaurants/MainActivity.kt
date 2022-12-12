package com.example.axezziblerestaurants

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//initialize database
val db = Firebase.firestore
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addInitialData() //Add the first restaurants that are included in app
    }

    //Creates the first default restaurants included in app
    private fun addInitialData() {
        //Create new restaurant object
        val restaurantOne = Restaurant("TiAmo",
            RestaurantType.ITALIAN,true,
            true, "Strandvägen 4", 44431,
            "Stenungsund","tiamo.png",4)
        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("restaurants").document("TiAmo").set(restaurantOne, SetOptions.merge())
    }

    fun CreateUser(){
        //Create a user with firstname and lastname
        val user = hashMapOf(
            "first" to "Linda",
            "last" to "Bergsängel"
            )
        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}
