package com.example.axezziblerestaurants

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class AddNewRestaurant : AppCompatActivity() {
    //initialize database
    val db = Firebase.firestore
    private val pickImage = 100
    private var imageUri: Uri? = null
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
        addImageButton.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val addImageView = findViewById<ImageView>(R.id.addRestaurantImage)
            addImageView.setImageURI(imageUri)
        }
    }
    /*
    Read the data from the textfields
    Save the information to the database.
     */
    private fun saveToDatabase() {
        //if(validateInput()){
            val name = findViewById<EditText>(R.id.nameEditText).text.toString()
            val type = findViewById<EditText>(R.id.typeEditText).text.toString()
            val guideDogsAllowed = findViewById<Switch>(R.id.guideDogsAllowedSwitch).isChecked
            val accessible = findViewById<Switch>(R.id.accessibleSwitch).isChecked
            val address = findViewById<EditText>(R.id.addressEditText).text.toString()
            val postalCode = Integer.parseInt(findViewById<EditText>(R.id.postalCodeEditText).text.toString())
            val city = findViewById<EditText>(R.id.cityEditText).text.toString()
            val rating = (findViewById<RatingBar>(R.id.addNewRating).rating.toDouble())
            val phoneNumber = findViewById<EditText>(R.id.phoneEditText).text.toString()
            val emailAddress = findViewById<EditText>(R.id.eMailEditText).text.toString()
            val webUrl = findViewById<EditText>(R.id.WebUrlEditText).text.toString()
            val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
            val review = findViewById<EditText>(R.id.addNewReviewEditText).text.toString()
            val restaurant = Restaurant(name,type,guideDogsAllowed,accessible,address,postalCode,city,"/restaurants/default.jpg",rating,phoneNumber,emailAddress, webUrl, description, review)
            db.collection("restaurants").add(restaurant) //Add restaurant to database
            finish()
        //

    }

    //Validate input fields Return false if any field is not correctly entered
    private fun validateInput():Boolean {
        var inputOk = true
        if(findViewById<EditText>(R.id.nameEditText).text.toString().length < 3){
            inputOk = false
        }
        if(findViewById<EditText>(R.id.typeEditText).text.toString().length < 3){
            inputOk = false
        }
        if (findViewById<EditText>(R.id.addressEditText).text.toString().length <1){
            inputOk = false
        }
        if(findViewById<EditText>(R.id.postalCodeEditText).text.toString().length != 5){
            inputOk = false
        }
        if(findViewById<EditText>(R.id.cityEditText).text.toString().length <1){
            inputOk = false
        }
        if(findViewById<EditText>(R.id.phoneEditText).text.toString().length <1){
            inputOk = false
        }
        if(findViewById<EditText>(R.id.eMailEditText).text.toString().length <1){
            inputOk = false
        }
        if(findViewById<EditText>(R.id.webUrlTextView).text.length <1){
            inputOk = false
        }
        //You are not allowed to add same restaurant multiple times
        for(rest in DataManager.restaurants){
            if(rest.name == findViewById<EditText>(R.id.nameEditText).text.toString()){
                if(rest.address == findViewById<EditText>(R.id.addressEditText).text.toString()){
                    inputOk = false
                }

            }
        }
        return inputOk
    }
}