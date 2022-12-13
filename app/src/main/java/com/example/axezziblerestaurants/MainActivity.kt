package com.example.axezziblerestaurants

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    //initialize database
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addInitialData() //Add the first restaurants that are included in app

        //Get the new restaurant button
        val newRestaurantButtonClick = findViewById<Button>(R.id.addNewResButton)
        //Set a clickListener
        newRestaurantButtonClick.setOnClickListener{
            val newRestaurantScreen = Intent(this,AddNewRestaurant::class.java) //Get a reference to the game activity screen
            startActivity(newRestaurantScreen) //Go to the new restaurant activity
        }
    }

    //Creates the first default restaurants included in app
    private fun addInitialData() {
        //Create new restaurant object
        val restaurantOne = Restaurant("TiAmo",
            "Italian",true,
            true, "Strandvägen 4", 44431,
            "Stenungsund","tiamo.png",4)
        val restaurantTwo = Restaurant("McDonalds",
            "Hamburger",false,
            true, "Stenunge allé 1", 44430,
            "Stenungsund","mcd.png",3)
        val restaurantThree = Restaurant("Tonys",
            "Hamburger",true,
            true, "Sandbergs plats 1", 44430,
            "Stenungsund","tonys.png",5)
        val restaurantFour = Restaurant("Karlbergs Krog",
            "Hamburger",true,
            true, "Västra köpmansgatan 2", 44430,
            "Stenungsund","karlbergs.png",5)
        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("restaurants").document("TiAmo").set(restaurantOne, SetOptions.merge())
        db.collection("restaurants").document("McD_Stenungsund").set(restaurantTwo, SetOptions.merge())
        db.collection("restaurants").document("Tonys").set(restaurantThree, SetOptions.merge())
        db.collection("restaurants").document("Karlbergs").set(restaurantFour, SetOptions.merge())
    }

    /*


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
    */
}
