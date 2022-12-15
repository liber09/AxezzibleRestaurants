package com.example.axezziblerestaurants

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Switch
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddNewRestaurant : AppCompatActivity() {
    //initialize database
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_restaurant)

        //Get the return to list button
        val returnToListButtonClick = findViewById<Button>(R.id.returnToListButton)
        //Set a clickListener
        returnToListButtonClick.setOnClickListener{
            val mainActivity = Intent(this,MainActivity::class.java) //Get a reference to the game activity screen
            startActivity(mainActivity) //Go to mainActivity
        }
        //Get the return to list button
        val saveButtonClick = findViewById<Button>(R.id.saveButton)
        //Set a clickListener
        saveButtonClick.setOnClickListener{
            saveToDatabase()
        }
        val addImageButton = findViewById<Button>(R.id.addImageButton)
        addImageButton.setOnClickListener {
        }
    }

    private fun saveToDatabase() {
        val name = findViewById<EditText>(R.id.nameEditText).text.toString()
        val type = findViewById<EditText>(R.id.typeEditText).text.toString()
        val guideDogsAllowed = findViewById<Switch>(R.id.guideDogsAllowedSwitch).isChecked
        val accessible = findViewById<Switch>(R.id.accessibleSwitch).isChecked
        val address = findViewById<EditText>(R.id.addressEditText).text.toString()
        val postalCode = Integer.parseInt(findViewById<EditText>(R.id.postalCodeEditText).text.toString())
        val city = findViewById<EditText>(R.id.cityEditText).text.toString()
        val rating = (findViewById<RatingBar>(R.id.addNewRating).rating.toDouble())
        val restaurant = Restaurant(name,type,guideDogsAllowed,accessible,address,postalCode,city,"",rating)
        db.collection("restaurants").add(restaurant)
    }
}